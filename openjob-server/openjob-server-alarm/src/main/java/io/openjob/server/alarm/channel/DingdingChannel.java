package io.openjob.server.alarm.channel;

import io.openjob.common.util.DateUtil;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.alarm.request.DingdingRequest;
import io.openjob.server.alarm.response.DingdingResponse;
import io.openjob.server.repository.constant.AlertMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class DingdingChannel extends AbstractChannel {

    /**
     * Resource bundle
     */
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public DingdingChannel(CloseableHttpClient httpClient, ResourceBundleMessageSource resourceBundleMessageSource) {
        super(httpClient);
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @Override
    public void send(AlarmDTO alarmDTO) {
        try {
            Locale locale = this.getLocale(alarmDTO.getAlertRule());
            String[] contentArgs = this.getContentArgs(alarmDTO);
            String title = this.resourceBundleMessageSource.getMessage(alarmDTO.getEvent().getName(), null, locale);
            String[] bodyArgs = ArrayUtils.addFirst(contentArgs, title);
            String content = this.resourceBundleMessageSource.getMessage("alarm.dingding.content", bodyArgs, locale);
            this.doSend(alarmDTO.getAlertRule().getSecret(), alarmDTO.getAlertRule().getUrl(), title, content);
        } catch (Throwable e) {
            log.error("Dingding alarm failed!", e);
        }
    }

    @Override
    public AlertMethodEnum channel() {
        return AlertMethodEnum.DINGDING;
    }

    /**
     * @param url        url
     * @param headerTile headerTile
     * @param content    content
     */
    private void doSend(String secret, String url, String headerTile, String content) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Header and content
        DingdingRequest dingdingRequest = new DingdingRequest();
        dingdingRequest.getMarkdown().setTitle(headerTile);
        dingdingRequest.getMarkdown().setText(content);

        //Request
        Long now = DateUtil.milliLongTime();
        String requestUrl = String.format("%s&timestamp=%s&sign=%s", url, now, this.getSign(secret, now));
        String responseBody = this.postJson(requestUrl, JsonUtil.encode(dingdingRequest));

        // Response code
        DingdingResponse dingdingResponse = JsonUtil.decode(responseBody, DingdingResponse.class);
        if (!NumberUtils.INTEGER_ZERO.equals(dingdingResponse.getErrCode())) {
            throw new RuntimeException("Dingding send failed! response=" + responseBody);
        }
    }

    private String getSign(String secret, Long timestamp)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String signStr = String.format("%s\n%s", timestamp, secret);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }
}
