package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.AdminMenuDAO;
import io.openjob.server.repository.data.AdminMenuData;
import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.entity.AdminMenu;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:34:58
 * @since 1.0.0
 */
@Component
public class AdminMenuDataImpl implements AdminMenuData {

    private final AdminMenuDAO adminMenuDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public AdminMenuDataImpl(AdminMenuDAO adminMenuDAO, RedisOperation redisOperation) {
        this.adminMenuDAO = adminMenuDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(AdminMenuDTO dto) {
        AdminMenu entity = BeanMapperUtil.map(dto, AdminMenu.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return adminMenuDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<AdminMenuDTO> dtoList) {
        List<AdminMenu> entityList = BeanMapperUtil.mapList(dtoList, AdminMenuDTO.class, AdminMenu.class);

        return adminMenuDAO.batchAdd(entityList);
    }

    @Override
    public AdminMenuDTO getById(Long id) {
        return BeanMapperUtil.map(adminMenuDAO.getById(id), AdminMenuDTO.class);
    }

    @Override
    public AdminMenuDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getAdminMenuByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(AdminMenuDTO dto) {
        AdminMenu entity = BeanMapperUtil.map(dto, AdminMenu.class);

        redisOperation.delete(CacheKey.getAdminMenuByIdKey(dto.getId()));

        return adminMenuDAO.updateById(entity);
    }
}

