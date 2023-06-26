package io.openjob.worker.processor;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
@Slf4j
public class KettleProcessor extends ShellProcessor {
    private Boolean finalResult = true;

    @Override
    public ProcessResult process(JobContext context) throws IOException, InterruptedException {
        ProcessResult processResult = super.process(context);
        if (this.finalResult) {
            processResult.setStatus(TaskStatusEnum.FAILED);
        }
        return processResult;
    }

    @Override
    protected void processStdout(String message) {
        // Pattern
        Pattern pattern = Pattern.compile("Java heap space|OutOfMemoryError|Unknown database|ORA-[0-9]{1,10}:|"
                + "ora-[0-9]{1,10}:|结果=\\[false]|执行该作业项时发生了一个错误|转换正在杀死其他步骤!|错误被检测到!|"
                + "错误初始化步骤|Kitchen - ERROR|Pan - ERROR|Error occurred while trying to connect to the database|"
                + "无法初始化至少一个步骤\\. {2}执行无法开始!|We failed to initialize at least one step\\. {2}Execution can not begin!|"
                + "Error initializing step|failed to initialize!|Errors detected!|result=\\[false]|"
                + "Transformation is killing the other steps!");
        Matcher matcher = pattern.matcher(message);

        // Match
        if (matcher.find()) {
            logger.error("Kettle final result is failed! result={}", message);
            log.error("Kettle final result is failed! result={}", message);
            finalResult = false;
        }
        super.processStdout(message);
    }
}
