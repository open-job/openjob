package io.openjob.worker.container;

import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.JobProcessor;
import io.openjob.worker.processor.ProcessResult;
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
        this.start();
    }

    @Override
    public void start() {
        String uniqueId = "";

        // Running
        if (this.jobContext.getTaskAttempt() == 0) {

        }

        ProcessResult result = new ProcessResult(true);

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
            System.out.println(ex);
        }

        System.out.println(result);
    }

    @Override
    public void stop() {

    }
}
