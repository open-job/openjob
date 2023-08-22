package io.openjob.server.alarm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class FeishuRequest {
    @JsonProperty(value = "timestamp")
    private String timestamp;

    @JsonProperty(value = "sign")
    private String sign;

    @JsonProperty(value = "msg_type")
    private String msgType = "interactive";

    @JsonProperty(value = "card")
    private CardRequest card = new CardRequest();

    @Data
    public static class CardRequest {
        @JsonProperty(value = "config")
        private CardConfigRequest config = new CardConfigRequest();

        @JsonProperty(value = "elements")
        private List<CardElementRequest> elements = new ArrayList<>();

        @JsonProperty(value = "header")
        private CardHeaderRequest header = new CardHeaderRequest();
    }

    @Data
    public static class CardConfigRequest {
        @JsonProperty(value = "wide_screen_mode")
        private Boolean wideScreenMode = true;
    }

    @Data
    public static class CardHeaderRequest {
        @JsonProperty(value = "template")
        private String template;

        @JsonProperty(value = "title")
        private CardElementRequest title = new CardElementRequest();
    }

    @Data
    public static class CardElementRequest {
        @JsonProperty(value = "tag")
        private String tag = "markdown";

        @JsonProperty(value = "content")
        private String content;
    }
}
