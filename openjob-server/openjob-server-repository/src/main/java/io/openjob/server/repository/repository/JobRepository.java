package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface JobRepository extends JpaRepository<Job, Long> {
    /**
     * Find jobs by condition.
     *
     * @param slotIds slotIds
     * @param status  status
     * @param types   types
     * @param time    time
     * @param deleted deleted
     * @return jobs
     */
    List<Job> findBySlotsIdInAndStatusAndTimeExpressionTypeNotInAndNextExecuteTimeLessThanEqualAndDeleted(List<Long> slotIds, Integer status, List<String> types, Long time, Integer deleted);

    /**
     * Find first by namespace id and app id
     *
     * @param namespaceId namespaceId
     * @param appId       appId
     * @param deleted     deleted
     * @return Job
     */
    Job findFirstByNamespaceIdAndAppIdAndDeleted(Long namespaceId, Long appId, Integer deleted);

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

}
