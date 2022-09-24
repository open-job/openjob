package io.openjob.worker.appender;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Plugin(name = "Log4j2Appender", category = "Core", elementType = "appender", printObject = true)
public class Log4j2Appender extends AbstractAppender {
    protected Log4j2Appender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent event) {

    }
}
