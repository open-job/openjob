package io.openjob.worker.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob")
public class OpenjobWorkerProperties {
    /**
     * Heartbeat interval(s)
     */
    private Integer heartbeatInterval;

    private Server server = new Server();
    private Actor actor = new Actor();
    private Delay delay = new Delay();

    @Data
    public static class Server {
        private String host;
        private Integer port;
    }

    @Data
    public static class Actor {
        private Integer heartbeatNum;
        private Integer taskMasterNum;
        private Integer taskContainerNum;
        private Integer persistentNum;
        private Integer delayMasterNum;
    }

    @Data
    public static class Delay {
        /**
         * Default is false
         */
        public Boolean enable;

        /**
         * Pull size
         */
        public Integer pullSize;

        /**
         * Pull sleep(ms)
         */
        public Long pullSleep;

        /**
         * Pull step(ms)
         */
        public Long pullStep;
    }
}
