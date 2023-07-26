package io.openjob.server.httpclient;

import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public interface ApacheHttpClientFactory {
    HttpClientBuilder createBuilder();
}
