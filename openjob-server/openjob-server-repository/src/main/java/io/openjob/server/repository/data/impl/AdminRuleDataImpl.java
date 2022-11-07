package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.AdminRuleDAO;
import io.openjob.server.repository.data.AdminRuleData;
import io.openjob.server.repository.dto.AdminRuleDTO;
import io.openjob.server.repository.entity.AdminRule;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 21:35:13
 * @since 1.0.0
 */
@Component
public class AdminRuleDataImpl implements AdminRuleData {

    private final AdminRuleDAO adminRuleDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public AdminRuleDataImpl(AdminRuleDAO adminRuleDAO, RedisOperation redisOperation) {
        this.adminRuleDAO = adminRuleDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(AdminRuleDTO dto) {
        AdminRule entity = BeanMapperUtil.map(dto, AdminRule.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return adminRuleDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<AdminRuleDTO> dtoList) {
        List<AdminRule> entityList = BeanMapperUtil.mapList(dtoList, AdminRuleDTO.class, AdminRule.class);

        return adminRuleDAO.batchAdd(entityList);
    }

    @Override
    public AdminRuleDTO getById(Long id) {
        return BeanMapperUtil.map(adminRuleDAO.getById(id), AdminRuleDTO.class);
    }

    @Override
    public AdminRuleDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getAdminRuleByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(AdminRuleDTO dto) {
        AdminRule entity = BeanMapperUtil.map(dto, AdminRule.class);

        redisOperation.delete(CacheKey.getAdminRuleByIdKey(dto.getId()));

        return adminRuleDAO.updateById(entity);
    }
}

