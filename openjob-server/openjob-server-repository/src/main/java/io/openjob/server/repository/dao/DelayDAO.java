package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Delay;

import java.util.List;

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
    Delay findByTopic(String topic);

    /**
     * Find by topics
     *
     * @param topics topic list.
     * @return list
     */
    List<Delay> findByTopics(List<String> topics);

    /**
     * Find by app id.
     *
     * @param appId app id.
     * @return list
     */
    List<Delay> findByAppId(Long appId);
}
