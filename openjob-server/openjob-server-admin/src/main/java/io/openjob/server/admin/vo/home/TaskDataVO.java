package io.openjob.server.admin.vo.home;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class TaskDataVO {
    private DataItemVO job;
    private DataItemVO jobInstance;
    private DataItemVO delay;
    private DataItemVO delayInstance;
}
