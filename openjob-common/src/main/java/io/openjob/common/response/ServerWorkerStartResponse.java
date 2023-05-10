package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerWorkerStartResponse implements Serializable {
    private Long appId;
    private String appName;
    private Set<String> workerAddressList;
}
