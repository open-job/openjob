package io.openjob.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author stelin swoft@qq.com
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

    /**
     * Get unique last id.
     *
     * @param uniqueId uniqueId
     * @return Long
     */
    public static Long getRandomUniqueIdLastId(String uniqueId) {
        // Empty unique id.
        if (StringUtils.isEmpty(uniqueId)) {
            return 0L;
        }

        String[] split = StringUtils.split(uniqueId, "_");
        return Long.valueOf(split[split.length - 1]);
    }

    public static String getRandomTaskId() {
        return UUID.randomUUID().toString();
    }
}
