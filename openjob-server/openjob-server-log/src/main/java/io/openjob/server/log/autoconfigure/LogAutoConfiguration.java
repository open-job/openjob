package io.openjob.server.log.autoconfigure;

import io.openjob.server.log.client.H2Client;
import io.openjob.server.log.constant.LogStorageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin <swoft@qq.com>
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

    @Bean
    @ConditionalOnProperty(prefix = "log.storage.selector", value = LogStorageConstant.H2)
    public H2Client h2Client() {
        return new H2Client(this.logProperties.getStorage().getH2());
    }
}
