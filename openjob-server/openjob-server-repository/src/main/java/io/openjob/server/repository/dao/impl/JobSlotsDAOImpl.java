package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.repository.JobSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobSlotsDAOImpl implements JobSlotsDAO {
    private final JobSlotsRepository jobSlotsRepository;

    @Autowired
    public JobSlotsDAOImpl(JobSlotsRepository taskSlotsRepository) {
        this.jobSlotsRepository = taskSlotsRepository;
    }

    @Override
    public Long save(JobSlots taskSlots) {
        Integer now = DateUtil.now();
        taskSlots.setCreateTime(now);
        taskSlots.setUpdateTime(now);
        return this.jobSlotsRepository.saveAndFlush(taskSlots).getId();
    }

    @Override
    public List<JobSlots> listJobSlots(Long serverId) {
        JobSlots taskSlots = new JobSlots();
        taskSlots.setServerId(serverId);
        return jobSlotsRepository.findAll(Example.of(taskSlots));
    }

    @Override
    public List<JobSlots> listJobSlots() {
        return jobSlotsRepository.findAll();
    }

    @Override
    public Long updateByIds(Long serverId, List<Long> slots) {
        return jobSlotsRepository.updateByIds(slots, serverId, DateUtil.now());
    }
}
