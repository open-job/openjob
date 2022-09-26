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
        private H2Properties h2;
        private MysqlProperties mysql;
        private TidbProperties tidb;
        private ElasticsearchProperties elasticsearch;
        private Elasticsearch7Properties elasticsearch7;
        private SlsProperties sls;
    }

    @Data
    public static class H2Properties {
        private JdbcProperties properties;
    }

    @Data
    public static class MysqlProperties {
        private JdbcProperties properties;
    }

    @Data
    public static class TidbProperties {
        private JdbcProperties properties;
    }

    @Data
    public static class ElasticsearchProperties {

    }

    @Data
    public static class Elasticsearch7Properties {

    }

    /**
     * Aliyun sls
     */
    @Data
    public static class SlsProperties {

    }

    /**
     * Qcloud cls
     */
    @Data
    public static class ClsProperties {

    }

    @Data
    public static class JdbcProperties {
        private String driver;
        private String url;
        private String user;
        private String password;
    }
}
