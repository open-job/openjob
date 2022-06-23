package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.TaskSlots;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskSlotsDAO {
    Long save(TaskSlots taskSlots);

    List<TaskSlots> listTaskSlots(Long serverId);

    List<TaskSlots> listTaskSlots();

    Long updateByIds(Long serverId, List<Long> slots);
}
