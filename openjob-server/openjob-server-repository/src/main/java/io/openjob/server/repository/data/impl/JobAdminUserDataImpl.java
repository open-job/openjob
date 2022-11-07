package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.JobAdminUserDAO;
import io.openjob.server.repository.data.JobAdminUserData;
import io.openjob.server.repository.dto.JobAdminUserDTO;
import io.openjob.server.repository.entity.JobAdminUser;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 13:33:42
 * @since 1.0.0
 */
@Component
public class JobAdminUserDataImpl implements JobAdminUserData {

    private final JobAdminUserDAO jobAdminUserDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public JobAdminUserDataImpl(JobAdminUserDAO jobAdminUserDAO, RedisOperation redisOperation) {
        this.jobAdminUserDAO = jobAdminUserDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(JobAdminUserDTO dto) {
        JobAdminUser entity = BeanMapperUtil.map(dto, JobAdminUser.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return jobAdminUserDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<JobAdminUserDTO> dtoList) {
        List<JobAdminUser> entityList = BeanMapperUtil.mapList(dtoList, JobAdminUserDTO.class, JobAdminUser.class);

        return jobAdminUserDAO.batchAdd(entityList);
    }

    @Override
    public JobAdminUserDTO getById(Long id) {
        return BeanMapperUtil.map(jobAdminUserDAO.getById(id), JobAdminUserDTO.class);
    }

    @Override
    public JobAdminUserDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getJobAdminUserByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(JobAdminUserDTO dto) {
        JobAdminUser entity = BeanMapperUtil.map(dto, JobAdminUser.class);

        redisOperation.delete(CacheKey.getJobAdminUserByIdKey(dto.getId()));

        return jobAdminUserDAO.updateById(entity);
    }
}

