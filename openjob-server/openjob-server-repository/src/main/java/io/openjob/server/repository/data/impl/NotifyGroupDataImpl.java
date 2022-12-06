package io.openjob.server.repository.data.impl;

import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.NotifyGroupDAO;
import io.openjob.server.repository.data.NotifyGroupData;
import io.openjob.server.repository.dto.NotifyGroupDTO;
import io.openjob.server.repository.entity.NotifyGroup;
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
public class NotifyGroupDataImpl implements NotifyGroupData {

    private final NotifyGroupDAO notifyGroupDAO;

    @Autowired
    public NotifyGroupDataImpl(NotifyGroupDAO notifyGroupDAO) {
        this.notifyGroupDAO = notifyGroupDAO;
    }

    @Override
    public Long add(NotifyGroupDTO dto) {
        NotifyGroup entity = new NotifyGroup();
        BeanUtils.copyProperties(EntityUtil.initDefaults(dto), entity);

        return notifyGroupDAO.add(entity);
    }

    @Override
    public void batchAdd(List<NotifyGroupDTO> dtoList) {
        List<NotifyGroup> entityList = new ArrayList<>();
        // TODO copy data

        notifyGroupDAO.batchAdd(entityList);
    }

    @Override
    public NotifyGroupDTO getById(Long id) {
        return ObjectUtil.mapObject(notifyGroupDAO.getById(id), NotifyGroupDTO.class);
    }

    @Override
    public Integer updateById(NotifyGroupDTO dto) {
        NotifyGroup entity = new NotifyGroup();
        BeanUtils.copyProperties(dto, entity);

        return notifyGroupDAO.updateById(entity);
    }

    @Override
    public List<NotifyGroupDTO> getPageList(Long id) {
        // TODO
        return null;
    }
}

