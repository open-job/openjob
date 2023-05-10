package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerDelayTopicPullResponse implements Serializable {
    private List<ServerDelayTopicResponse> topicList;
}
