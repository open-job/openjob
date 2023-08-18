package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class WorkerInstanceTaskChildListPullResponse implements Serializable {

    /**
     * Task list.
     */
    private List<WorkerInstanceTaskResponse> taskList;
}
