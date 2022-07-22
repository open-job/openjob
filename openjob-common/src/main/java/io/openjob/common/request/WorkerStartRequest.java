package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerStartRequest implements Serializable {

    private String workerKey;

    private String appName;

    private String address;

    private String version;

    private String protocolType;

    private Metric metric;

    @Data
    public static class Metric {

    }
}

