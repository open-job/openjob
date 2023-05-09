package io.openjob.worker.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.openjob")
public class OpenjobWorkerProperties {

    /**
     * Default is enable
     */
    private Boolean enable = true;

    /**
     * Server configuration
     */
    private Server server = new Server();

    /**
     * Worker configuration
     */
    private Worker worker = new Worker();

    /**
     * Actor configuration
     */
    private Actor actor = new Actor();

    /**
     * Delay configuration
     */
    private Delay delay = new Delay();

    @Data
    public static class Server {

        /**
         * Default is IP
         */
        private String host;

        /**
         * Default is 25520
         */
        private Integer port;
    }

    @Data
    public static class Worker {
        /**
         * Heartbeat interval(s)
         */
        private Integer heartbeatInterval;

        /**
         * Default is IP
         */
        private String host;

        /**
         * Default is 25588
         */
        private String port;

        /**
         * Worker application name
         */
        private String appName;
    }

    @Data
    public static class Actor {
        /**
         * Heartbeat num
         */
        private Integer heartbeatNum;

        /**
         * Task master num.
         */
        private Integer taskMasterNum;

        /**
         * Task container num.
         */
        private Integer taskContainerNum;

        /**
         * Persistent num
         */
        private Integer persistentNum;

        /**
         * Delay master num
         */
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

        /**
         * Timeout(ms)
         */
        public Long timeout;
    }
}
