package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.JobContactGroupDAO;
import io.openjob.server.repository.data.JobContactGroupData;
import io.openjob.server.repository.dto.JobContactGroupDTO;
import io.openjob.server.repository.entity.JobContactGroup;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
@Component
public class JobContactGroupDataImpl implements JobContactGroupData {

    private final JobContactGroupDAO jobContactGroupDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public JobContactGroupDataImpl(JobContactGroupDAO jobContactGroupDAO, RedisOperation redisOperation) {
        this.jobContactGroupDAO = jobContactGroupDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(JobContactGroupDTO dto) {
        JobContactGroup entity = BeanMapperUtil.map(dto, JobContactGroup.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return jobContactGroupDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<JobContactGroupDTO> dtoList) {
        List<JobContactGroup> entityList = BeanMapperUtil.mapList(dtoList, JobContactGroupDTO.class, JobContactGroup.class);

        return jobContactGroupDAO.batchAdd(entityList);
    }

    @Override
    public JobContactGroupDTO getById(Long id) {
        return BeanMapperUtil.map(jobContactGroupDAO.getById(id), JobContactGroupDTO.class);
    }

    @Override
    public JobContactGroupDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getJobContactGroupByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(JobContactGroupDTO dto) {
        JobContactGroup entity = BeanMapperUtil.map(dto, JobContactGroup.class);

        redisOperation.delete(CacheKey.getJobContactGroupByIdKey(dto.getId()));

        return jobContactGroupDAO.updateById(entity);
    }
}

