package io.openjob.common.response;

import io.openjob.common.request.WorkerJobInstanceTaskRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkerInstanceTaskResponse extends WorkerJobInstanceTaskRequest implements Serializable {
}
