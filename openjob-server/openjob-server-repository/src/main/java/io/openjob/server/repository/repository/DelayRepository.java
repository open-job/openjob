package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Delay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayRepository extends JpaRepository<Delay, Long> {

    /**
     * Find by topic
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
    List<Delay> findByTopicIn(List<String> topics);

    /**
     * Find by app id.
     *
     * @param appId app id.
     * @return List
     */
    List<Delay> findByAppId(Long appId);
}
