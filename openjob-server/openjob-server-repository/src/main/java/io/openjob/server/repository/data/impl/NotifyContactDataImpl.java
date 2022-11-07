package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.NotifyContactDAO;
import io.openjob.server.repository.data.NotifyContactData;
import io.openjob.server.repository.dto.NotifyContactDTO;
import io.openjob.server.repository.entity.NotifyContact;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:34:42
 * @since 1.0.0
 */
@Component
public class NotifyContactDataImpl implements NotifyContactData {

    private final NotifyContactDAO notifyContactDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public NotifyContactDataImpl(NotifyContactDAO notifyContactDAO, RedisOperation redisOperation) {
        this.notifyContactDAO = notifyContactDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(NotifyContactDTO dto) {
        NotifyContact entity = BeanMapperUtil.map(dto, NotifyContact.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return notifyContactDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<NotifyContactDTO> dtoList) {
        List<NotifyContact> entityList = BeanMapperUtil.mapList(dtoList, NotifyContactDTO.class, NotifyContact.class);

        return notifyContactDAO.batchAdd(entityList);
    }

    @Override
    public NotifyContactDTO getById(Long id) {
        return BeanMapperUtil.map(notifyContactDAO.getById(id), NotifyContactDTO.class);
    }

    @Override
    public NotifyContactDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getNotifyContactByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(NotifyContactDTO dto) {
        NotifyContact entity = BeanMapperUtil.map(dto, NotifyContact.class);

        redisOperation.delete(CacheKey.getNotifyContactByIdKey(dto.getId()));

        return notifyContactDAO.updateById(entity);
    }
}

