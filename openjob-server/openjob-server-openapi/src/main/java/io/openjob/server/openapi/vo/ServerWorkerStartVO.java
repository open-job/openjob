package io.openjob.server.openapi.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class ServerWorkerStartVO {

    /**
     * App id
     */
    private Long appId;

    /**
     * App name
     */
    private String appName;

    /**
     * Online workers  and exclude start worker.
     */
    private Set<String> workerAddressList;
}
