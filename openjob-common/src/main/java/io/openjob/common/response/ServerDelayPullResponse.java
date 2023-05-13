package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerDelayPullResponse implements Serializable {
    private List<ServerDelayInstanceResponse> delayInstanceResponses;
}
