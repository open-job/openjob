package io.openjob.alarm;

import io.openjob.alarm.dto.AlarmEventDTO;
import io.openjob.alarm.task.EventTaskConsumer;
import io.openjob.common.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class AlarmEvent {
    private static final TaskQueue<AlarmEventDTO> queue;

    static {
        queue = new TaskQueue<>(0L, 1024);
        EventTaskConsumer consumer = new EventTaskConsumer(
                0L,
                1,
                4,
                "Openjob-heartbeat-executor",
                1024,
                "Openjob-heartbeat-consumer",
                queue
        );
        consumer.start();
    }

    public static void add(AlarmEventDTO event) {
        try {
            queue.submit(event);
        } catch (Throwable throwable) {
            log.error("Alarm event add failed!", throwable);
        }
    }
}
