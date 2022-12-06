package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.NotifyContactDAO;
import io.openjob.server.repository.entity.NotifyContact;
import io.openjob.server.repository.repository.NotifyContactRepository;
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
        return notifyContactRepository.findById(id).orElse(null);
    }

    @Override
    public Integer updateById(NotifyContact entity) {
        notifyContactRepository.save(entity);
        return 1;
    }

    @Override
    public Page<NotifyContact> getPageList(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return notifyContactRepository.findAll(pageReq);
    }
}

