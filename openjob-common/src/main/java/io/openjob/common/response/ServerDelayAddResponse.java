package io.openjob.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ServerDelayAddResponse implements Serializable {

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    private String taskId;
}
