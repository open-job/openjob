package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.DelayPageDTO;
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
     * Update
     *
     * @param delay
     * @return Long
     */
    Long update(Delay delay);

    /**
     * Update status or deleted
     *
     * @param id      id
     * @param status  status
     * @param deleted deleted
     * @return Long
     */
    Long updateStatusOrDeleted(Long id, Integer status, Integer deleted);

    /**
     * Page list
     *
     * @param delayPageDTO
     * @return PageDTO
     */
    PageDTO<Delay> pageList(DelayPageDTO delayPageDTO);

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
