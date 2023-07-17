package io.openjob.worker.processor;

import io.openjob.common.constant.ShellTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.dto.ShellProcessorDTO;
import io.openjob.common.util.JsonUtil;
import io.openjob.worker.context.JobContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        if (!this.finalResult) {
            processResult.setStatus(TaskStatusEnum.FAILED);
        }
        return processResult;
    }

    @Override
    protected String[] parseCommand(JobContext context) {
        // Command params
        ShellProcessorDTO processorDTO = JsonUtil.decode(context.getProcessorInfo(), ShellProcessorDTO.class);
        List<String> params = parseDefaultCommand(processorDTO.getType());

        // Parse kettle params
        String[] cmdSplit = processorDTO.getContent().split(" ");
        params.addAll(Arrays.asList(cmdSplit));

        logger.info("Kettle command={}", String.join(" ", params));
        log.info("Kettle command={}", String.join(" ", params));
        return params.toArray(new String[0]);
    }

    @Override
    protected List<String> parseDefaultCommand(String type) {
        List<String> params = new ArrayList<>();
        if (ShellTypeEnum.UNIX.getType().equals(type)) {
            // Unix
            params.add("/bin/sh");
        } else {
            // Windows
            params.add("cmd.exe");
            params.add("/c");
        }

        this.type = type;
        return params;
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
