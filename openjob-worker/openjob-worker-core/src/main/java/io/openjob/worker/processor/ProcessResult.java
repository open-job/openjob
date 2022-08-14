package io.openjob.worker.processor;

import io.openjob.common.constant.InstanceStatusEnum;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessResult {
    private InstanceStatusEnum status;
    private String result;

    public ProcessResult(Boolean result) {
        this.status = result ? InstanceStatusEnum.SUCCESS : InstanceStatusEnum.FAIL;
    }

    public ProcessResult(Boolean status, String result) {
        this.status = status ? InstanceStatusEnum.SUCCESS : InstanceStatusEnum.FAIL;
        this.result = result;
    }
}
