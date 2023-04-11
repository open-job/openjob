package io.openjob.worker.spring.boot.autoconfigure;

import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.spring.boot.OpenjobSpringWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {OpenjobWorkerProperties.class})
public class OpenjobWorkerAutoConfiguration {
    private final OpenjobWorkerProperties properties;

    @Autowired
    public OpenjobWorkerAutoConfiguration(OpenjobWorkerProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (Objects.nonNull(this.properties.getServer().getHost())) {
            System.setProperty(WorkerConstant.SERVER_HOST, this.properties.getServer().getHost());
        }
    }

    @Bean
    public OpenjobWorker openjobWorker() {
        return new OpenjobWorker();
    }

    @Bean
    public OpenjobSpringWorker openjobSpringWorker() {
        return new OpenjobSpringWorker();
    }
}
