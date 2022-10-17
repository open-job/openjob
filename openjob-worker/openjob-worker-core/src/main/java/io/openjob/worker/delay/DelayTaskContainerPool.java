package io.openjob.worker.delay;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskContainerPool {

    /**
     * Delay task pool.
     * topic => DelayTaskContainer.
     */
    private static final Map<Long, DelayTaskContainer> DELAY_TASK_POOL = Maps.newConcurrentMap();

    /**
     * Whether topic is existed.
     *
     * @param delayId delayId
     * @return Boolean
     */
    public static Boolean contains(Long delayId) {
        return DELAY_TASK_POOL.containsKey(delayId);
    }

    /**
     * Get delay task container.
     *
     * @param delayId delayId
     * @return DelayTaskContainer
     */
    public static DelayTaskContainer get(Long delayId) {
        return DELAY_TASK_POOL.get(delayId);
    }

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

    /**
     * Get all delay task container.
     *
     * @return Map
     */
    public static Map<Long, DelayTaskContainer> getAllDelayTaskContainer() {
        return DELAY_TASK_POOL;
    }

    /**
     * Remove task container.
     *
     * @param topic topic
     */
    public static void remove(Long topic) {
        DELAY_TASK_POOL.remove(topic);
    }
}
