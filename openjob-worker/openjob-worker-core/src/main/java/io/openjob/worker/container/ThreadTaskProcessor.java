package io.openjob.worker.container;

import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.JobProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskProcessor implements TaskProcessor, Runnable {
    private static final Logger logger = LoggerFactory.getLogger("openjob");

    protected JobContext jobContext;

    protected BaseProcessor baseProcessor;


    public ThreadTaskProcessor(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public void run() {
        this.start();
    }

    /**
     * Start
     */
    @Override
    public void start() {
        // Init job context
        ThreadLocalUtil.setJobContext(this.jobContext);

        String workerAddress = "";

        // Running
        if (this.jobContext.getFailAttemptTimes() == 0) {
            this.reportTaskStatus(new ProcessResult(TaskStatusEnum.RUNNING), workerAddress);
        }

        ProcessResult result = new ProcessResult(false);

        try {
            // Java
            if (ProcessorTypeEnum.PROCESSOR.getType().equals(this.jobContext.getProcessorType())) {
                this.baseProcessor = ProcessorUtil.getProcess(this.jobContext.getProcessorInfo());
            }

            if (Objects.nonNull(this.baseProcessor)) {
                if (this.baseProcessor instanceof JobProcessor) {
                    JobProcessor jobProcessor = (JobProcessor) this.baseProcessor;

                    jobProcessor.preProcess(this.jobContext);
                    result = this.baseProcessor.process(this.jobContext);
                    jobProcessor.postProcess(this.jobContext);
                } else {
                    result = this.baseProcessor.process(this.jobContext);
                }

                logger.info("Task processor completed!");
            } else {
                logger.error("Processor({}) can not find!", this.jobContext.getProcessorInfo());
            }
        } catch (InterruptedException ex) {
            // Stop processor.
            this.stop();
        } catch (Throwable ex) {
            result.setResult(ex.getMessage());
            logger.error("Processor execute exception!{}", ex.getMessage());
        } finally {
            this.reportTaskStatus(result, workerAddress);
        }
    }

    @Override
    public void stop() {
        if (Objects.nonNull(this.baseProcessor)) {
            try {
                this.baseProcessor.stop(this.jobContext);
                logger.info("Task processor stopped!");
            } catch (Throwable ex) {
                logger.error("Processor stop exception!", ex);
            }
        }
    }

    private void reportTaskStatus(ProcessResult result, String workerAddress) {
        ContainerTaskStatusRequest request = new ContainerTaskStatusRequest();
        request.setJobId(this.jobContext.getJobId());
        request.setJobInstanceId(this.jobContext.getJobInstanceId());
        request.setTaskId(this.jobContext.getTaskId());
        request.setWorkerAddress(workerAddress);
        request.setMasterActorPath(this.jobContext.getMasterActorPath());
        request.setStatus(result.getStatus().getStatus());
        request.setResult(result.getResult());

        TaskStatusReporter.report(request);
    }
}
