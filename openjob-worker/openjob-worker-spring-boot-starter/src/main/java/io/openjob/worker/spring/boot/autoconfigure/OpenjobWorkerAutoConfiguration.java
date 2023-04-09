package io.openjob.worker.spring.boot.autoconfigure;

import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.spring.boot.OpenjobSpringWorker;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {OpenjobWorkerProperties.class})
public class OpenjobWorkerAutoConfiguration {

    @Bean
    public OpenjobWorker openjobWorker() {
        return new OpenjobWorker();
    }

    @Bean
    public OpenjobSpringWorker openjobSpringWorker() {
        return new OpenjobSpringWorker();
    }
}
