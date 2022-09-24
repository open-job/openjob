package io.openjob.worker.task;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class LogTaskConsumer<T> extends BaseConsumer<T> {

    public LogTaskConsumer(Long id,
                           Integer consumerCoreThreadNum,
                           Integer consumerMaxThreadNum,
                           String consumerThreadName,
                           Integer pollSize,
                           String pollThreadName,
                           TaskQueue<T> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues);
    }

    @Override
    public void consume(Long id, List<T> tasks) {

    }
}
