package io.openjob.server.alarm.channel;

import io.openjob.common.util.DateUtil;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.alarm.request.WebhookRequest;
import io.openjob.server.alarm.response.WebhookResponse;
import io.openjob.server.repository.constant.AlertMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class WebhookChannel extends AbstractChannel {
    @Autowired
    protected WebhookChannel(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public void send(AlarmDTO alarmDTO) {
        try {
            this.doSend(alarmDTO.getAlertRule().getUrl(), alarmDTO);
        } catch (Throwable throwable) {
            log.error("Wecom alarm failed!", throwable);
        }
    }

    @Override
    public AlertMethodEnum channel() {
        return AlertMethodEnum.WEBHOOK;
    }

    private void doSend(String url, AlarmDTO alarmDTO) throws IOException {
        // Content
        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setAppName(alarmDTO.getApp().getName());
        webhookRequest.setProcessor(alarmDTO.getEvent().getName());
        webhookRequest.setMessage(Optional.ofNullable(alarmDTO.getEvent().getMessage()).orElse(""));
        webhookRequest.setTimestamp(DateUtil.timestamp());
        webhookRequest.setJobId(alarmDTO.getEvent().getJobUniqueId());
        webhookRequest.setJobInstanceId(alarmDTO.getEvent().getInstanceId());
        if (AlarmEventEnum.isJobEvent(alarmDTO.getEvent().getName())) {
            webhookRequest.setJobName(alarmDTO.getJob().getName());
            webhookRequest.setProcessor(alarmDTO.getJob().getProcessorInfo());
        } else if (AlarmEventEnum.isDelayEvent(alarmDTO.getEvent().getName())) {
            webhookRequest.setJobName(alarmDTO.getDelay().getName());
            webhookRequest.setProcessor(alarmDTO.getDelay().getProcessorInfo());
        } else {
            throw new RuntimeException("Event not supported! event=" + alarmDTO.getEvent().getName());
        }

        //Request
        String responseBody = this.postJson(url, JsonUtil.encode(webhookRequest));

        // Response code
        WebhookResponse response = JsonUtil.decode(responseBody, WebhookResponse.class);
        if (!NumberUtils.INTEGER_ZERO.equals(response.getCode())) {
            throw new RuntimeException("Feishu send failed! response=" + responseBody);
        }
    }
}
