package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.NotifyContactDAO;
import io.openjob.server.repository.entity.NotifyContact;
import io.openjob.server.repository.repository.NotifyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:42
 * @since 1.0.0
 */
@Component
public class NotifyContactDAOImpl implements NotifyContactDAO {
    private final NotifyContactRepository notifyContactRepository;

    @Autowired
    public NotifyContactDAOImpl(NotifyContactRepository notifyContactRepository) {
        this.notifyContactRepository = notifyContactRepository;
    }

    @Override
    public Long add(NotifyContact entity) {
        return notifyContactRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<NotifyContact> entityList) {
        notifyContactRepository.saveAll(entityList);
    }

    @Override
    public NotifyContact getById(Long id) {
        return notifyContactRepository.getById(id);
    }

    @Override
    public Integer updateById(NotifyContact entity) {
        return notifyContactRepository.updateById(entity);
    }
}

