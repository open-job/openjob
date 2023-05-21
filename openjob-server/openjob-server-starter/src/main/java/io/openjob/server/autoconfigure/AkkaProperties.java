package io.openjob.server.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "akka")
public class AkkaProperties {

    /**
     * External (logical)
     */
    private Remote remote = new Remote();

    /**
     * Internal (bind)
     */
    private Bind bind = new Bind();

    @Data
    public static class Remote {
        private String hostname;
        private Integer port = 25520;
    }

    @Data
    public static class Bind {
        private String hostname;
        private Integer port = 25520;
    }
}
