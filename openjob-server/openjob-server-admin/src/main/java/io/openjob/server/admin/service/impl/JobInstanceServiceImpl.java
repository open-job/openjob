package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.job.KillJobInstanceRequest;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.vo.job.KillJobInstanceVO;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class JobInstanceServiceImpl implements JobInstanceService {
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public JobInstanceServiceImpl(JobInstanceDAO jobInstanceDAO) {
        this.jobInstanceDAO = jobInstanceDAO;
    }

    @Override
    public PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request) {
        PageDTO<JobInstance> pageDTO = this.jobInstanceDAO.pageList(BeanMapperUtil.map(request, JobInstancePageDTO.class));
        return PageUtil.convert(pageDTO, n -> BeanMapperUtil.map(n, ListJobInstanceVO.class));
    }

    @Override
    public KillJobInstanceVO killInstance(KillJobInstanceRequest killRequest) {
        return new KillJobInstanceVO();
    }
}
