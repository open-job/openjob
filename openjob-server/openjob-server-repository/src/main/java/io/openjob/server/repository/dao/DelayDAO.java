package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Delay;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayDAO {
    Long save(Delay delay);

    Delay findByNamespaceIdAndTopic(String topic);
}
