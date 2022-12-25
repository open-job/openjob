package io.openjob.worker.task;

import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.common.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class LogTaskConsumer extends BaseConsumer<LogContentDTO> {

    public LogTaskConsumer(Long id,
                           Integer consumerCoreThreadNum,
                           Integer consumerMaxThreadNum,
                           String consumerThreadName,
                           Integer pollSize,
                           String pollThreadName,
                           TaskQueue<LogContentDTO> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues);
    }

    @Override
    public void consume(Long id, List<LogContentDTO> tasks) {
        this.consumerExecutor.submit(new LogTaskRunnable(tasks));
    }

    private static class LogTaskRunnable implements Runnable {
        private final List<LogContentDTO> contentList;

        public LogTaskRunnable(List<LogContentDTO> contentList) {
            this.contentList = contentList;
        }

        @Override
        public void run() {
            Map<String, List<LogContentDTO>> logContentMap = this.contentList.stream()
                    .collect(Collectors.groupingBy(LogContentDTO::getUniqueId));

            for (Map.Entry<String, List<LogContentDTO>> entry : logContentMap.entrySet()) {
                WorkerJobInstanceTaskLogRequest logRequest = new WorkerJobInstanceTaskLogRequest();
                List<LogContentDTO> logList = entry.getValue();
                LogContentDTO first = logList.get(0);

                logRequest.setJobId(first.getJobId());
                logRequest.setJobInstanceId(first.getJobInstanceId());
                logRequest.setCircleId(first.getCircleId());
                logRequest.setTaskId(first.getTaskId());
                logRequest.setWorkerAddress(first.getWorkerAddress());

                List<List<WorkerJobInstanceTaskLogFieldRequest>> fieldList = logList.stream()
                        .map(LogContentDTO::getFieldList)
                        .collect(Collectors.toList());
                logRequest.setFieldList(fieldList);
                try {
                    WorkerUtil.getServerWorkerJobInstanceTaskLogActor().tell(logRequest, null);
                } catch (Throwable throwable) {
                    log.error("Log appender error", throwable);
                }
            }
        }
    }
}
