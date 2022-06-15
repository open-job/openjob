package io.openjob.server.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(ServerProperties.class)
public class ServerAutoConfiguration {

}
