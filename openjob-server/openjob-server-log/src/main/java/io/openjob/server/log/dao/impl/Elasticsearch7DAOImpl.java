package io.openjob.server.log.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.client.Elasticsearch7Client;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogElasticDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;
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
                processorLogElasticDTO.setFields(p.getFields().stream().collect(Collectors.toMap(ProcessorLogFieldDTO::getName, ProcessorLogFieldDTO::getValue)));

                // Json
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonLog = objectMapper.writeValueAsString(processorLogElasticDTO);

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
    public List<ProcessorLogDTO> queryByPage(String taskUniqueId, Long time, Long size) throws Exception {
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
        searchSourceBuilder.size(size.intValue());
        searchSourceBuilder.sort(new FieldSortBuilder("time").order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = this.elasticsearch7Client.getClient().search(searchRequest, this.elasticsearch7Client.getRequestOptions());

        SearchHit[] searchHit = searchResponse.getHits().getHits();
        if (searchHit.length > 0) {
            for (SearchHit hit : searchHit) {
                System.out.println(hit.getSourceAsString());
            }
        }
        return null;
    }

    @Override
    public List<ProcessorLogDTO> queryByPageSize(String taskUniqueId, String searchKey, Long page, Long size) throws Exception {
        return null;
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
