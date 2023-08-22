package io.openjob.server.autoconfigure;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.server.httpclient.ApacheHttpClientConnectionManagerFactory;
import io.openjob.server.httpclient.ApacheHttpClientFactory;
import io.openjob.server.httpclient.DefaultApacheHttpClientConnectionManagerFactory;
import io.openjob.server.httpclient.DefaultApacheHttpClientFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Configuration
@EnableConfigurationProperties(value = {HttpClientProperties.class})
public class HttpClientAutoConfiguration {
    private final ScheduledExecutorService scheduledService;
    private CloseableHttpClient httpClient;

    @Autowired
    public HttpClientAutoConfiguration() {
        this.scheduledService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-http-client-manager").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

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

    /**
     * Connection manager
     */
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


        this.scheduledService.scheduleWithFixedDelay(connectionManager::closeExpiredConnections, 1000,
                httpClientProperties.getConnectionTimerRepeat(), TimeUnit.MILLISECONDS);

        return connectionManager;
    }

    /**
     * Http client
     */
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

    /**
     * Destroy
     */
    @PreDestroy
    public void destroy() throws Exception {
        this.scheduledService.shutdown();
        if (this.httpClient != null) {
            this.httpClient.close();
        }
    }
}
