package io.openjob.server.scheduler.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(SchedulerProperties.class)
public class SchedulerAutoConfiguration {
}
