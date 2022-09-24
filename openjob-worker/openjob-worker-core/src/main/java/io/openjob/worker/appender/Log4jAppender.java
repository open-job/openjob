package io.openjob.worker.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class Log4jAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent loggingEvent) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
