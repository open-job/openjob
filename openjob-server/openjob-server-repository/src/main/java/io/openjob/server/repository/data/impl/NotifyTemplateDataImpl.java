package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.NotifyTemplateDAO;
import io.openjob.server.repository.data.NotifyTemplateData;
import io.openjob.server.repository.dto.NotifyTemplateDTO;
import io.openjob.server.repository.entity.NotifyTemplate;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:33:45
 * @since 1.0.0
 */
@Component
public class NotifyTemplateDataImpl implements NotifyTemplateData {

    private final NotifyTemplateDAO notifyTemplateDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public NotifyTemplateDataImpl(NotifyTemplateDAO notifyTemplateDAO, RedisOperation redisOperation) {
        this.notifyTemplateDAO = notifyTemplateDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(NotifyTemplateDTO dto) {
        NotifyTemplate entity = BeanMapperUtil.map(dto, NotifyTemplate.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return notifyTemplateDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<NotifyTemplateDTO> dtoList) {
        List<NotifyTemplate> entityList = BeanMapperUtil.mapList(dtoList, NotifyTemplateDTO.class, NotifyTemplate.class);

        return notifyTemplateDAO.batchAdd(entityList);
    }

    @Override
    public NotifyTemplateDTO getById(Long id) {
        return BeanMapperUtil.map(notifyTemplateDAO.getById(id), NotifyTemplateDTO.class);
    }

    @Override
    public NotifyTemplateDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getNotifyTemplateByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(NotifyTemplateDTO dto) {
        NotifyTemplate entity = BeanMapperUtil.map(dto, NotifyTemplate.class);

        redisOperation.delete(CacheKey.getNotifyTemplateByIdKey(dto.getId()));

        return notifyTemplateDAO.updateById(entity);
    }
}

