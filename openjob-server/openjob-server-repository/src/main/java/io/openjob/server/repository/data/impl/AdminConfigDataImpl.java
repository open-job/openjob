package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.AdminConfigDAO;
import io.openjob.server.repository.data.AdminConfigData;
import io.openjob.server.repository.dto.AdminConfigDTO;
import io.openjob.server.repository.entity.AdminConfig;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:35:56
 * @since 1.0.0
 */
@Component
public class AdminConfigDataImpl implements AdminConfigData {

    private final AdminConfigDAO adminConfigDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public AdminConfigDataImpl(AdminConfigDAO adminConfigDAO, RedisOperation redisOperation) {
        this.adminConfigDAO = adminConfigDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(AdminConfigDTO dto) {
        AdminConfig entity = BeanMapperUtil.map(dto, AdminConfig.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return adminConfigDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<AdminConfigDTO> dtoList) {
        List<AdminConfig> entityList = BeanMapperUtil.mapList(dtoList, AdminConfigDTO.class, AdminConfig.class);

        return adminConfigDAO.batchAdd(entityList);
    }

    @Override
    public AdminConfigDTO getById(Long id) {
        return BeanMapperUtil.map(adminConfigDAO.getById(id), AdminConfigDTO.class);
    }

    @Override
    public AdminConfigDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getAdminConfigByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(AdminConfigDTO dto) {
        AdminConfig entity = BeanMapperUtil.map(dto, AdminConfig.class);

        redisOperation.delete(CacheKey.getAdminConfigByIdKey(dto.getId()));

        return adminConfigDAO.updateById(entity);
    }
}

