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
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dto.JobPageDTO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Component
public class JobServiceImpl implements JobService {

    private final JobDAO jobDAO;
    private final AppDAO appDAO;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO, AppDAO appDAO) {
        this.jobDAO = jobDAO;
        this.appDAO = appDAO;
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
        if (CollectionUtils.isEmpty(jobPageDTO.getList())) {
            return PageUtil.emptyList(ListJobVO.class);
        }

        // App list.
        List<Long> appIds = jobPageDTO.getList().stream()
                .map(Job::getAppId).distinct().collect(Collectors.toList());
        Map<Long, App> appMap = this.appDAO.getByIds(appIds).stream()
                .collect(Collectors.toMap(App::getId, a -> a));

        // Page
        return PageUtil.convert(jobPageDTO, j -> {
            ListJobVO listJobVO = ObjectUtil.mapObject(j, ListJobVO.class);
            App app = appMap.get(j.getAppId());
            if (Objects.nonNull(app)) {
                listJobVO.setAppName(app.getName());
            }
            return listJobVO;
        });
    }
}
