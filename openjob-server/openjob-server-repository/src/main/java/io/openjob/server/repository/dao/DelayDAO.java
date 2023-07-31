package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.DelayPageDTO;
import io.openjob.server.repository.entity.Delay;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
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
     * Delete by id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * Update
     *
     * @param delay delay
     * @return Long
     */
    Long update(Delay delay);

    /**
     * Update cid by id
     *
     * @param id  id
     * @param cid cid
     */
    void updateCidById(Long id, Long cid);

    /**
     * Update status or deleted
     *
     * @param id      id
     * @param status  status
     * @param deleted deleted
     */
    void updateStatusOrDeleted(Long id, Integer status, Integer deleted);

    /**
     * Page list
     *
     * @param delayPageDTO delayPageDTO
     * @return PageDTO
     */
    PageDTO<Delay> pageList(DelayPageDTO delayPageDTO);

    List<Delay> findByIds(List<Long> ids);

    /**
     * Find by namespaceId and topic.
     *
     * @param topic topic
     * @return Delay
     */
    Delay findByTopic(String topic);

    /**
     * Find by id
     *
     * @param id id
     * @return Delay
     */
    Optional<Delay> findById(Long id);

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

    /**
     * Count by namespace
     *
     * @param namespaceId namespaceId
     * @return Long
     */
    Long countByNamespace(Long namespaceId);

    /**
     * Count by namespace and create time
     *
     * @param namespaceId namespaceId
     * @param startTime   startTime
     * @param endTime     endTime
     * @return Long
     */
    Long countByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime);

    /**
     * Get first by namespace id and appid
     *
     * @param namespaceId namespaceId
     * @param appId       appId
     * @return Delay
     */
    Delay getFirstByNamespaceAndAppid(Long namespaceId, Long appId);
}
