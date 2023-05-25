package io.openjob.server.log.autoconfigure;

import io.openjob.server.log.constant.LogStorageConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.log")
public class LogProperties {

    private Storage storage = new Storage();

    @Data
    public static class Storage {
        private String selector = LogStorageConstant.H2;
        private Integer queryMaxSize;
        private H2Properties h2 = new H2Properties();
        private MysqlProperties mysql = new MysqlProperties();
        private TidbProperties tidb = new TidbProperties();
        private Elasticsearch7Properties elasticsearch7 = new Elasticsearch7Properties();
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
    public static class Elasticsearch7Properties {
        private String clusterNodes;
        private String username;
        private String password;
        private String protocol = "http";
        private Integer connectTimeout = 3000;
        private Integer socketTimeout = 3000;
        private Integer responseTimeout = 3000;
        private Integer bufferLimit = 104857600;
    }

    @Data
    public static class JdbcProperties {
        private String driver;
        private String url;
        private String user;
        private String password;
    }
}
