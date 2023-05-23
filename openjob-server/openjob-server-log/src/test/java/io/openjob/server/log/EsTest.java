package io.openjob.server.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.1
 */
public class EsTest {
    @Test
    public void test() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        Map<String, String> cnt = new HashMap<>();
        cnt.put("key", "key2");
        cnt.put("key2", "key236");
        cnt.put("key3", "key326");
        Product product1 = new Product();
        product1.setId("id1");
        product1.setName("name2");
        product1.setContent(cnt);


        ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(product1);

        IndexRequest indexRequest = new IndexRequest("product2");
        indexRequest.id(product1.getId())
                .source(jsonString, XContentType.JSON);

        // 同步执行，并使用自定义RequestOptions（COMMON_OPTIONS）。
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        long version = indexResponse.getVersion();
        System.out.println(version);
    }

    @Test
    public void test2() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        Map<String, String> cnt = new HashMap<>();
        cnt.put("key", "key2");
        cnt.put("key2", "key236");
        cnt.put("key3", "key326");
        Product product1 = new Product();
        product1.setId("id12");
        product1.setName("name12");
        product1.setContent(cnt);

        Map<String, String> cnt2 = new HashMap<>();
        cnt.put("key", "key2");
        cnt.put("key2", "key236");
        cnt.put("key3", "key326");
        Product product2 = new Product();
        product2.setId("id13");
        product2.setName("name13");
        product2.setContent(cnt2);


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(product1);

        ObjectMapper objectMapper2 = new ObjectMapper();
        String jsonString2 = objectMapper2.writeValueAsString(product2);

        IndexRequest indexRequest = new IndexRequest("product2");
        indexRequest.id(product1.getId())
                .source(jsonString, XContentType.JSON);

        IndexRequest indexRequest2 = new IndexRequest("product2");
        indexRequest2.id(product2.getId())
                .source(jsonString2, XContentType.JSON);

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(indexRequest);
        bulkRequest.add(indexRequest2);


        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
