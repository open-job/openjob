package io.openjob.server.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "akka")
public class AkkaProperties {
    private Remote remote = new Remote();

    @Data
    public static class Remote {
        private String hostname;
        private Integer port;
    }

}
