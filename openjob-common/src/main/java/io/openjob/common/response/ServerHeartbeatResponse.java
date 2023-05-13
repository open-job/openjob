package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerHeartbeatResponse implements Serializable {

    /**
     * Worker address list.
     */
    private Set<String> workerAddressList;

    private ServerHeartbeatSystemResponse systemResponse;
}
