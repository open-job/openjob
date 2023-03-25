package io.openjob.server.scheduler.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class CacheConst {

    /**
     * Topic list prefix.
     */
    public static final String TOPIC_LIST_PREFIX = "topic:list";

    /**
     * Delay prefix
     */
    public static final String DELAY_DETAIL_TOPIC_PREFIX = "delay:detail:topic";

    /**
     * Delay prefix
     */
    public static final String DELAY_DETAIL_ID_PREFIX = "delay:detail:id";

    /**
     * Delay detail prefix.
     */
    public static final String DELAY_DETAIL_TASKID_PREFIX = "delay:detail:taskid";

    /**
     * Delay worker address
     */
    public static final String DELAY_DETAIL_WORKER_ADDRESS_PREFIX = "delay:detail:worker:address";

    /**
     * Delay task retry times
     */
    public static final String DELAY_TASK_RETRY_TIMES_PREFIX = "delay:task:retry:times";

    /**
     * Delay list prefix.
     */
    public static final String DELAY_ADD_LIST_PREFIX = "delay:list:add";

    /**
     * Delay status list prefix.
     */
    public static final String DELAY_STATUS_LIST_PREFIX = "delay:list:status";

    /**
     * Delay delete list prefix.
     */
    public static final String DELAY_DELETE_LIST_PREFIX = "delay:list:delete";

    /**
     * Delay zset prefix.
     */
    public static final String DELAY_ZSET_PREFIX = "delay:zset";
}
