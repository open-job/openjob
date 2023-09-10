package io.openjob.common.util;

import io.openjob.common.constant.TaskConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class TaskUtil {
    private TaskUtil() {

    }

    /**
     * Get random unique id
     *
     * @param jobId      jobId
     * @param instanceId instanceId
     * @param version    version
     * @param circleId   circleId
     * @param taskId     taskId
     * @return String
     */
    public static String getRandomUniqueId(Long jobId, Long instanceId, Long version, Long circleId, Long taskId) {
        return String.format("%d_%d_%d_%d_%d", jobId, instanceId, version, circleId, taskId);
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

    /**
     * Get broadcast task name
     *
     * @param id id
     * @return String
     */
    public static String getBroadcastTaskName(Long id) {
        return String.format("%s_%d", TaskConstant.BROADCAST_NAME, id);
    }

    /**
     * Get sharding task name
     *
     * @param id id
     * @return String
     */
    public static String getShardingTaskName(Long id) {
        return String.format("%s_%d", TaskConstant.SHARDING_NAME, id);
    }
}
