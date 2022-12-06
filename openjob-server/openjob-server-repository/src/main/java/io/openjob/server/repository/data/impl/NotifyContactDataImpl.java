package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.NotifyContactDAO;
import io.openjob.server.repository.data.NotifyContactData;
import io.openjob.server.repository.dto.NotifyContactDTO;
import io.openjob.server.repository.entity.NotifyContact;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author inhere
 * @since 1.0.0
 */
@Component
public class NotifyContactDataImpl implements NotifyContactData {

    private final NotifyContactDAO notifyContactDAO;
    // private final RedisOperation redisOperation;

    @Autowired
    public NotifyContactDataImpl(NotifyContactDAO notifyContactDAO) {
        this.notifyContactDAO = notifyContactDAO;
    }

    @Override
    public Long add(NotifyContactDTO dto) {
        NotifyContact entity = new NotifyContact();
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

        return notifyContactDAO.add(entity);
    }

    @Override
    public void batchAdd(List<NotifyContactDTO> dtoList) {
        List<NotifyContact> entityList = new ArrayList<>();
        // TODO copy data

        notifyContactDAO.batchAdd(entityList);
    }

    @Override
    public NotifyContactDTO getById(Long id) {
        return ObjectUtil.mapObject(notifyContactDAO.getById(id), NotifyContactDTO.class);
    }

    @Override
    public Integer updateById(NotifyContactDTO dto) {
        NotifyContact entity = new NotifyContact();
        BeanUtils.copyProperties(dto, entity);

        return notifyContactDAO.updateById(entity);
    }

    @Override
    public List<NotifyContactDTO> getPageList(Long id) {
        // TODO
        return null;
    }
}

