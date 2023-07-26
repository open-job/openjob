package io.openjob.server.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
@ConfigurationProperties(prefix = "httpclient")
public class HttpClientProperties {

    /**
     * Value for disabling SSL validation.
     */
    private Boolean disableSslValidation = false;

    /**
     * Value for max number od connections.
     */
    private Integer maxConnections = 200;

    /**
     * Value for max number od connections per route.
     */
    private Integer maxConnectionsPerRoute = 50;

    /**
     * Value for time to live.
     */
    private Long timeToLive = 900L;

    /**
     * Time to live unit.
     */
    private TimeUnit timeToLiveUnit = TimeUnit.SECONDS;

    /**
     * Value for following redirects.
     */
    private Boolean followRedirects = true;

    /**
     * Value for connection timeout.
     */
    private Integer connectionTimeout = 2000;

    /**
     * Value for connection timer repeat.
     */
    private Integer connectionTimerRepeat = 3000;
}
