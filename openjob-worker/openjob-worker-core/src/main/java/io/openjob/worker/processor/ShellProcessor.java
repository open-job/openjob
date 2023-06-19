package io.openjob.worker.processor;

import io.openjob.common.constant.ShellTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.dto.ShellProcessorDTO;
import io.openjob.common.util.JsonUtil;
import io.openjob.worker.context.JobContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class ShellProcessor implements JobProcessor {

    protected static final Logger logger = LoggerFactory.getLogger("openjob");
    protected ExecutorService executorService;
    protected Process process;

    @Override
    public void preProcess(JobContext context) {

    }

    @Override
    public ProcessResult process(JobContext context) throws IOException, InterruptedException {
        ProcessResult result = new ProcessResult(false);
        ProcessBuilder processBuilder = new ProcessBuilder(this.parseCommand(context));
        processBuilder.redirectErrorStream(true);
        this.process = processBuilder.start();

        // Process stream
        this.executorService = new ThreadPoolExecutor(
                1,
                1,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1),
                r -> new Thread(r, "Openjob-shell-stream")
        );

        // Input stream and error stream
        this.executorService.submit(new InputStreamRunnable(this.process.getInputStream(), this::processStdout));

        // Waiting
        if (this.process.waitFor() == 0) {
            result.setStatus(TaskStatusEnum.SUCCESS);
        }
        return result;
    }

    @Override
    public ProcessResult postProcess(JobContext context) {
        return null;
    }

    @Override
    public void stop(JobContext context) {
        if (Objects.nonNull(this.process)) {
            this.process.destroy();
        }

        if (Objects.nonNull(this.executorService)) {
            this.executorService.shutdown();
        }
    }

    protected void processStdout(String message) {
        logger.info(message);
    }

    protected String[] parseCommand(JobContext context) {
        List<String> params = new ArrayList<>();
        ShellProcessorDTO processorDTO = JsonUtil.decode(context.getProcessorInfo(), ShellProcessorDTO.class);

        if (ShellTypeEnum.UNIX.getType().equals(processorDTO.getType())) {
            // Unix
            params.add("/bin/sh");
            params.add("-c");
        } else {
            // Windows
            params.add("cmd.exe");
            params.add("/c");
        }

        params.add(processorDTO.getContent());
        if (Objects.nonNull(context.getShardingId())) {
            params.add(String.valueOf(context.getShardingId()));
            params.add(context.getShardingParam());
        }
        return params.toArray(new String[0]);
    }

    protected static class InputStreamRunnable implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;

        public InputStreamRunnable(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                while (!Thread.currentThread().isInterrupted() && (line = br.readLine()) != null) {
                    this.consumer.accept(line);
                }
            } catch (Throwable e) {
                logger.error("ShellProcessor reader stream", e);
            }
        }
    }
}
