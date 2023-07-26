package io.openjob.server.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class DefaultApacheHttpClientConnectionManagerFactory implements ApacheHttpClientConnectionManagerFactory {

    @Override
    public HttpClientConnectionManager newConnectionManager(boolean disableSslValidation,
                                                            int maxTotalConnections, int maxConnectionsPerRoute, long timeToLive,
                                                            TimeUnit timeUnit) {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HTTP_SCHEME, PlainConnectionSocketFactory.INSTANCE);

        if (disableSslValidation) {
            try {
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null,
                        new TrustManager[]{new DisabledValidationTrustManager()},
                        new SecureRandom());
                registryBuilder.register(HTTPS_SCHEME, new SSLConnectionSocketFactory(
                        sslContext, NoopHostnameVerifier.INSTANCE));
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                log.warn("Error creating SSLContext", e);
            }
        } else {
            registryBuilder.register("https", SSLConnectionSocketFactory.getSocketFactory());
        }

        final Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                registry, null, null, null, timeToLive, timeUnit);
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

        return connectionManager;
    }

    static class DisabledValidationTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
