package io.openjob.server.alarm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class DingdingResponse {
    @JsonProperty(value = "errcode")
    private Integer errCode;

    @JsonProperty(value = "errmsg")
    private String errMsg;
}
