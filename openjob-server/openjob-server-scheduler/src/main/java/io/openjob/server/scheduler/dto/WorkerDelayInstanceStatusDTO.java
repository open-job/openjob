package io.openjob.server.scheduler.dto;

import io.openjob.common.constant.FailStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerDelayInstanceStatusDTO {

    private List<WorkerDelayInstanceTaskDTO> taskList;

    @Data
    public static class WorkerDelayInstanceTaskDTO {
        /**
         * Topic
         */
        private String topic;

        /**
         * Delay id.
         */
        private Long delayId;

        /**
         * Delay pid
         */
        private Long delayPid;

        /**
         * Task id.
         */
        private String taskId;

        /**
         * Task status.
         */
        private Integer status;

        /**
         * Fail status
         *
         * @see FailStatusEnum#getStatus()
         */
        private Integer failStatus;

        /**
         * Task result.
         */
        private String result;

        /**
         * Worker address
         */
        private String workerAddress;

        /**
         * Complete time
         */
        private Long completeTime;
    }
}
