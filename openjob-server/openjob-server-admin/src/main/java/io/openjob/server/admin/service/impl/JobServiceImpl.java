package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.AddJobRequest;
import io.openjob.server.admin.request.ListJobRequest;
import io.openjob.server.admin.request.UpdateJobRequest;
import io.openjob.server.admin.request.UpdateJobStatusRequest;
import io.openjob.server.admin.service.JobService;
import io.openjob.server.admin.vo.AddJobVO;
import io.openjob.server.admin.vo.ListJobVO;
import io.openjob.server.admin.vo.UpdateJobStatusVO;
import io.openjob.server.admin.vo.UpdateJobVO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.entity.Job;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:21:16
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
        Job job = new Job();

        BeanUtils.copyProperties(job, addJobRequest);
        long id = this.jobDAO.save(job);

        return new AddJobVO().setId(id);
    }

    @Override
    public UpdateJobVO update(UpdateJobRequest updateJobRequest) {
        Job job = new Job();

        BeanUtils.copyProperties(job, updateJobRequest);
        this.jobDAO.save(job);

        return new UpdateJobVO();
    }

    @Override
    public UpdateJobStatusVO updateStatus(UpdateJobStatusRequest updateJobStatusRequest) {
        Job job = new Job();

        BeanUtils.copyProperties(job, updateJobStatusRequest);
        this.jobDAO.save(job);

        return new UpdateJobStatusVO();
    }

    @Override
    public ListJobVO list(ListJobRequest listJobRequest) {
        List<ListJobVO.JobVO> jobList = new ArrayList<>();

        this.jobDAO.list(listJobRequest.getPage(), listJobRequest.getSize()).forEach(n -> {
            ListJobVO.JobVO jobVO = new ListJobVO.JobVO();

            BeanUtils.copyProperties(jobVO, n);
            jobList.add(jobVO);
        });

        ListJobVO listJobVO = new ListJobVO();
        listJobVO.setPage(listJobRequest.getPage());
        listJobVO.setJobList(jobList);

        return listJobVO;
    }
}
