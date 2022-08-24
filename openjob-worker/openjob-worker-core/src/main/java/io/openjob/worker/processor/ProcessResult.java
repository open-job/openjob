package io.openjob.worker.processor;

import io.openjob.common.constant.TaskStatusEnum;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessResult {
    private TaskStatusEnum status;
    private String result;

    public ProcessResult(Boolean result) {
        this.status = result ? TaskStatusEnum.SUCCESS : TaskStatusEnum.FAILED;
    }

    public ProcessResult(Boolean status, String result) {
        this.status = status ? TaskStatusEnum.SUCCESS : TaskStatusEnum.FAILED;
        this.result = result;
    }
}
