package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.admin.vo.namespace.ListNamespaceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceServiceImpl implements JobInstanceService {
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public JobInstanceServiceImpl(JobInstanceDAO jobInstanceDAO) {
        this.jobInstanceDAO = jobInstanceDAO;
    }

    @Override
    public PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request) {
        PageDTO<JobInstance> pageDTO = this.jobInstanceDAO.pageList(ObjectUtil.mapObject(request, JobInstancePageDTO.class));
        return PageUtil.convert(pageDTO, n -> ObjectUtil.mapObject(n, ListJobInstanceVO.class));
    }
}
