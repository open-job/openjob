package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskLogRequest implements Serializable {
    private List<List<WorkerJobInstanceTaskLogFieldRequest>> fieldList;
}
