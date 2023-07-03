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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        this.executorService.submit(new InputStreamRunnable(context, this.process.getInputStream(), this));

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
        // Stop child threads
        this.stopChildThreads();

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
        // Command params
        ShellProcessorDTO processorDTO = JsonUtil.decode(context.getProcessorInfo(), ShellProcessorDTO.class);
        List<String> params = parseDefaultCommand(processorDTO.getType());

        // Content and sharding params
        params.add(processorDTO.getContent());
        if (Objects.nonNull(context.getShardingId())) {
            params.add(String.valueOf(context.getShardingId()));
            params.add(context.getShardingParam());
        }

        logger.info("Processor command={}", String.join(" ", params));
        log.info("Processor command={}", String.join(" ", params));
        return params.toArray(new String[0]);
    }

    protected List<String> parseDefaultCommand(String type) {
        List<String> params = new ArrayList<>();
        if (ShellTypeEnum.UNIX.getType().equals(type)) {
            // Unix
            params.add("/bin/sh");
            params.add("-c");
        } else {
            // Windows
            params.add("cmd.exe");
            params.add("/c");
        }

        this.type = type;
        return params;
    }

    /**
     * Stop child threads
     */
    protected void stopChildThreads() {
        Long processPid = -1L;
        try {
            // Obtain process pid failed!
            processPid = this.getProcessPid(this.process);
            if (processPid < 0) {
                logger.warn("Obtain process pid failed!");
                log.warn("Obtain process pid failed!");
                return;
            }

            if (ShellTypeEnum.WINDOWS.getType().equals(this.type)) {
                // Windows
                this.stopProcessForWindows(processPid);
            } else {
                // unix
                this.stopProcessForUnix(processPid);
            }
            logger.info("Process stop {} success! pid={}", this.type, processPid);
            log.info("Process stop {} success! pid={}", this.type, processPid);
        } catch (Throwable throwable) {
            logger.error(String.format("Process stop failed! pid=%d", processPid), throwable);
            log.error(String.format("Process stop failed! pid=%d", processPid), throwable);
        }
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
     * Stop process for windows
     *
     * @param pid pid
     * @throws IOException IOException
     */
    protected void stopProcessForWindows(Long pid) throws IOException {
        List<String> commandKill = new ArrayList<>();
        commandKill.add("taskkill");
        commandKill.add("/PID");
        commandKill.add(String.valueOf(pid));
        commandKill.add("/F");
        commandKill.add("/T");

        ProcessBuilder pbk = new ProcessBuilder(commandKill);
        Process stopProcess = pbk.start();
        BufferedReader readerKill = new BufferedReader(this.getInputStreamReader(stopProcess.getInputStream()));

        String line;
        while ((line = readerKill.readLine()) != null) {
            logger.info("Stop windows result {}", line);
            log.info("Stop windows result {}", line);
        }
    }

    /**
     * Stop process for unix
     *
     * @param pid pid
     * @throws IOException IOException
     */
    protected void stopProcessForUnix(Long pid) throws IOException {
        List<String> commandKill = new ArrayList<>();
        commandKill.add("/bin/sh");
        commandKill.add("-c");
        commandKill.add("kill");
        commandKill.add("-9");
        commandKill.add(String.format("-%d", pid));
        ProcessBuilder pbk = new ProcessBuilder(commandKill);
        Process stopProcess = pbk.start();
        BufferedReader readerKill = new BufferedReader(this.getInputStreamReader(stopProcess.getInputStream()));

        String line;
        while ((line = readerKill.readLine()) != null) {
            logger.info("Stop unix result {}", line);
            log.info("Stop unix result {}", line);
        }
    }

    /**
     * Get input stream reader
     *
     * @param inputStream inputStream
     * @return InputStreamReader
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    protected InputStreamReader getInputStreamReader(InputStream inputStream) throws UnsupportedEncodingException {
        if (ShellTypeEnum.WINDOWS.getType().equals(this.type)) {
            return new InputStreamReader(inputStream, "GBK");
        }

        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

    protected static class InputStreamRunnable implements Runnable {
        private final JobContext context;
        private final InputStream inputStream;
        private final ShellProcessor shellProcessor;

        public InputStreamRunnable(JobContext context, InputStream inputStream, ShellProcessor shellProcessor) {
            this.inputStream = inputStream;
            this.context = context;
            this.shellProcessor = shellProcessor;
        }

        @Override
        public void run() {
            // Init context
            ThreadLocalUtil.setJobContext(this.context);

            try {
                String line;
                BufferedReader br = new BufferedReader(this.shellProcessor.getInputStreamReader(this.inputStream));
                while (!Thread.currentThread().isInterrupted() && (line = br.readLine()) != null) {
                    this.shellProcessor.processStdout(line);
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
