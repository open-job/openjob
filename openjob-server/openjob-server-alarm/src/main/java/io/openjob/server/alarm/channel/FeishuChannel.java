package io.openjob.server.alarm.channel;

import io.openjob.common.constant.StatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.alarm.request.FeishuRequest;
import io.openjob.server.alarm.response.FeishuResponse;
import io.openjob.server.repository.constant.AlertMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class FeishuChannel extends AbstractChannel {

    /**
     * Header color map
     */
    private static final Map<String, String> COLOR_MAP = new HashMap<String, String>() {{
        put(AlarmEventEnum.JOB_EXECUTE_FAIL.getEvent(), "red");
        put(AlarmEventEnum.JOB_DISCARD.getEvent(), "yellow");
        put(AlarmEventEnum.JOB_EXECUTE_TIMEOUT.getEvent(), "red");
        put(AlarmEventEnum.JOB_REACH_RETRY_TIMES.getEvent(), "yellow");

        put(AlarmEventEnum.DELAY_EXECUTE_FAIL.getEvent(), "red");
        put(AlarmEventEnum.DELAY_EXECUTE_TIMEOUT.getEvent(), "red");
        put(AlarmEventEnum.DELAY_TASK_IGNORE.getEvent(), "yellow");
    }};

    /**
     * Http client
     */
    private final CloseableHttpClient httpClient;

    /**
     * Resource bundle
     */
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
            Locale locale = this.getLocale(alarmDTO.getAlertRule());
            String title = this.resourceBundleMessageSource.getMessage(alarmDTO.getEvent().getName(), null, locale);
            String content = this.resourceBundleMessageSource.getMessage("alarm.feishu.content", getContentArgs(alarmDTO), locale);
            this.doSend(alarmDTO.getAlertRule().getSecret(), alarmDTO.getAlertRule().getUrl(), title, this.getHeaderColor(alarmDTO.getEvent().getName()), content);
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
    private void doSend(String secret, String url, String headerTile, String headerColor, String content) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
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


        // Sign
        Long now = DateUtil.timestamp();
        FeishuRequest feishuRequest = new FeishuRequest();
        feishuRequest.setTimestamp(String.valueOf(now));
        feishuRequest.setSign(this.getSign(secret, now));

        // Header
        FeishuRequest.CardHeaderRequest header = feishuRequest.getCard().getHeader();
        header.setTemplate(headerColor);
        header.getTitle().setContent(headerTile);

        // Body
        FeishuRequest.CardElementRequest cardElementRequest = new FeishuRequest.CardElementRequest();
        cardElementRequest.setContent(content);
        feishuRequest.getCard().getElements().add(cardElementRequest);

        // Execute
        StringEntity entity = new StringEntity(JsonUtil.encode(feishuRequest), StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        // Response data.
        String body = EntityUtils.toString(response.getEntity(), "UTF-8");
        int statusCode = response.getStatusLine().getStatusCode();
        response.close();

        // Http code
        if (!StatusEnum.SUCCESS.getStatus().equals(statusCode)) {
            throw new RuntimeException(String.format("Feishu post failed! status=%d %s", statusCode, body));
        }

        // Response code
        FeishuResponse feishuResponse = JsonUtil.decode(body, FeishuResponse.class);
        if (!NumberUtils.INTEGER_ZERO.equals(feishuResponse.getCode())) {
            throw new RuntimeException("Feishu send failed! response=" + body);
        }
    }

    private String[] getContentArgs(AlarmDTO alarmDTO) {
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

    private String getHeaderColor(String eventName) {
        return Optional.ofNullable(COLOR_MAP.get(eventName)).orElse("red");
    }

    private String getSign(String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String signStr = String.format("%s\n%s", timestamp, secret);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signStr.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[]{});
        return new String(Base64.encodeBase64(signData));
    }
}
