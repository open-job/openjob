package io.openjob.server.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "log")
public class LogProperties {

    private Storage storage;

    @Data
    public static class Storage {
        private String selector;
        private Integer queryMaxSize;
    }

    @Data
    public static class H2StorageProperties {
        private String driver;
        private String url;
        private String user;
        private String password;
    }

    @Data
    public static class MysqlStorageProperties {
        private String jdbcUrl;
        private String user;
        private String password;
    }

    @Data
    public static class TidbStorageProperties {
        private String jdbcUrl;
        private String user;
        private String password;
    }

    @Data
    public static class ElasticsearchStorageProperties {

    }

    @Data
    public static class Elasticsearch7StorageProperties {

    }

    @Data
    public static class AliyunSlsStorageProperties {

    }
}
