package io.openjob.worker.spring.boot.autoconfigure;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.delay.OpenjobDelayTemplate;
import io.openjob.worker.spring.boot.OpenjobSpringWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {OpenjobWorkerProperties.class})
public class OpenjobWorkerAutoConfiguration implements InitializingBean {

    private final OpenjobWorkerProperties properties;

    @Autowired
    public OpenjobWorkerAutoConfiguration(OpenjobWorkerProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Server address
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.SERVER_ADDRESS))
                && Objects.nonNull(this.properties.getServer().getAddress())) {
            System.setProperty(WorkerConstant.SERVER_ADDRESS, String.valueOf(this.properties.getServer().getAddress()));
        }

        // Worker
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_HEARTBEAT_INTERVAL))
                && Objects.nonNull(this.properties.getWorker().getHeartbeatInterval())) {
            System.setProperty(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, String.valueOf(this.properties.getWorker().getHeartbeatInterval()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_HOST))
                && Objects.nonNull(this.properties.getWorker().getHost())) {
            System.setProperty(WorkerConstant.WORKER_HOST, this.properties.getWorker().getHost());
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_PORT))
                && Objects.nonNull(this.properties.getWorker().getPort())) {
            System.setProperty(WorkerConstant.WORKER_PORT, String.valueOf(this.properties.getWorker().getPort()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_APP_NAME))
                && Objects.nonNull(this.properties.getWorker().getAppName())) {
            System.setProperty(WorkerConstant.WORKER_APP_NAME, this.properties.getWorker().getAppName());
        }

        // Actor
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_HEARTBEAT_ACTOR_NUM))
                && Objects.nonNull(this.properties.getActor().getHeartbeatNum())) {
            System.setProperty(WorkerConstant.WORKER_HEARTBEAT_ACTOR_NUM, String.valueOf(this.properties.getActor().getHeartbeatNum()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_TASK_MASTER_ACTOR_NUM))
                && Objects.nonNull(this.properties.getActor().getTaskMasterNum())) {
            System.setProperty(WorkerConstant.WORKER_TASK_MASTER_ACTOR_NUM, String.valueOf(this.properties.getActor().getTaskMasterNum()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_TASK_CONTAINER_ACTOR_NUM))
                && Objects.nonNull(this.properties.getActor().getTaskContainerNum())) {
            System.setProperty(WorkerConstant.WORKER_TASK_CONTAINER_ACTOR_NUM, String.valueOf(this.properties.getActor().getTaskContainerNum()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_TASK_PERSISTENT_ACTOR_NUM))
                && Objects.nonNull(this.properties.getActor().getPersistentNum())) {
            System.setProperty(WorkerConstant.WORKER_TASK_PERSISTENT_ACTOR_NUM, String.valueOf(this.properties.getActor().getPersistentNum()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_MASTER_ACTOR_NUM))
                && Objects.nonNull(this.properties.getActor().getDelayMasterNum())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_MASTER_ACTOR_NUM, String.valueOf(this.properties.getActor().getDelayMasterNum()));
        }

        // Delay
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_ENABLE))
                && Objects.nonNull(this.properties.getDelay().getEnable())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_ENABLE, String.valueOf(this.properties.getDelay().getEnable()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_PULL_SIZE))
                && Objects.nonNull(this.properties.getDelay().getPullSize())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_PULL_SIZE, String.valueOf(this.properties.getDelay().getPullSize()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_PULL_SLEEP))
                && Objects.nonNull(this.properties.getDelay().getPullSleep())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_PULL_SLEEP, String.valueOf(this.properties.getDelay().getPullSleep()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_PULL_STEP))
                && Objects.nonNull(this.properties.getDelay().getPullStep())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_PULL_STEP, String.valueOf(this.properties.getDelay().getPullStep()));
        }
        if (StringUtils.isEmpty(System.getProperty(WorkerConstant.WORKER_DELAY_TIMEOUT))
                && Objects.nonNull(this.properties.getDelay().getTimeout())) {
            System.setProperty(WorkerConstant.WORKER_DELAY_TIMEOUT, String.valueOf(this.properties.getDelay().getTimeout()));
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenjobSpringContext openjobSpringContext() {
        return new OpenjobSpringContext();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.openjob", name = "enable", havingValue = "true", matchIfMissing = true)
    public OpenjobWorker openjobWorker() {
        return new OpenjobWorker();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.openjob", name = "enable", havingValue = "true", matchIfMissing = true)
    public OpenjobSpringWorker openjobSpringWorker() {
        return new OpenjobSpringWorker();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.openjob.delay", name = "enable", havingValue = "true")
    public OpenjobDelayTemplate openjobDelayTemplate() {
        return new OpenjobDelayTemplate();
    }
}
