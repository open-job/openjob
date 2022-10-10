package io.openjob.common.util;

import java.util.UUID;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskUtil {
    private TaskUtil() {

    }

    public static String getReduceParentUniqueId(Long jobId, Long instanceId, Long circleId) {
        return String.format("%d_%d_%d_reduce", jobId, instanceId, circleId);
    }

    public static String getRandomUniqueId(Long jobId, Long instanceId, Long circleId, Long taskId) {
        return String.format("%d_%d_%d_%d", jobId, instanceId, circleId, taskId);
    }

    public static String getRandomUniqueId() {
        return UUID.randomUUID().toString();
    }
}
