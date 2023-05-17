package io.openjob.worker.appender;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import io.openjob.worker.constant.LogConstant;
import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.util.LogUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class OpenjobLogbackAppender<E> extends UnsynchronizedAppenderBase<E> {
    protected String timeZone = LogConstant.DEFAULT_TIME_ZONE;
    protected String timeFormat = LogConstant.DEFAULT_TIME_FORMAT;

    protected Encoder<E> encoder;

    protected DateTimeFormatter formatter;

    @Override
    public void start() {
        this.formatter = DateTimeFormat.forPattern(this.timeFormat).withZone(DateTimeZone.forID(this.timeZone));
        super.start();
    }

    @Override
    protected void append(E eventObject) {
        if (!(eventObject instanceof LoggingEvent)) {
            return;
        }

        // Prepare processing
        ((LoggingEvent) eventObject).prepareForDeferredProcessing();

        LoggingEvent event = (LoggingEvent) eventObject;
        LogContentDTO logContent = LogUtil.getLogContent();

        // Not job context
        if (Objects.isNull(logContent)) {
            return;
        }

        // Timezone
        DateTime dateTime = new DateTime(event.getTimeStamp());
        logContent.addTimeField(dateTime.toString(formatter));
        logContent.addTimeStamp(event.getTimeStamp());
        logContent.addLevelField(event.getLevel().levelStr);
        logContent.addThreadField(event.getThreadName());
        logContent.addMessageField(event.getFormattedMessage());

        StackTraceElement[] caller = event.getCallerData();
        if (caller != null && caller.length > 0) {
            logContent.addLocationField(caller[0].toString());
        }

        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null) {
            String throwable = getExceptionInfo(iThrowableProxy);
            throwable += formatThrowable(event.getThrowableProxy().getStackTraceElementProxyArray());
            logContent.addThrowableField(throwable);
        }

        if (this.encoder != null) {
            logContent.addLogField(new String(this.encoder.encode(eventObject)));
        } else {
            logContent.addLogField(event.getMessage());
        }

        // Merge property map
        event.getMDCPropertyMap().forEach(logContent::addField);

        LogAppender.INSTANCE.append(logContent);
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

    private String formatThrowable(StackTraceElementProxy[] stackTraceElementProxyArray) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElementProxy step : stackTraceElementProxyArray) {
            builder.append(CoreConstants.LINE_SEPARATOR);
            String string = step.toString();
            builder.append(CoreConstants.TAB).append(string);
            ThrowableProxyUtil.subjoinPackagingData(builder, step);
        }
        return builder.toString();
    }

    private String getExceptionInfo(IThrowableProxy iThrowableProxy) {
        String s = iThrowableProxy.getClassName();
        String message = iThrowableProxy.getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}
