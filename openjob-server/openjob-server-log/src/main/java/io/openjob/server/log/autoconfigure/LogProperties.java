package io.openjob.server.log.autoconfigure;

import io.openjob.server.log.constant.LogStorageConstant;
import jdk.nashorn.internal.scripts.JD;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "log")
public class LogProperties {

    private Storage storage = new Storage();

    @Data
    public static class Storage {
        private String selector = LogStorageConstant.H2;
        private Integer queryMaxSize;
        private H2Properties h2 = new H2Properties();
        private MysqlProperties mysql = new MysqlProperties();
        private TidbProperties tidb = new TidbProperties();
        private ElasticsearchProperties elasticsearch = new ElasticsearchProperties();
        private Elasticsearch7Properties elasticsearch7 = new Elasticsearch7Properties();
        private SlsProperties sls = new SlsProperties();
        private ClsProperties cls = new ClsProperties();
    }

    @Data
    public static class H2Properties {
        private JdbcProperties properties = new JdbcProperties();
    }

    @Data
    public static class MysqlProperties {
        private JdbcProperties properties = new JdbcProperties();
    }

    @Data
    public static class TidbProperties {
        private JdbcProperties properties = new JdbcProperties();
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
