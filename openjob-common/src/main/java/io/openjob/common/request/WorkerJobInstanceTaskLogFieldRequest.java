package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogFieldRequest implements Serializable {
    private String name;
    private String value;

    public WorkerJobInstanceTaskLogFieldRequest() {
    }

    public WorkerJobInstanceTaskLogFieldRequest(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
