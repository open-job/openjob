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
    private static final Map<String, DelayTaskContainer> DELAY_TASK_POOL = Maps.newConcurrentMap();

    /**
     * Whether topic is existed.
     *
     * @param topic topic
     * @return Boolean
     */
    public static Boolean contains(String topic) {
        return DELAY_TASK_POOL.containsKey(topic);
    }

    /**
     * Get delay task container.
     *
     * @param topic topic
     * @return DelayTaskContainer
     */
    public static DelayTaskContainer get(String topic) {
        return DELAY_TASK_POOL.get(topic);
    }

    /**
     * Get delay task container.
     *
     * @param topic   topic
     * @param creator creator
     * @return DelayTaskContainer
     */
    public static DelayTaskContainer get(String topic, Function<String, DelayTaskContainer> creator) {
        return DELAY_TASK_POOL.computeIfAbsent(topic, creator);
    }

    /**
     * Remove task container.
     *
     * @param topic topic
     */
    public static void remove(String topic) {
        DELAY_TASK_POOL.remove(topic);
    }
}
