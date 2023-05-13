package io.openjob.server.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DelayInstanceAddRequestDTO {

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    @JsonProperty(value = "ti")
    private String taskId;

    /**
     * Topic
     */
    @JsonProperty(value = "t")
    private String topic;

    /**
     * Delay task params.
     */
    @JsonProperty(value = "p")
    private String params;

    /**
     * Delay task extra params.
     */
    @JsonProperty(value = "e")
    private String extra;

    /**
     * Delay task execute time.
     */
    @JsonProperty(value = "et")
    private Long executeTime;
}
