package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.JobAdminConfigDAO;
import io.openjob.server.repository.data.JobAdminConfigData;
import io.openjob.server.repository.dto.JobAdminConfigDTO;
import io.openjob.server.repository.entity.JobAdminConfig;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 13:33:15
 * @since 1.0.0
 */
@Component
public class JobAdminConfigDataImpl implements JobAdminConfigData {

    private final JobAdminConfigDAO jobAdminConfigDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public JobAdminConfigDataImpl(JobAdminConfigDAO jobAdminConfigDAO, RedisOperation redisOperation) {
        this.jobAdminConfigDAO = jobAdminConfigDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(JobAdminConfigDTO dto) {
        JobAdminConfig entity = BeanMapperUtil.map(dto, JobAdminConfig.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return jobAdminConfigDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<JobAdminConfigDTO> dtoList) {
        List<JobAdminConfig> entityList = BeanMapperUtil.mapList(dtoList, JobAdminConfigDTO.class, JobAdminConfig.class);

        return jobAdminConfigDAO.batchAdd(entityList);
    }

    @Override
    public JobAdminConfigDTO getById(Long id) {
        return BeanMapperUtil.map(jobAdminConfigDAO.getById(id), JobAdminConfigDTO.class);
    }

    @Override
    public JobAdminConfigDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getJobAdminConfigByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(JobAdminConfigDTO dto) {
        JobAdminConfig entity = BeanMapperUtil.map(dto, JobAdminConfig.class);

        redisOperation.delete(CacheKey.getJobAdminConfigByIdKey(dto.getId()));

        return jobAdminConfigDAO.updateById(entity);
    }
}

