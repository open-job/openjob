package io.openjob.server.alarm.channel;

import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.alarm.request.FeishuRequest;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.repository.constant.AlertMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class FeishuChannel implements AlarmChannel {

    private final CloseableHttpClient httpClient;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public FeishuChannel(CloseableHttpClient httpClient, ResourceBundleMessageSource resourceBundleMessageSource) {
        this.httpClient = httpClient;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
        System.out.println(resourceBundleMessageSource.getMessage("alarm.job.execute.timeout", null, Locale.CHINA));
    }

    @Override
    public void send(AlarmDTO alarmDTO) {
        try {
            String title = this.resourceBundleMessageSource.getMessage(alarmDTO.getEvent().getName(), null, Locale.CHINA);
            String content = this.resourceBundleMessageSource.getMessage("alarm.feishu.content", getContentArgs(alarmDTO), Locale.CHINA);
            this.doSend(alarmDTO.getAlertRule().getUrl(), title, "red", content);
        } catch (Throwable e) {
            log.error("Feishu alarm failed!", e);
        }
    }


    @Override
    public AlertMethodEnum channel() {
        return AlertMethodEnum.FEISHU;
    }

    /**
     * @param url         url
     * @param headerTile  headerTile
     * @param headerColor blue/yellow/green/red
     * @param content     content
     */
    private void doSend(String url, String headerTile, String headerColor, String content) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                // 200 ms
                .setConnectionRequestTimeout(100)
                // 1000ms
                .setSocketTimeout(1000)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        // Header
        FeishuRequest feishuRequest = new FeishuRequest();
        FeishuRequest.CardHeaderRequest header = feishuRequest.getCard().getHeader();
        header.setTemplate(headerColor);
        header.getTitle().setContent(headerTile);

        // Body
        FeishuRequest.CardElementRequest cardElementRequest = new FeishuRequest.CardElementRequest();
        cardElementRequest.setContent(content);
        feishuRequest.getCard().getElements().add(cardElementRequest);

        StringEntity entity = new StringEntity(JsonUtil.encode(feishuRequest), StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
    }

    private String[] getContentArgs(AlarmDTO alarmDTO) {
        String message = Optional.ofNullable(alarmDTO.getEvent().getMessage()).orElse("");
        if (AlarmEventEnum.isJobEvent(alarmDTO.getEvent().getName())) {
            return new String[]{
                    alarmDTO.getJob().getName(),
                    alarmDTO.getEvent().getInstanceId(),
                    alarmDTO.getJob().getProcessorInfo(),
                    String.valueOf(alarmDTO.getJob().getExecuteTimeout()),
                    message
            };
        }

        if (AlarmEventEnum.isDelayEvent(alarmDTO.getEvent().getName())) {
            return new String[]{
                    String.format("%s(%s)", alarmDTO.getDelay().getName(), alarmDTO.getDelay().getTopic()),
                    alarmDTO.getEvent().getInstanceId(),
                    alarmDTO.getDelay().getProcessorInfo(),
                    String.valueOf(alarmDTO.getJob().getExecuteTimeout()),
                    message
            };
        }
        return new String[]{};
    }
}
