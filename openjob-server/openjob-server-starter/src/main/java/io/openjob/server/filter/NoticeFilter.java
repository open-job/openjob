package io.openjob.server.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class NoticeFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (Level.TRACE.equals(event.getLevel()) || Level.INFO.equals(event.getLevel())) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
