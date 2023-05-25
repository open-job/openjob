package io.openjob.server.log.dao.impl;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.JsonUtil;
import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.client.Elasticsearch7Client;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogElasticDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Elasticsearch7 client document
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-getting-started-maven.html">...</a>
 *
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Slf4j
public class Elasticsearch7DAOImpl implements LogDAO {
    private final Elasticsearch7Client elasticsearch7Client;
    private final LogProperties.Elasticsearch7Properties properties;

    public Elasticsearch7DAOImpl(Elasticsearch7Client elasticsearch7Client, LogProperties.Elasticsearch7Properties properties) {
        this.elasticsearch7Client = elasticsearch7Client;
        this.properties = properties;
    }

    @Override
    public void batchAdd(List<ProcessorLogDTO> jobInstanceTaskLogs) {
        BulkRequest bulkRequest = new BulkRequest();
        jobInstanceTaskLogs.forEach(p -> {
            try {
                ProcessorLogElasticDTO processorLogElasticDTO = new ProcessorLogElasticDTO();
                processorLogElasticDTO.setTaskId(p.getTaskId());
                processorLogElasticDTO.setWorkerAddress(p.getWorkerAddress());
                processorLogElasticDTO.setTime(p.getTime());

                // Field map
                Map<String, String> fieldMap = new HashMap<>(32);
                p.getFields().forEach(plf -> {
                    if (LogFieldConstant.MESSAGE.equals(plf.getName())) {
                        processorLogElasticDTO.setMessage(plf.getValue());
                        return;
                    }

                    if (LogFieldConstant.THROWABLE.equals(plf.getName())) {
                        processorLogElasticDTO.setThrowable(plf.getValue());
                        return;
                    }

                    fieldMap.put(plf.getName(), plf.getValue());
                });
                processorLogElasticDTO.setFields(fieldMap);

                // Json
                String jsonLog = JsonUtil.encode(processorLogElasticDTO);

                // Index request
                IndexRequest indexRequest = new IndexRequest(this.getCreateIndex());
                indexRequest.id(UUID.randomUUID().toString()).source(jsonLog, XContentType.JSON);
                bulkRequest.add(indexRequest);
            } catch (Exception exception) {
                throw new RuntimeException("Elasticsearch7 format ", exception);
            }
        });

        // Async bulk
        bulkRequest.timeout(TimeValue.timeValueMillis(this.properties.getSocketTimeout()));
        this.elasticsearch7Client.getClient().bulkAsync(bulkRequest, this.elasticsearch7Client.getRequestOptions(), new BulkListener());
    }

    @Override
    public List<ProcessorLogDTO> queryByScroll(String taskUniqueId, Long time, Integer size) throws Exception {
        SearchRequest searchRequest = new SearchRequest(this.getSearchIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // Bool query builder
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        // `taskId=?`
        // Append `.keyword` to exact Match
        boolBuilder.must(QueryBuilders.termQuery("taskId" + ".keyword", taskUniqueId));

        // `time >= ?`
        RangeQueryBuilder timeQueryBuilder = QueryBuilders.rangeQuery("time");
        timeQueryBuilder.gte(time);
        boolBuilder.must(timeQueryBuilder);

        searchSourceBuilder.query(boolBuilder);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort(new FieldSortBuilder("time").order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);
        return this.queryResult(searchRequest, 0, size).getList();
    }

    @Override
    public PageDTO<ProcessorLogDTO> queryByPageSize(String taskUniqueId, String searchKey, Integer page, Integer size) throws IOException {

        SearchRequest searchRequest = new SearchRequest(this.getSearchIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // Bool query builder
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        // `taskId=?` and ( message=? or throwable=?)
        // Append `.keyword` to exact Match
        boolBuilder.must(QueryBuilders.termQuery("taskId" + ".keyword", taskUniqueId));
        if (StringUtils.isNotBlank(searchKey)) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchKey, LogFieldConstant.MESSAGE, LogFieldConstant.THROWABLE);
            multiMatchQueryBuilder.operator(Operator.OR);
            boolBuilder.must(multiMatchQueryBuilder);
        }

        // From
        searchSourceBuilder.query(boolBuilder);
        searchSourceBuilder.from((page - 1) * size);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort(new FieldSortBuilder("time").order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);
        return this.queryResult(searchRequest, page, size);
    }

    private PageDTO<ProcessorLogDTO> queryResult(SearchRequest searchRequest, Integer page, Integer size) throws IOException {
        SearchResponse searchResponse = this.elasticsearch7Client.getClient().search(searchRequest, this.elasticsearch7Client.getRequestOptions());
        SearchHit[] searchHit = searchResponse.getHits().getHits();

        // Processor log list
        List<ProcessorLogDTO> processorLogList = Arrays.stream(searchHit).map(h -> {
            String sourceJson = h.getSourceAsString();
            ProcessorLogElasticDTO processorLogElasticDTO = JsonUtil.decode(sourceJson, ProcessorLogElasticDTO.class);

            // Append search field
            Map<String, String> fieldsMap = processorLogElasticDTO.getFields();
            fieldsMap.put(LogFieldConstant.MESSAGE, Optional.ofNullable(processorLogElasticDTO.getMessage()).orElse(""));
            fieldsMap.put(LogFieldConstant.THROWABLE, Optional.ofNullable(processorLogElasticDTO.getThrowable()).orElse(""));

            // Processor log
            ProcessorLogDTO processorLogDTO = new ProcessorLogDTO();
            processorLogDTO.setTime(processorLogElasticDTO.getTime());
            processorLogDTO.setWorkerAddress(processorLogElasticDTO.getWorkerAddress());
            processorLogDTO.setTaskId(processorLogElasticDTO.getTaskId());

            // Processor log field
            List<ProcessorLogFieldDTO> fieldList = new ArrayList<>();
            fieldsMap.forEach((n, v) -> fieldList.add(new ProcessorLogFieldDTO(n, v)));
            processorLogDTO.setFields(fieldList);
            return processorLogDTO;
        }).collect(Collectors.toList());

        // Page
        PageDTO<ProcessorLogDTO> pageDTO = new PageDTO<>();
        pageDTO.setPage(page);
        pageDTO.setSize(size);
        pageDTO.setList(processorLogList);
        pageDTO.setTotal(searchResponse.getHits().getTotalHits().value);
        return pageDTO;
    }

    @Override
    public void deleteByDays(Integer beforeDays) {

    }

    public static class BulkListener implements ActionListener<BulkResponse> {
        @Override
        public void onResponse(BulkResponse bulkItemResponses) {

        }

        @Override
        public void onFailure(Exception e) {
            log.error("Elasticsearch7 bulk failed!", e);
        }
    }

    private String getCreateIndex() {
        return String.format("openjob_%s", "20230522");
    }

    private String getSearchIndex() {
        return "openjob*";
    }
}
