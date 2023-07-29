package io.openjob.worker.container;

import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class ThreadTaskProcessor implements TaskProcessor, Runnable {
    private static final Logger logger = LoggerFactory.getLogger("openjob");

    protected JobContext jobContext;

    protected ProcessorHandler processorHandler;


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
            if (ProcessorTypeEnum.isProcessor(this.jobContext.getProcessorType())) {
                this.processorHandler = ProcessorUtil.getProcessor(this.jobContext.getProcessorInfo());
            } else {
                this.processorHandler = ProcessorUtil.getDefaultProcessor(this.jobContext.getProcessorType());
            }

            if (Objects.nonNull(this.processorHandler)) {
                this.processorHandler.preProcess(this.jobContext);
                result = this.processorHandler.process(this.jobContext);
                this.processorHandler.postProcess(this.jobContext);

                logger.info("Task processor completed! jobInstanceId={}", this.jobContext.getJobInstanceId());
                log.info("Task processor completed! jobInstanceId={}", this.jobContext.getJobInstanceId());
            } else {
                logger.error("Processor(jobInstanceId={} type={} processorInfo={}) can not find!",
                        this.jobContext.getJobInstanceId(), this.jobContext.getProcessorType(), this.jobContext.getProcessorInfo());
                log.error("Processor(jobInstanceId={} type={} processorInfo={}) can not find!",
                        this.jobContext.getJobInstanceId(), this.jobContext.getProcessorType(), this.jobContext.getProcessorInfo());
            }
        } catch (InterruptedException ex) {
            // Stop processor.
            this.stop();

            // Result
            result.setResult(ex.getMessage());
            logger.info("Processor is interrupted! jobInstanceId=" + this.jobContext.getJobInstanceId());
            log.info("Processor is interrupted! jobInstanceId=" + this.jobContext.getJobInstanceId());
        } catch (Throwable ex) {
            Throwable cause = Objects.nonNull(ex.getCause()) ? ex.getCause() : ex;

            result.setResult(getStackTraceAsString(cause));
            logger.error(String.format("Processor execute exception! jobInstanceId=%s", this.jobContext.getJobInstanceId()), cause);
            log.error(String.format("Processor execute exception! jobInstanceId=%s", this.jobContext.getJobInstanceId()), cause);
        } finally {
            // Remove job context
            ThreadLocalUtil.removeJobContext();

            // Report status
            this.reportTaskStatus(result, workerAddress);
        }
    }

    @Override
    public void stop() {
        if (Objects.nonNull(this.processorHandler)) {
            try {
                this.processorHandler.stop(this.jobContext);
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
        request.setFailStatus(FailStatusEnum.NONE.getStatus());
        request.setResult(result.getResult());

        TaskStatusReporter.report(request);
    }

    private String getStackTraceAsString(Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.getClass().getName()).append(": ").append(e.getMessage()).append("\n");
        Arrays.stream(e.getStackTrace()).forEach(s -> sb.append(s.toString()).append("\n"));
        return sb.toString();
    }
}
