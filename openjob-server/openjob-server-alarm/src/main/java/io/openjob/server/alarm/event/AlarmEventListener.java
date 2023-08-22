package io.openjob.server.alarm.event;

import io.openjob.common.task.TaskQueue;
import io.openjob.server.alarm.dto.AlarmEventDTO;
import io.openjob.server.alarm.task.EventTaskConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class AlarmEventListener {
    private final TaskQueue<AlarmEventDTO> queue;

    @Autowired
    public AlarmEventListener() {
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

    /**
     * Alarm listener
     *
     * @param alarmEvent alarmEvent
     */
    @EventListener
    public void alarmListener(AlarmEvent alarmEvent) {
        try {
            AlarmEventDTO event = (AlarmEventDTO) alarmEvent.getSource();
            queue.submit(event);
        } catch (Throwable throwable) {
            log.error("Alarm event add failed!", throwable);
        }
    }
}
