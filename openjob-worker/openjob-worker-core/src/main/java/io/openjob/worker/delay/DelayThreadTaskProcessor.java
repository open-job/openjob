package io.openjob.worker.delay;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.request.WorkerDelayTaskRequest;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.init.WorkerConfig;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayThreadTaskProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger("openjob");

    protected JobContext jobContext;

    protected BaseProcessor baseProcessor;

    public DelayThreadTaskProcessor(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public void run() {
        // Init job context
        ThreadLocalUtil.setJobContext(this.jobContext);

        ProcessResult result = new ProcessResult(false);
        try {
            // Report running
            this.reportRunningTaskStatus();

            String taskId = this.jobContext.getDelayTaskId();
            String topic = this.jobContext.getDelayTopic();
            String processorInfo = this.jobContext.getProcessorInfo();
            if (StringUtils.isEmpty(processorInfo)) {
                throw new RuntimeException(String.format("Delay processor info can not be null! taskId=%s topic=%s", taskId, topic));
            }

            this.baseProcessor = ProcessorUtil.getProcess(processorInfo);
            if (Objects.isNull(this.baseProcessor)) {
                throw new RuntimeException(String.format("Delay processor is not exist! taskId=%s topic=%s", taskId, topic));
            }

            result = this.baseProcessor.process(this.jobContext);
            logger.info("Delay processor completed! taskId={}", this.jobContext.getDelayTaskId());
        } catch (InterruptedException ex) {
            logger.info("Delay processor is interrupted! taskId={}", this.jobContext.getDelayTaskId());
        } catch (Throwable ex) {
            logger.error(String.format("Delay processor run exception! taskId=%s", this.jobContext.getDelayTaskId()), ex);
            result.setResult(ex.getMessage());
        } finally {
            DelayDAO.INSTANCE.updatePullSizeById(this.jobContext.getDelayId(), 1);
            this.reportFinallyTaskStatus(result);

            // Remove from task manager
            DelayTaskManager.INSTANCE.remove(this.jobContext.getDelayTaskId());
        }
    }

    private void reportRunningTaskStatus() {
        WorkerDelayTaskRequest workerDelayTaskRequest = new WorkerDelayTaskRequest();
        workerDelayTaskRequest.setTopic(this.jobContext.getDelayTopic());
        workerDelayTaskRequest.setDelayId(this.jobContext.getDelayId());
        workerDelayTaskRequest.setTaskId(this.jobContext.getDelayTaskId());
        workerDelayTaskRequest.setStatus(TaskStatusEnum.RUNNING.getStatus());
        workerDelayTaskRequest.setWorkerAddress(WorkerConfig.getWorkerAddress());
        DelayStatusReporter.report(workerDelayTaskRequest);
    }

    private void reportFinallyTaskStatus(ProcessResult result) {
        WorkerDelayTaskRequest workerDelayTaskRequest = new WorkerDelayTaskRequest();
        workerDelayTaskRequest.setTopic(this.jobContext.getDelayTopic());
        workerDelayTaskRequest.setDelayId(this.jobContext.getDelayId());
        workerDelayTaskRequest.setTaskId(this.jobContext.getDelayTaskId());
        workerDelayTaskRequest.setStatus(result.getStatus().getStatus());
        workerDelayTaskRequest.setResult(result.getResult());
        workerDelayTaskRequest.setWorkerAddress(WorkerConfig.getWorkerAddress());
        DelayStatusReporter.report(workerDelayTaskRequest);
    }
}
