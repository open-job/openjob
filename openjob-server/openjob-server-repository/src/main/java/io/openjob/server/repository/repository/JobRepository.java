package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
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
     * @return jobs
     */
    List<Job> findBySlotsIdInAndStatusAndTimeExpressionTypeNotInAndNextExecuteTimeLessThanEqual(List<Long> slotIds, Integer status, List<Integer> types, Integer time);

}
