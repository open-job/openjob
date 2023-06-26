package io.openjob.worker.processor;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import io.openjob.common.constant.ShellTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.dto.ShellProcessorDTO;
import io.openjob.common.util.JsonUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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
    protected String type;

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
        this.executorService.submit(new InputStreamRunnable(context, this.process.getInputStream(), this::processStdout));

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
        // Windows
        if (ShellTypeEnum.WINDOWS.getType().equals(this.type)) {
            Long processPid = -1L;
            try {
                processPid = this.getProcessPid(this.process);
                if (processPid > 0) {
                    this.stopProcess(processPid);
                }
                logger.info("Process stop success! pid={}", processPid);
                log.info("Process stop success! pid={}", processPid);
            } catch (Throwable throwable) {
                logger.error(String.format("Process stop failed! pid=%d", processPid), throwable);
                log.error(String.format("Process stop failed! pid=%d", processPid), throwable);
            }
        }

        try {
            // Process
            if (Objects.nonNull(this.process)) {
                this.process.destroy();
            }

            // Executor service
            if (Objects.nonNull(this.executorService)) {
                this.executorService.shutdown();
            }
        } catch (Throwable throwable) {
            logger.error("Processor stop failed!", throwable);
            log.error("Processor stop failed!", throwable);
        }
    }

    /**
     * Process stdout
     *
     * @param message message
     */
    protected void processStdout(String message) {
        logger.info(message);
    }

    /**
     * Parse command
     *
     * @param context context
     * @return string[]
     */
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

        this.type = processorDTO.getType();
        logger.info("Processor command={}", String.join(" ", params));
        log.info("Processor command={}", String.join(" ", params));
        return params.toArray(new String[0]);
    }

    /**
     * Get process pid
     *
     * @param process process
     * @return Long
     */
    @SuppressWarnings(value = "all")
    protected Long getProcessPid(Process process) {
        Long pid = -1L;
        try {
            //Windows
            if (process.getClass().getName().equals("java.lang.Win32Process") || process.getClass().getName().equals("java.lang.ProcessImpl")) {
                Field f = process.getClass().getDeclaredField("handle");
                f.setAccessible(true);
                long handl = f.getLong(process);
                Kernel32 kernel = Kernel32.INSTANCE;
                WinNT.HANDLE hand = new WinNT.HANDLE();
                hand.setPointer(Pointer.createConstant(handl));
                pid = (long) kernel.GetProcessId(hand);
                f.setAccessible(false);
            } else if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
                //Unix
                Field f = process.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                pid = f.getLong(process);
                f.setAccessible(false);
            }
        } catch (Throwable ex) {
            pid = -1L;
        }
        return pid;
    }

    /**
     * Stop process for unix
     *
     * @param pid pid
     * @throws IOException IOException
     */
    protected void stopProcess(Long pid) throws IOException {
        List<String> commandKill = new ArrayList<>();
        commandKill.add("taskkill");
        commandKill.add("/PID");
        commandKill.add(String.valueOf(pid));
        commandKill.add("/F");

        ProcessBuilder pbk = new ProcessBuilder(commandKill);
        Process stopProcess = pbk.start();
        BufferedReader readerKill = new BufferedReader(new InputStreamReader(stopProcess.getInputStream(), "GBK"));

        String line;
        while ((line = readerKill.readLine()) != null) {
            System.out.println("执行 taskkill 返回结果：" + line);
        }
    }

    protected static class InputStreamRunnable implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;
        private final JobContext context;

        public InputStreamRunnable(JobContext context, InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
            this.context = context;
        }

        @Override
        public void run() {
            // Init context
            ThreadLocalUtil.setJobContext(this.context);

            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                while (!Thread.currentThread().isInterrupted() && (line = br.readLine()) != null) {
                    this.consumer.accept(line);
                }
            } catch (Throwable e) {
                logger.error("ShellProcessor reader stream", e);
                log.error("ShellProcessor reader stream", e);
            } finally {
                // Clear context
                ThreadLocalUtil.removeJobContext();
            }
        }
    }
}
