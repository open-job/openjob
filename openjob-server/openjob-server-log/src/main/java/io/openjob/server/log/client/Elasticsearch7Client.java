package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class Elasticsearch7Client implements Client {
    /**
     * Host split count
     */
    private static final Integer HOST_SPLIT_COUNT = 2;

    private final LogProperties.Elasticsearch7Properties properties;
    private RestHighLevelClient client;
    private RequestOptions requestOptions;

    public Elasticsearch7Client(LogProperties.Elasticsearch7Properties elasticsearch7Properties) {
        this.properties = elasticsearch7Properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Connect client
        this.connect();

        // Request options
        this.clientRequestOptions();
    }

    @Override
    public void connect() throws Exception {
        // Cluster hosts
        RestClientBuilder builder = RestClient.builder(this.clientClusterHosts());

        // Cluster credentials
        this.clientCredentials(builder);
        client = new RestHighLevelClient(builder);
    }

    @Override
    public void shutdown() throws IOException {
        this.client.close();
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    /**
     * Client credentials
     *
     * @param builder builder
     */
    private void clientCredentials(RestClientBuilder builder) {
        if (Objects.isNull(this.properties.getUsername()) || Objects.isNull(this.properties.getPassword())) {
            return;
        }

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.properties.getUsername(), this.properties.getPassword()));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
    }

    /**
     * Client hosts
     *
     * @return HttpHost[]
     */
    private HttpHost[] clientClusterHosts() {
        if (StringUtils.isBlank(this.properties.getClusterNodes())) {
            throw new RuntimeException("Elasticsearch7 `clusterNodes` can not be empty!");
        }

        if (StringUtils.isBlank(this.properties.getProtocol())) {
            throw new RuntimeException("Elasticsearch7 `protocol` can not be empty!");
        }

        return Arrays.stream(this.properties.getClusterNodes().split(",")).map(cn -> {
            String[] clusterSplit = cn.split(":");
            if (clusterSplit.length != HOST_SPLIT_COUNT) {
                throw new RuntimeException(String.format("Elasticsearch7 `clusterNodes` is invalid(clusterNode=%s)!", cn));
            }
            return new HttpHost(clusterSplit[0], Integer.parseInt(clusterSplit[1]), this.properties.getProtocol());
        }).toArray(HttpHost[]::new);
    }

    /**
     * Client default request options
     */
    private void clientRequestOptions() {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(this.properties.getBufferLimit()));
        this.requestOptions = builder.build();
    }
}
