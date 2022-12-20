package io.openjob.worker.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
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
        } catch (Throwable ex) {
            log.error("Delay processor run failed!", ex);
            result.setResult(ex.getMessage());
        } finally {
            DelayDAO.INSTANCE.updatePullSizeById(this.jobContext.getDelayId(), 1);
            this.reportTaskStatus(result, workerAddress);
        }
    }

    private void reportTaskStatus(ProcessResult result, String workerAddress) {

    }
}
