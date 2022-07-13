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
}
