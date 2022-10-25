package io.openjob.worker.appender;

import io.openjob.worker.constant.LogConstant;
import io.openjob.worker.dto.LogContentDTO;
import io.openjob.worker.util.LogUtil;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Throwables;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Plugin(name = "OpenjobLog4j2Appender", category = "Core", elementType = "appender", printObject = true)
public class Log4j2Appender extends AbstractAppender {
    private DateTimeFormatter formatter;

    /**
     * Log4j2Appender
     *
     * @param name             name
     * @param filter           filter
     * @param layout           layout
     * @param ignoreExceptions ignoreExceptions
     */
    public Log4j2Appender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        // Must use deprecated method.
        // Compatible with lower version.
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        LogContentDTO logContent = LogUtil.getLogContent();

        // Timezone
        DateTime dateTime = new DateTime(event.getTimeMillis());
        logContent.addTimeField(dateTime.toString(formatter));
        logContent.addTimeStamp(event.getTimeMillis());
        logContent.addLevelField(event.getLevel().name());
        logContent.addThreadField(event.getThreadName());

        String message = event.getMessage().getFormattedMessage();
        logContent.addMessageField(message);


        StackTraceElement source = event.getSource();
        if (source == null && (!event.isIncludeLocation())) {
            event.setIncludeLocation(true);
            source = event.getSource();
            event.setIncludeLocation(false);
        }
        String location = source == null ? "Unknown(Unknown Source)" : source.toString();
        logContent.addLocationField(location);

        String throwable = getThrowableStr(event.getThrown());
        if (throwable != null) {
            logContent.addThrowableField(throwable);
        }

        if (getLayout() != null) {
            logContent.addLogField(new String(getLayout().toByteArray(event)));
        } else {
            logContent.addLogField(message);
        }

        // Merge property map
        MDC.getCopyOfContextMap().forEach(logContent::addField);

        LogAppender.INSTANCE.append(logContent);
    }

    /**
     * Set time formatter.
     *
     * @param formatter formatter
     */
    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @PluginFactory
    public static Log4j2Appender create(
            @PluginAttribute("name") final String name,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("ignoreExceptions") final String ignore,
            @PluginAttribute("timeFormat") final String timeFormat,
            @PluginAttribute("timeZone") final String timeZone
    ) {
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, false);
        Log4j2Appender log4j2Appender = new Log4j2Appender(name, filter, layout, ignoreExceptions);
        String tf = Optional.ofNullable(timeFormat).orElse(LogConstant.DEFAULT_TIME_FORMAT);
        String tz = Optional.ofNullable(timeZone).orElse(LogConstant.DEFAULT_TIME_ZONE);

        log4j2Appender.setFormatter(DateTimeFormat.forPattern(tf).withZone(DateTimeZone.forID(tz)));
        return log4j2Appender;
    }

    private String getThrowableStr(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String s : Throwables.toStringList(throwable)) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
