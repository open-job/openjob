package io.openjob.server.alarm.channel;

import io.openjob.common.constant.StatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.repository.entity.AlertRule;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public abstract class AbstractChannel implements AlarmChannel {

    /**
     * Http client
     */
    protected final CloseableHttpClient httpClient;

    protected AbstractChannel(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected String[] getContentArgs(AlarmDTO alarmDTO) {
        String message = Optional.ofNullable(alarmDTO.getEvent().getMessage()).orElse("");
        if (AlarmEventEnum.isJobEvent(alarmDTO.getEvent().getName())) {
            return new String[]{
                    alarmDTO.getApp().getName(),
                    alarmDTO.getJob().getName(),
                    alarmDTO.getEvent().getInstanceId(),
                    String.valueOf(alarmDTO.getJob().getExecuteTimeout()),
                    alarmDTO.getJob().getProcessorInfo(),
                    DateUtil.formatTimestamp(DateUtil.milliLongTime()),
                    message
            };
        }

        if (AlarmEventEnum.isDelayEvent(alarmDTO.getEvent().getName())) {
            return new String[]{
                    alarmDTO.getApp().getName(),
                    String.format("%s(%s)", alarmDTO.getDelay().getName(), alarmDTO.getDelay().getTopic()),
                    alarmDTO.getEvent().getInstanceId(),
                    String.valueOf(alarmDTO.getJob().getExecuteTimeout()),
                    alarmDTO.getDelay().getProcessorInfo(),
                    DateUtil.formatTimestamp(DateUtil.milliLongTime()),
                    message
            };
        }
        return new String[]{};
    }

    protected String postJson(String url, String body) throws IOException {
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

        // Execute
        StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        // Response data.
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        int statusCode = response.getStatusLine().getStatusCode();
        response.close();

        // Http code
        if (!StatusEnum.SUCCESS.getStatus().equals(statusCode)) {
            throw new RuntimeException(String.format("Feishu post failed! status=%d %s", statusCode, responseBody));
        }

        return responseBody;
    }

    /**
     * Get locale
     *
     * @param alertRule alertRule
     * @return Locale
     */
    protected Locale getLocale(AlertRule alertRule) {
        String locale = alertRule.getLocale();
        String[] splitLocale = locale.split("_");
        if (splitLocale.length != 2) {
            return Locale.US;
        }

        return new Locale(splitLocale[0], splitLocale[1]);
    }
}
