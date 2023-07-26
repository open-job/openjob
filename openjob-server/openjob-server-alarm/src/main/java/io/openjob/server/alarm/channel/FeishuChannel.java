package io.openjob.server.alarm.channel;

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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class FeishuChannel implements AlarmChannel {

    private final CloseableHttpClient httpClient;

    @Autowired
    public FeishuChannel(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void send(AlarmDTO alarmDTO) {
        try {
            String body = String.format("任务：%s ", alarmDTO.getJob().getName()+"<br> 实例ID：%s", alarmDTO.getEvent().getInstanceId());
            this.doSend(alarmDTO.getAlertRule().getUrl(), "定时任务超时", "red", body);
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
}
