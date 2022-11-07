package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.NotifyGroupDAO;
import io.openjob.server.repository.entity.NotifyGroup;
import io.openjob.server.repository.repository.NotifyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:21:31
 * @since 1.0.0
 */
@Component
public class NotifyGroupDAOImpl implements NotifyGroupDAO {
    private final NotifyGroupRepository notifyGroupRepository;

    @Autowired
    public NotifyGroupDAOImpl(NotifyGroupRepository notifyGroupRepository) {
        this.notifyGroupRepository = notifyGroupRepository;
    }

    @Override
    public Long add(NotifyGroup entity) {
        return notifyGroupRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<NotifyGroup> entityList) {
        notifyGroupRepository.saveAll(entityList);
    }

    @Override
    public NotifyGroup getById(Long id) {
        return notifyGroupRepository.getById(id);
    }

    @Override
    public Integer updateById(NotifyGroup entity) {
        return notifyGroupRepository.updateById(entity);
    }
}

