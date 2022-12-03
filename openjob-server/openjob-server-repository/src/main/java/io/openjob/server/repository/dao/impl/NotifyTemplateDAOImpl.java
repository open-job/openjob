package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.NotifyTemplateDAO;
import io.openjob.server.repository.entity.NotifyTemplate;
import io.openjob.server.repository.repository.NotifyTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class NotifyTemplateDAOImpl implements NotifyTemplateDAO {
    private final NotifyTemplateRepository notifyTemplateRepository;

    @Autowired
    public NotifyTemplateDAOImpl(NotifyTemplateRepository notifyTemplateRepository) {
        this.notifyTemplateRepository = notifyTemplateRepository;
    }

    @Override
    public Long add(NotifyTemplate entity) {
        return notifyTemplateRepository.save(entity).getId();
    }

    @Override
    public void batchAdd(List<NotifyTemplate> entityList) {
        notifyTemplateRepository.saveAll(entityList);
    }

    @Override
    public NotifyTemplate getById(Long id) {
        return notifyTemplateRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(NotifyTemplate entity) {
        // return notifyTemplateRepository.updateById(entity); // TODO
        return 0;
    }
}

