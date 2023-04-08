package io.openjob.worker.processor;

import com.google.common.collect.Lists;
import io.openjob.worker.context.JobContext;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessorHandler implements JobProcessor {
    private BaseProcessor baseProcessor;
    private Object target;
    private Method processMethod;
    private Method preMethod;
    private Method postMethod;
    private Method stopMethod;

    public ProcessorHandler() {
    }

    public ProcessorHandler(BaseProcessor baseProcessor) {
        this.baseProcessor = baseProcessor;
    }

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        // Implement interface
        if (Objects.nonNull(this.baseProcessor)) {
            return this.baseProcessor.process(context);
        }

        return (ProcessResult) this.processMethod.invoke(this.target, this.getMethodParams(this.processMethod, context));
    }

    @Override
    public void stop(JobContext context) throws Exception {
        // Implement interface
        if (Objects.nonNull(this.baseProcessor)) {
            this.baseProcessor.stop(context);
            return;
        }

        if (Objects.isNull(this.stopMethod)) {
            return;
        }
        this.stopMethod.invoke(this.target, this.getMethodParams(this.stopMethod, context));
    }

    @Override
    public void preProcess(JobContext context) throws Exception {
        // Implement interface
        if (Objects.nonNull(this.baseProcessor) && this.baseProcessor instanceof JobProcessor) {
            ((JobProcessor) this.baseProcessor).preProcess(context);
            return;
        }

        if (Objects.isNull(this.preMethod)) {
            return;
        }
        this.preMethod.invoke(this.target, this.getMethodParams(this.preMethod, context));
    }

    @Override
    public ProcessResult postProcess(JobContext context) throws Exception {
        // Implement interface
        if (Objects.nonNull(this.baseProcessor)) {
            return ((JobProcessor) this.baseProcessor).postProcess(context);
        }

        if (Objects.isNull(this.postMethod)) {
            return new ProcessResult(true);
        }

        return (ProcessResult) this.postMethod.invoke(this.target, this.getMethodParams(this.postMethod, context));
    }

    /**
     * Get method params
     *
     * @param method  method
     * @param context context
     * @return array
     */
    private Object[] getMethodParams(Method method, JobContext context) {
        List<Object> args = Lists.newArrayList();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType.equals(JobContext.class)) {
                args.add(context);
                continue;
            }
            args.add(null);
        }
        return args.toArray();
    }
}
