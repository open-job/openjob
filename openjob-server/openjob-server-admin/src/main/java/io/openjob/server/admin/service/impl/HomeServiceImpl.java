package io.openjob.server.admin.service.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.request.home.DelayCircleRequest;
import io.openjob.server.admin.request.home.DelayPercentageRequest;
import io.openjob.server.admin.request.home.JobCircleRequest;
import io.openjob.server.admin.request.home.JobPercentageRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.service.HomeService;
import io.openjob.server.admin.vo.home.DelayCircleVO;
import io.openjob.server.admin.vo.home.DelayPercentageVO;
import io.openjob.server.admin.vo.home.JobCircleVO;
import io.openjob.server.admin.vo.home.JobPercentageVO;
import io.openjob.server.admin.vo.home.SystemDataVO;
import io.openjob.server.admin.vo.home.TaskDataVO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Service
public class HomeServiceImpl implements HomeService {
    private final JobDAO jobDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final DelayDAO delayDAO;
    private final DelayInstanceDAO delayInstanceDAO;

    @Autowired
    public HomeServiceImpl(JobDAO jobDAO, JobInstanceDAO jobInstanceDAO, DelayDAO delayDAO, DelayInstanceDAO delayInstanceDAO) {
        this.jobDAO = jobDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.delayDAO = delayDAO;
        this.delayInstanceDAO = delayInstanceDAO;
    }

    @Override
    public TaskDataVO taskData(TaskDataRequest taskDataRequest) {
        TaskDataVO taskDataVO = new TaskDataVO();
        Long namespaceId = taskDataRequest.getNamespaceId();
        Long zeroTimestamp = DateUtil.getZeroTimestamp();
        Long timestamp = DateUtil.timestamp();

        // Job
        Long jobTotal = this.jobDAO.countByNamespace(namespaceId);
        Long jobNew = this.jobDAO.countByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp);
        taskDataVO.setJob(new TaskDataVO.TaskDataItemVO(jobTotal, jobNew));

        // Job Instance
        Long jobInstanceTotal = this.jobInstanceDAO.countTotalByNamespace(namespaceId);
        Long jobInstanceNew = this.jobInstanceDAO.countTotalByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp, null);
        taskDataVO.setJobInstance(new TaskDataVO.TaskDataItemVO(jobInstanceTotal, jobInstanceNew));

        //Delay
        Long delayTotal = this.delayDAO.countByNamespace(namespaceId);
        Long delayNew = this.delayDAO.countByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp);
        taskDataVO.setDelay(new TaskDataVO.TaskDataItemVO(delayTotal, delayNew));

        //DelaInstance
        Long delayInstanceTotal = this.delayInstanceDAO.countTotalByNamespace(namespaceId);
        Long delayInstanceNew = this.delayInstanceDAO.countTotalByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp, null);
        taskDataVO.setDelayInstance(new TaskDataVO.TaskDataItemVO(delayInstanceTotal, delayInstanceNew));
        return taskDataVO;
    }

    @Override
    public SystemDataVO systemData(SystemDataRequest systemDataRequest) {
        return null;
    }

    @Override
    public JobCircleVO jobCircle(JobCircleRequest jobCircleRequest) {
        return null;
    }

    @Override
    public JobPercentageVO jobPercentage(JobPercentageRequest jobPercentageRequest) {
        return null;
    }

    @Override
    public DelayCircleVO delayCircle(DelayCircleRequest delayCircleRequest) {
        return null;
    }

    @Override
    public DelayPercentageVO delayPercentage(DelayPercentageRequest delayPercentageRequest) {
        return null;
    }
}
