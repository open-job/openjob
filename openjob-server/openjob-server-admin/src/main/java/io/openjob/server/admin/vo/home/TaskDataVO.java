package io.openjob.server.admin.vo.home;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class TaskDataVO {
    private TaskDataItemVO job;
    private TaskDataItemVO jobInstance;
    private TaskDataItemVO delay;
    private TaskDataItemVO delayInstance;

    @Data
    public static class TaskDataItemVO {
        private Long total;
        private Long newTotal;

        public TaskDataItemVO(Long total, Long newTotal) {
            this.total = total;
            this.newTotal = newTotal;
        }
    }
}
