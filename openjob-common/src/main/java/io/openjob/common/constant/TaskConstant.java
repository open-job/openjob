package io.openjob.common.constant;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class TaskConstant {

    /**
     * MR root task name
     */
    public static final String MAP_TASK_ROOT_NAME = "MR_TASK_ROOT";

    /**
     * MR reduce task name
     */
    public static final String MAP_TASK_REDUCE_NAME = "MR_TASK_REDUCE";

    /**
     * Broadcast task name
     */
    public static final String BROADCAST_NAME = "BROADCAST";

    /**
     * Broadcast post task name
     */
    public static final String BROADCAST_POST_NAME = "BROADCAST_POST";

    /**
     * Sharding task name
     */
    public static final String SHARDING_NAME = "SHARDING";

    /**
     * Default parent task id
     */
    public static final String DEFAULT_PARENT_ID = "0_0_0_0_0";

    /**
     * Default circle id
     */
    public static final Long DEFAULT_CIRCLE_ID = 1L;
}
