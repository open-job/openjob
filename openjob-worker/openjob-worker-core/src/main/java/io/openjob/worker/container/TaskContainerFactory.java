package io.openjob.worker.container;

import io.openjob.worker.context.JobContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;

/**
 * @author stelin <swoft@qq.com>
 * @see ThreadTaskContainer
 * @since 1.0.0
 */
public class TaskContainerFactory {

    /**
     *
     * @param jobContext
     * @return
     */
    public static TaskContainer create(JobContext jobContext) {
        String containerType = "thread";

        try {
            String className = String.format("io.openjob.worker.container.%sTaskContainer", StringUtils.capitalize(containerType));
            @SuppressWarnings("unchecked")
            Constructor<? extends TaskContainer> constructor = ((Class<? extends TaskContainer>) Class.forName(className))
                    .getConstructor(JobContext.class);
            return constructor.newInstance(jobContext);
        } catch (Throwable ex) {
            throw new RuntimeException("Task master is not exist! executeType=" + containerType);
        }
    }

    /**
     *
     * @return
     */
    public static TaskContainerPool getPool() {
        return ThreadTaskContainerPool.INSTANCE;
    }
}
