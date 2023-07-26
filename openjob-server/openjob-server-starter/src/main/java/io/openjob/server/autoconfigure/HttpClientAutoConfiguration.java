package io.openjob.server.autoconfigure;

import io.openjob.server.httpclient.ApacheHttpClientConnectionManagerFactory;
import io.openjob.server.httpclient.ApacheHttpClientFactory;
import io.openjob.server.httpclient.DefaultApacheHttpClientConnectionManagerFactory;
import io.openjob.server.httpclient.DefaultApacheHttpClientFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Configuration
@EnableConfigurationProperties(value = {HttpClientProperties.class})
public class HttpClientAutoConfiguration {
    private final Timer connectionManagerTimer = new Timer(
            "HttpClientAutoConfiguration.connectionManagerTimer", true);
    private CloseableHttpClient httpClient;

    @Bean
    @ConditionalOnMissingBean
    public ApacheHttpClientConnectionManagerFactory connManFactory() {
        return new DefaultApacheHttpClientConnectionManagerFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClientBuilder apacheHttpClientBuilder() {
        return HttpClientBuilder.create();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApacheHttpClientFactory apacheHttpClientFactory(
            HttpClientBuilder builder) {
        return new DefaultApacheHttpClientFactory(builder);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClientConnectionManager connectionManager(
            ApacheHttpClientConnectionManagerFactory connectionManagerFactory,
            HttpClientProperties httpClientProperties) {
        final HttpClientConnectionManager connectionManager = connectionManagerFactory
                .newConnectionManager(httpClientProperties.getDisableSslValidation(),
                        httpClientProperties.getMaxConnections(),
                        httpClientProperties.getMaxConnectionsPerRoute(),
                        httpClientProperties.getTimeToLive(),
                        httpClientProperties.getTimeToLiveUnit());
        this.connectionManagerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectionManager.closeExpiredConnections();
            }
        }, 30000, httpClientProperties.getConnectionTimerRepeat());
        return connectionManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public CloseableHttpClient httpClient(ApacheHttpClientFactory httpClientFactory,
                                          HttpClientConnectionManager httpClientConnectionManager,
                                          HttpClientProperties httpClientProperties) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectionTimeout())
                .setRedirectsEnabled(httpClientProperties.getFollowRedirects())
                .build();

        this.httpClient = httpClientFactory
                .createBuilder()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setConnectionManager(httpClientConnectionManager)
                .build();
        return this.httpClient;
    }

    @PreDestroy
    public void destroy() throws Exception {
        this.connectionManagerTimer.cancel();
        if (this.httpClient != null) {
            this.httpClient.close();
        }
    }
}
