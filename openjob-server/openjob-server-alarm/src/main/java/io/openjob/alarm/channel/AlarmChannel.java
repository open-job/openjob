package io.openjob.alarm.channel;

import io.openjob.alarm.dto.AlarmDTO;
import io.openjob.server.repository.constant.AlertMethodEnum;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public interface AlarmChannel {
    void send(AlarmDTO alarmDTO);

    AlertMethodEnum channel();
}
