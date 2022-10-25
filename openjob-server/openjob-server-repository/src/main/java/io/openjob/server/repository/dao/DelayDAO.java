package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Delay;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayDAO {

    /**
     * Save
     *
     * @param delay delay
     * @return Long
     */
    Long save(Delay delay);

    /**
     * Find by namespaceId and topic.
     *
     * @param topic topic
     * @return Delay
     */
    Delay findByNamespaceIdAndTopic(String topic);
}
