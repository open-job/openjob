package io.openjob.server.alarm.event;

import io.openjob.server.alarm.dto.AlarmEventDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public class AlarmEvent extends ApplicationEvent {
    public AlarmEvent(AlarmEventDTO alarmEventDTO) {
        super(alarmEventDTO);
    }
}
