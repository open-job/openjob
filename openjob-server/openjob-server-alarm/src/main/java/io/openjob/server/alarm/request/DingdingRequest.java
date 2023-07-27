package io.openjob.server.alarm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class DingdingRequest {
    @JsonProperty(value = "timestamp")
    private Long timestamp;

    @JsonProperty(value = "sign")
    private String sign;

    @JsonProperty(value = "msgtype")
    private String msgType = "markdown";

    @JsonProperty(value = "markdown")
    private MarkdownRequest markdown = new MarkdownRequest();

    @Data
    public static class MarkdownRequest {
        @JsonProperty(value = "title")
        private String title;

        @JsonProperty(value = "text")
        private String text;
    }
}
