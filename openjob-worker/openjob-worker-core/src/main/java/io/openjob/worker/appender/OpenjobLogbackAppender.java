package io.openjob.worker.appender;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import io.openjob.common.request.WorkerJobInstanceTaskLogMessageRequest;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.util.ThreadLocalUtil;

import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class OpenjobLogbackAppender<E> extends UnsynchronizedAppenderBase<E> {
    protected Encoder<E> encoder;

    @Override
    public void start() {
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
        JobContext context = ThreadLocalUtil.getJobContext();

        WorkerJobInstanceTaskLogMessageRequest messageRequest = new WorkerJobInstanceTaskLogMessageRequest();
        messageRequest.setUniqueId(String.format("%d_%d_%d", context.getJobInstanceId(), context.getCircleId(), context.getTaskId()));
        messageRequest.setJobInstanceId(context.getJobInstanceId());
        messageRequest.setTaskId(context.getTaskId());
        messageRequest.setCircleId(context.getCircleId());

        // Timezone
        messageRequest.setTime(String.valueOf(event.getTimeStamp()));
        messageRequest.setLevel(event.getLevel().levelStr);
        messageRequest.setThread(event.getThreadName());
        messageRequest.setMessage(event.getFormattedMessage());

        StackTraceElement[] caller = event.getCallerData();
        if (caller != null && caller.length > 0) {
            messageRequest.setLocation(caller[0].toString());
        }

        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null) {
            String throwable = getExceptionInfo(iThrowableProxy);
            throwable += formatThrowable(event.getThrowableProxy().getStackTraceElementProxyArray());
            messageRequest.setThrowable(throwable);
        }

        if (this.encoder != null) {
            messageRequest.setLog(new String(this.encoder.encode(eventObject)));
        } else {
            messageRequest.setLog(event.getMessage());
        }

        // Merge property map
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        mdcPropertyMap.putAll(event.getLoggerContextVO().getPropertyMap());
        messageRequest.setPropertyMap(mdcPropertyMap);

        System.out.println(messageRequest);
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
