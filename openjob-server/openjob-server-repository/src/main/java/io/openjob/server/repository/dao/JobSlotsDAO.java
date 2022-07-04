package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobSlots;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobSlotsDAO {
    /**
     * Save job slots.
     *
     * @param taskSlots taskSlots
     * @return Save id
     */
    Long save(JobSlots taskSlots);

    /**
     * List job slots by server id.
     *
     * @param serverId serverId
     * @return List
     */
    List<JobSlots> listJobSlotsByServerId(Long serverId);

    /**
     * List all job slots.
     *
     * @return List
     */
    List<JobSlots> listJobSlots();

    /**
     * Update slots by serverId and slots.
     *
     * @param serverId serverId
     * @param slots    slots
     * @return Effected rows.
     */
    Integer updateByServerId(Long serverId, List<Long> slots);

    /**
     * Update slots by server Id.
     *
     * @param serverId serverId
     * @return Effected rows.
     */
    Integer updateByServerId(Long serverId);
}
