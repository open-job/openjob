package io.openjob.worker.appender;

import io.openjob.worker.constant.LogConstant;
import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.util.LogUtil;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class Log4jAppender extends AppenderSkeleton {
    protected String timeZone = LogConstant.DEFAULT_TIME_ZONE;

    protected String timeFormat = LogConstant.DEFAULT_TIME_FORMAT;

    protected DateTimeFormatter formatter;


    @Override
    public void activateOptions() {
        super.activateOptions();
        this.formatter = DateTimeFormat.forPattern(this.timeFormat).withZone(DateTimeZone.forID(this.timeZone));
    }

    @Override
    protected void append(LoggingEvent event) {
        LogContentDTO logContent = LogUtil.getLogContent();

        // Timezone
        DateTime dateTime = new DateTime(event.getTimeStamp());
        logContent.addTimeField(dateTime.toString(formatter));
        logContent.addTimeStamp(event.getTimeStamp());
        logContent.addLevelField(event.getLevel().toString());
        logContent.addThreadField(event.getThreadName());
        String message = Objects.nonNull(event.getMessage()) ? event.getMessage().toString() : "";
        logContent.addMessageField(message);
        logContent.addLocationField(event.getLocationInformation().fullInfo);

        String throwable = getThrowableStr(event);
        if (throwable != null) {
            logContent.addThrowableField(throwable);
        }

        if (getLayout() != null) {
            logContent.addLogField(getLayout().format(event));
        } else {
            logContent.addLogField(message);
        }

        event.getProperties().forEach((k, v) -> logContent.addField((String) k, (String) v));
        LogAppender.INSTANCE.append(logContent);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    private String getThrowableStr(LoggingEvent event) {
        ThrowableInformation throwable = event.getThrowableInformation();
        if (throwable == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String s : throwable.getThrowableStrRep()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Inject timezone
     *
     * @param timeZone timeZone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Inject timeFormat
     *
     * @param timeFormat timeFormat
     */
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
