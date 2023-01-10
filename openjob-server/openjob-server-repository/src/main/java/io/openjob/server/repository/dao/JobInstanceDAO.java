package io.openjob.server.repository.dao;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.server.repository.entity.JobInstance;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceDAO {
    /**
     * Save
     *
     * @param jobInstance jobInstance
     * @return Insert id.
     */
    Long save(JobInstance jobInstance);

    /**
     * Update
     *
     * @param id     id
     * @param status status
     * @return Integer
     */
    Integer updateStatusById(Long id, Integer status);

    /**
     * Update
     *
     * @param ids            ids
     * @param lastReportTime lastReportTime
     * @return Integer
     */
    Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime);

    /**
     * Get failover list.
     *
     * @param lastReportTime last report time.
     * @param statusList     status list.
     * @return List
     */
    List<JobInstance> getFailoverList(Long lastReportTime, List<Integer> statusList);

    /**
     * Update to running.
     *
     * @param id                 id
     * @param workerAddress      worker address
     * @param instanceStatusEnum instance status
     * @return Integer
     */
    Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instanceStatusEnum);

    /**
     * Get one by id and status.
     *
     * @param id     id
     * @param status status
     * @return JobInstance
     */
    JobInstance getOneByIdAndStatus(Long id, Integer status);
}
