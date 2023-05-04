package io.openjob.server.admin.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author inhere
 */
@Configuration
@EnableConfigurationProperties(AdminUserProperties.class)
public class AdminAutoConfiguration {
}
