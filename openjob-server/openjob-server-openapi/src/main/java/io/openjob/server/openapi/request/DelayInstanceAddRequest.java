package io.openjob.server.openapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayInstanceAddRequest {

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    private String taskId;

    /**
     * Topic
     */
    @NotBlank
    private String topic;

    /**
     * Delay task params.
     */
    @NotBlank
    private String params;

    /**
     * Delay task extra params.
     */
    private String extra;

    /**
     * Delay task execute time.
     */
    @NotNull
    private Integer executeTime;
}
