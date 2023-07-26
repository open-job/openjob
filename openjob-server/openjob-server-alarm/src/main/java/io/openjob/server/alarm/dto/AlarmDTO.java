package io.openjob.server.alarm.dto;

import io.openjob.server.repository.entity.AlertRule;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.Job;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Data
public class AlarmDTO {
    private AlertRule alertRule;
    private AlarmEventDTO event;
    private Job job;
    private Delay delay;
}
