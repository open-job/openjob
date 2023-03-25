package io.openjob.worker.delay;

import com.google.common.collect.Maps;
import io.openjob.common.constant.CommonConstant;

import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskContainerPool {

    /**
     * Delay task pool.
     * delayId => DelayTaskContainer.
     */
    private static final Map<Long, DelayTaskContainer> DELAY_TASK_POOL = Maps.newConcurrentMap();

    /**
     * Get delay task container.
     *
     * @param delayId delayId
     * @param creator creator
     * @return DelayTaskContainer
     */
    public static DelayTaskContainer get(Long delayId, Function<Long, DelayTaskContainer> creator) {
        return DELAY_TASK_POOL.computeIfAbsent(delayId, creator);
    }

    public static void stop() {
        DELAY_TASK_POOL.forEach((t, c) -> c.stop());
    }
}
