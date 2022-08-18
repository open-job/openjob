package io.openjob.worker.container;

import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.JobProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.util.ProcessorUtil;

import java.util.Objects;


/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskContainer extends BaseTaskContainer implements Runnable {
    private BaseProcessor baseProcessor;

    public ThreadTaskContainer(JobContext jobContext) {
        super(jobContext);
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void start() throws InterruptedException {
        TaskContainerFactory.getPool().setJobContext(jobContext);
        String uniqueId = "";

        // Running
        if (this.jobContext.getFailAttemptTimes() == 0) {

        }

        ProcessResult result = new ProcessResult(false);

        try {
            // Java
            if (ProcessorTypeEnum.JAVA.getType().equals(this.jobContext.getProcessorType())) {
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
            }
        } catch (Throwable ex) {
            result.setResult(ex.getMessage());
        } finally {
            this.reportTaskStatus(result);
        }
    }

    private void reportTaskStatus(ProcessResult result) throws InterruptedException {
        String workerAddress = "";
        ContainerTaskStatusRequest request = new ContainerTaskStatusRequest();
        request.setJobId(this.jobContext.getJobId());
        request.setJobInstanceId(this.jobContext.getJobInstanceId());
        request.setTaskId(this.jobContext.getTaskId());
        request.setWorkerAddress(workerAddress);
        request.setMasterActorPath(this.jobContext.getMasterActorPath());
        request.setStatus(result.getStatus().getStatus());
        request.setResult(result.getResult());

        ContainerTaskStatusManager.submit(request);
    }

    @Override
    public void stop() {

    }
}
