package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.job.AddJobRequest;
import io.openjob.server.admin.request.job.DeleteJobRequest;
import io.openjob.server.admin.request.job.ExecuteJobRequest;
import io.openjob.server.admin.request.job.ListJobRequest;
import io.openjob.server.admin.request.job.UpdateJobRequest;
import io.openjob.server.admin.request.job.UpdateJobStatusRequest;
import io.openjob.server.admin.service.JobService;
import io.openjob.server.admin.vo.job.AddJobVO;
import io.openjob.server.admin.vo.job.DeleteJobVO;
import io.openjob.server.admin.vo.job.ExecuteJobVO;
import io.openjob.server.admin.vo.job.ListJobVO;
import io.openjob.server.admin.vo.job.UpdateJobStatusVO;
import io.openjob.server.admin.vo.job.UpdateJobVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dto.JobPageDTO;
import io.openjob.server.repository.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Component
public class JobServiceImpl implements JobService {

    private final JobDAO jobDAO;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Override
    public AddJobVO add(AddJobRequest addJobRequest) {
        long id = this.jobDAO.save(ObjectUtil.mapObject(addJobRequest, Job.class));
        return new AddJobVO().setId(id);
    }

    @Override
    public UpdateJobVO update(UpdateJobRequest updateJobRequest) {
        this.jobDAO.update(ObjectUtil.mapObject(updateJobRequest, Job.class));
        return new UpdateJobVO();
    }

    @Override
    public DeleteJobVO delete(DeleteJobRequest deleteJobRequest) {
        this.jobDAO.updateByStatusOrDeleted(deleteJobRequest.getId(), null, CommonConstant.YES);
        return new DeleteJobVO();
    }

    @Override
    public UpdateJobStatusVO updateStatus(UpdateJobStatusRequest request) {
        this.jobDAO.updateByStatusOrDeleted(request.getId(), request.getStatus(), null);
        return new UpdateJobStatusVO();
    }

    @Override
    public ExecuteJobVO execute(ExecuteJobRequest request) {
        return null;
    }

    @Override
    public PageVO<ListJobVO> getPageList(ListJobRequest request) {
        PageDTO<Job> jobPageDTO = this.jobDAO.pageList(ObjectUtil.mapObject(request, JobPageDTO.class));
        return PageUtil.convert(jobPageDTO, j -> ObjectUtil.mapObject(j, ListJobVO.class));
    }
}
