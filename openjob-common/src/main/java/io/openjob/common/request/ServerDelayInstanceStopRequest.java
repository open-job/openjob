package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerDelayInstanceStopRequest implements Serializable {
    private String taskId;
}
