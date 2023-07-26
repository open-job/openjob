package io.openjob.server.alarm.channel;

import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.repository.constant.AlertMethodEnum;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Component
public class DingdingChannel implements AlarmChannel {
    @Override
    public void send(AlarmDTO alarmDTO) {

    }

    @Override
    public AlertMethodEnum channel() {
        return AlertMethodEnum.DINGDING;
    }
}
