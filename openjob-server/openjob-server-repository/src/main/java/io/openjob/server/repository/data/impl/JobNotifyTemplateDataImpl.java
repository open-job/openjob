package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.JobNotifyTemplateDAO;
import io.openjob.server.repository.data.JobNotifyTemplateData;
import io.openjob.server.repository.dto.JobNotifyTemplateDTO;
import io.openjob.server.repository.entity.JobNotifyTemplate;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 13:44:58
 * @since 1.0.0
 */
@Component
public class JobNotifyTemplateDataImpl implements JobNotifyTemplateData {

    private final JobNotifyTemplateDAO jobNotifyTemplateDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public JobNotifyTemplateDataImpl(JobNotifyTemplateDAO jobNotifyTemplateDAO, RedisOperation redisOperation) {
        this.jobNotifyTemplateDAO = jobNotifyTemplateDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(JobNotifyTemplateDTO dto) {
        JobNotifyTemplate entity = BeanMapperUtil.map(dto, JobNotifyTemplate.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return jobNotifyTemplateDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<JobNotifyTemplateDTO> dtoList) {
        List<JobNotifyTemplate> entityList = BeanMapperUtil.mapList(dtoList, JobNotifyTemplateDTO.class, JobNotifyTemplate.class);

        return jobNotifyTemplateDAO.batchAdd(entityList);
    }

    @Override
    public JobNotifyTemplateDTO getById(Long id) {
        return BeanMapperUtil.map(jobNotifyTemplateDAO.getById(id), JobNotifyTemplateDTO.class);
    }

    @Override
    public JobNotifyTemplateDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getJobNotifyTemplateByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(JobNotifyTemplateDTO dto) {
        JobNotifyTemplate entity = BeanMapperUtil.map(dto, JobNotifyTemplate.class);

        redisOperation.delete(CacheKey.getJobNotifyTemplateByIdKey(dto.getId()));

        return jobNotifyTemplateDAO.updateById(entity);
    }
}

