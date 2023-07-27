package io.openjob.server.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class WebhookResponse {

    @JsonProperty(value = "code")
    private Integer code;

    @JsonProperty(value = "message")
    private Integer message;
}
