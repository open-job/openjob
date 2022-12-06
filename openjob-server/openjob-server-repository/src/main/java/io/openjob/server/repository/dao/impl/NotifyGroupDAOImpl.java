package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.NotifyGroupDAO;
import io.openjob.server.repository.entity.NotifyGroup;
import io.openjob.server.repository.repository.NotifyGroupRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
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
        return notifyGroupRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(NotifyGroup entity) {
        notifyGroupRepository.save(entity);
        return 1;
    }

    @Override
    public Page<NotifyGroup> getPageList(Integer page, Integer size) {
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return notifyGroupRepository.findAll(pageReq);
    }
}

