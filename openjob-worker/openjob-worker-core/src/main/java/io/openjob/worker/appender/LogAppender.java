package io.openjob.worker.appender;

import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.task.LogTaskConsumer;
import io.openjob.common.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class LogAppender {
    public static final LogAppender INSTANCE = new LogAppender();

    protected TaskQueue<LogContentDTO> logQueue;

    protected LogTaskConsumer logTaskConsumer;

    private LogAppender() {
        logQueue = new TaskQueue<>(0L, 10240);
        logTaskConsumer = new LogTaskConsumer(
                0L,
                1,
                1,
                "Openjob-log-consumer",
                100,
                "Openjob-log-consumer-poll",
                logQueue
        );

        logTaskConsumer.start();
    }

    /**
     * Append content.
     *
     * @param logContentDTO logContentDTO
     */
    public void append(LogContentDTO logContentDTO) {
        try {
            logQueue.submit(logContentDTO);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
}
