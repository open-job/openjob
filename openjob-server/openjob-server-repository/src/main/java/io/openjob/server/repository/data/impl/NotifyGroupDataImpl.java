package io.openjob.server.repository.data.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.NotifyGroupDAO;
import io.openjob.server.repository.data.NotifyGroupData;
import io.openjob.server.repository.dto.NotifyGroupDTO;
import io.openjob.server.repository.entity.NotifyGroup;
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
    // private final RedisOperation redisOperation;

    @Autowired
    public NotifyGroupDataImpl(NotifyGroupDAO notifyGroupDAO) {
        this.notifyGroupDAO = notifyGroupDAO;
    }

    @Override
    public Long add(NotifyGroupDTO dto) {
        NotifyGroup entity = new NotifyGroup();
        BeanUtils.copyProperties(dto, entity);

        long curTime = DateUtil.timestamp();
        entity.setCreateTime(curTime);
        entity.setUpdateTime(curTime);

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

