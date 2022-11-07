package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.NotifyGroupDAO;
import io.openjob.server.repository.data.NotifyGroupData;
import io.openjob.server.repository.dto.NotifyGroupDTO;
import io.openjob.server.repository.entity.NotifyGroup;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:21:32
 * @since 1.0.0
 */
@Component
public class NotifyGroupDataImpl implements NotifyGroupData {

    private final NotifyGroupDAO notifyGroupDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public NotifyGroupDataImpl(NotifyGroupDAO notifyGroupDAO, RedisOperation redisOperation) {
        this.notifyGroupDAO = notifyGroupDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(NotifyGroupDTO dto) {
        NotifyGroup entity = BeanMapperUtil.map(dto, NotifyGroup.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return notifyGroupDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<NotifyGroupDTO> dtoList) {
        List<NotifyGroup> entityList = BeanMapperUtil.mapList(dtoList, NotifyGroupDTO.class, NotifyGroup.class);

        return notifyGroupDAO.batchAdd(entityList);
    }

    @Override
    public NotifyGroupDTO getById(Long id) {
        return BeanMapperUtil.map(notifyGroupDAO.getById(id), NotifyGroupDTO.class);
    }

    @Override
    public NotifyGroupDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getNotifyGroupByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(NotifyGroupDTO dto) {
        NotifyGroup entity = BeanMapperUtil.map(dto, NotifyGroup.class);

        redisOperation.delete(CacheKey.getNotifyGroupByIdKey(dto.getId()));

        return notifyGroupDAO.updateById(entity);
    }
}

