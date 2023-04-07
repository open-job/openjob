package io.openjob.worker.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {OpenjobWorkerProperties.class})
public class OpenjobWorkerAutoConfiguration {

}
