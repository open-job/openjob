package io.openjob.server.log.autoconfigure;

import io.openjob.server.log.client.Elasticsearch7Client;
import io.openjob.server.log.client.H2Client;
import io.openjob.server.log.client.MysqlClient;
import io.openjob.server.log.constant.LogStorageConstant;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dao.impl.Elasticsearch7DAOImpl;
import io.openjob.server.log.dao.impl.H2LogDAOImpl;
import io.openjob.server.log.dao.impl.MysqlLogDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {
    private final LogProperties logProperties;

    @Autowired
    public LogAutoConfiguration(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @ConditionalOnProperty(prefix = "openjob.log.storage", name = "selector", havingValue = LogStorageConstant.H2)
    public class H2AutoConfiguration {
        @Bean
        public H2Client h2Client() {
            return new H2Client(logProperties.getStorage().getH2());
        }

        @Bean
        public LogDAO h2LogDAO(H2Client h2Client) {
            return new H2LogDAOImpl(h2Client);
        }
    }

    @ConditionalOnProperty(prefix = "openjob.log.storage", name = "selector", havingValue = LogStorageConstant.MYSQL)
    public class MysqlAutoConfiguration {
        @Bean
        public MysqlClient mysqlClient() {
            return new MysqlClient(logProperties.getStorage().getMysql());
        }

        @Bean
        public LogDAO h2LogDAO(MysqlClient mysqlClient) {
            return new MysqlLogDAOImpl(mysqlClient);
        }
    }

    @ConditionalOnProperty(prefix = "openjob.log.storage", name = "selector", havingValue = LogStorageConstant.ELASTICSEARCH7)
    public class Elasticsearch7autoconfiguration {
        @Bean
        public Elasticsearch7Client mysqlClient() {
            return new Elasticsearch7Client(logProperties.getStorage().getElasticsearch7());
        }

        @Bean
        public LogDAO h2LogDAO(Elasticsearch7Client elasticsearch7Client, LogProperties.Elasticsearch7Properties properties) {
            return new Elasticsearch7DAOImpl(elasticsearch7Client, properties);
        }
    }
}
