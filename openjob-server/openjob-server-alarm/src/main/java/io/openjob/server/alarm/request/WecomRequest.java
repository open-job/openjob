package io.openjob.server.alarm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class WecomRequest {
    @JsonProperty(value = "msgtype")
    private String msgType = "markdown";

    @JsonProperty(value = "markdown")
    private MarkdownRequest markdown = new MarkdownRequest();

    @Data
    public static class MarkdownRequest {
        @JsonProperty(value = "content")
        private String content;
    }
}
