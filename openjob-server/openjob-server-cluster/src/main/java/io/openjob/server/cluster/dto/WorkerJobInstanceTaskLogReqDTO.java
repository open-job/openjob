package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class WorkerJobInstanceTaskLogReqDTO {
    private List<List<WorkerJobInstanceTaskLogFieldReqDTO>> fieldList;

    @Data
    public static class WorkerJobInstanceTaskLogFieldReqDTO {
        private String name;
        private String value;
    }
}
