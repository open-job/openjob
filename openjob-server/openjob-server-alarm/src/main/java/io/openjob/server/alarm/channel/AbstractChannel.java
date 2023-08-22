package io.openjob.server.alarm.channel;

import io.openjob.common.constant.StatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.repository.entity.AlertRule;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public abstract class AbstractChannel implements AlarmChannel {

    /**
     * Locale size
     */
    protected static final Integer LOCALE_SIZE = 2;

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
                    String.valueOf(alarmDTO.getDelay().getExecuteTimeout()),
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
                .setConnectionRequestTimeout(1000)
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

    protected String getFeishuOrWebhookSign(String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String signStr = String.format("%s\n%s", timestamp, secret);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signStr.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[]{});
        return new String(Base64.encodeBase64(signData));
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
        if (splitLocale.length != LOCALE_SIZE) {
            return Locale.US;
        }

        return new Locale(splitLocale[0], splitLocale[1]);
    }
}
