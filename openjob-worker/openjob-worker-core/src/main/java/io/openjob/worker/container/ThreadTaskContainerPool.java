package io.openjob.worker.container;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskContainerPool extends TaskContainerPool {
    protected Map<Long, ExecutorService> threadPoolMap = Maps.newConcurrentMap();


}
