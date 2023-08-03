package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Delay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin swoft@qq.com
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
     * Find by ids
     *
     * @param ids ids
     * @return list
     */
    List<Delay> findByIdIn(List<Long> ids);

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
     * @param appId   app id.
     * @param deleted deleted
     * @return List
     */
    List<Delay> findByAppIdAndDeleted(Long appId, Integer deleted);

    /**
     * Count by namespace
     *
     * @param namespaceId namespaceId
     * @param deleted     deleted
     * @return Long
     */
    Long countByNamespaceIdAndDeleted(Long namespaceId, Integer deleted);

    /**
     * Count by namespace and create time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @param deleted     deleted
     * @return Long
     */
    Long countByNamespaceIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqualAndDeleted(Long namespaceId, Long startTime, Long endTime, Integer deleted);

    /**
     * Find first by namespace and appid
     *
     * @param namespaceId namespaceId
     * @param appId       appId
     * @param deleted     deleted
     * @return Delay
     */
    Delay findFirstByNamespaceIdAndAppIdAndDeleted(Long namespaceId, Long appId, Integer deleted);
}
