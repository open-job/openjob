package io.openjob.server.alarm.channel;

import io.openjob.common.util.JsonUtil;
import io.openjob.server.alarm.dto.AlarmDTO;
import io.openjob.server.alarm.request.WecomRequest;
import io.openjob.server.alarm.response.WecomResponse;
import io.openjob.server.repository.constant.AlertMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class WecomChannel extends AbstractChannel {

    /**
     * Resource bundle
     */
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public WecomChannel(CloseableHttpClient httpClient, ResourceBundleMessageSource resourceBundleMessageSource) {
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
            String content = this.resourceBundleMessageSource.getMessage("alarm.wecom.content", bodyArgs, locale);
            this.doSend(alarmDTO.getAlertRule().getUrl(), content);
        } catch (Throwable e) {
            log.error("Wecom alarm failed!", e);
        }
    }

    @Override
    public AlertMethodEnum channel() {
        return AlertMethodEnum.WECOM;
    }

    /**
     * @param url     url
     * @param content content
     */
    private void doSend(String url, String content) throws IOException {
        // Content
        WecomRequest wecomRequest = new WecomRequest();
        wecomRequest.getMarkdown().setContent(content);

        //Request
        String responseBody = this.postJson(url, JsonUtil.encode(wecomRequest));

        // Response code
        WecomResponse wecomResponse = JsonUtil.decode(responseBody, WecomResponse.class);
        if (!NumberUtils.INTEGER_ZERO.equals(wecomResponse.getErrCode())) {
            throw new RuntimeException("Wecom send failed! response=" + responseBody);
        }
    }
}
