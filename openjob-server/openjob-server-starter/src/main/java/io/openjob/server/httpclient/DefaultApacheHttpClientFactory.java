package io.openjob.server.httpclient;

import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public class DefaultApacheHttpClientFactory implements ApacheHttpClientFactory {

    private final HttpClientBuilder builder;

    public DefaultApacheHttpClientFactory(HttpClientBuilder builder) {
        this.builder = builder;
    }

    @Override
    public HttpClientBuilder createBuilder() {
        return this.builder.disableContentCompression().disableCookieManagement()
                .useSystemProperties();
    }
}
