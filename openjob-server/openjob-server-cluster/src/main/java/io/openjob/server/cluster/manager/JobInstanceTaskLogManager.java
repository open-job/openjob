package io.openjob.server.cluster.manager;

import com.google.common.collect.Lists;
import io.openjob.common.constant.LogFieldConstant;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskLogReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskLogRespDTO;
import io.openjob.server.cluster.executor.WorkerTaskLogExecutor;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Slf4j
@Component
public class JobInstanceTaskLogManager {
    private final LogDAO logDAO;
    private final WorkerTaskLogExecutor executor;

    @Autowired
    public JobInstanceTaskLogManager(LogDAO logDAO, WorkerTaskLogExecutor executor) {
        this.logDAO = logDAO;
        this.executor = executor;
    }

    /**
     * Handle instance log.
     *
     * @param logReq log request.
     */
    public WorkerJobInstanceTaskLogRespDTO handleInstanceTaskLog(WorkerJobInstanceTaskLogReqDTO logReq) {
        this.executor.submit(logReq);
        return new WorkerJobInstanceTaskLogRespDTO();
    }

    /**
     * Batch instance log
     *
     * @param requests requests
     */
    public void batchInstanceTaskLog(List<WorkerJobInstanceTaskLogReqDTO> requests) {
        List<ProcessorLogDTO> processorLogList = requests.stream().flatMap(r -> r.getFieldList().stream().map(fields -> {
            // Field map.
            Map<String, List<WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO>> fieldMap = fields.stream()
                    .collect(Collectors.groupingBy(WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO::getName));

            // Task id.
            String taskId = Optional.ofNullable(fieldMap.get(LogFieldConstant.TASK_ID))
                    .orElseGet(() -> Lists.newArrayList(new WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO()))
                    .get(0).getValue();

            // Worker address.
            String workerAddress = Optional.ofNullable(fieldMap.get(LogFieldConstant.WORKER_ADDRESS))
                    .orElseGet(() -> Lists.newArrayList(new WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO()))
                    .get(0).getValue();

            // Time
            String timeStamp = Optional.ofNullable(fieldMap.get(LogFieldConstant.TIME_STAMP))
                    .orElseGet(() -> Lists.newArrayList(new WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO()))
                    .get(0).getValue();

            ProcessorLogDTO processorLog = new ProcessorLogDTO();
            processorLog.setTaskId(taskId);
            processorLog.setWorkerAddress(workerAddress);
            processorLog.setTime(Long.valueOf(timeStamp));
            processorLog.setFields(BeanMapperUtil.mapList(fields, WorkerJobInstanceTaskLogReqDTO.WorkerJobInstanceTaskLogFieldReqDTO.class, ProcessorLogFieldDTO.class));
            return processorLog;
        })).collect(Collectors.toList());

        try {
            logDAO.batchAdd(processorLogList);
        } catch (Exception e) {
            log.error("Batch add task log failed!", e);
            throw new RuntimeException(e);
        }

    }
}
