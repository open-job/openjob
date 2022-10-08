package io.openjob.worker.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayThreadTaskProcessor implements Runnable {

    protected JobContext jobContext;

    protected BaseProcessor baseProcessor;

    public DelayThreadTaskProcessor(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public void run() {
        // Init job context
        ThreadLocalUtil.setJobContext(this.jobContext);
        String workerAddress = "";

        ProcessResult result = new ProcessResult(false);
        try {
            this.baseProcessor = ProcessorUtil.getProcess(this.jobContext.getProcessorInfo());
            if (Objects.isNull(this.baseProcessor)) {
                throw new RuntimeException("Delay processor is not existed!");
            }

            result = this.baseProcessor.process(this.jobContext);
        } catch (Throwable ex) {
            result.setResult(ex.getMessage());
        } finally {
            this.reportTaskStatus(result, workerAddress);
        }
    }

    private void reportTaskStatus(ProcessResult result, String workerAddress) {

    }
}
