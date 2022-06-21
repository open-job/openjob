package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.TaskSlotsDAO;
import io.openjob.server.repository.entity.TaskSlots;
import io.openjob.server.repository.repository.TaskSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class TaskSlotsDAOImpl implements TaskSlotsDAO {
    private final TaskSlotsRepository taskSlotsRepository;

    @Autowired
    public TaskSlotsDAOImpl(TaskSlotsRepository taskSlotsRepository) {
        this.taskSlotsRepository = taskSlotsRepository;
    }

    @Override
    public Long save(TaskSlots taskSlots) {
        Integer now = DateUtil.now();
        taskSlots.setCreateTime(now);
        taskSlots.setUpdateTime(now);
        return this.taskSlotsRepository.saveAndFlush(taskSlots).getId();
    }

    @Override
    public List<TaskSlots> listTaskSlots(Long serverId) {
        return null;
    }

    @Override
    public List<TaskSlots> listTaskSlots() {
        return taskSlotsRepository.findAll();
    }
}
