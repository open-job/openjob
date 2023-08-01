package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.request.home.DelayChartRequest;
import io.openjob.server.admin.request.home.JobChartRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.service.HomeService;
import io.openjob.server.admin.util.ChartUtil;
import io.openjob.server.admin.vo.home.DataItemVO;
import io.openjob.server.admin.vo.home.DelayChartVO;
import io.openjob.server.admin.vo.home.JobChartVO;
import io.openjob.server.admin.vo.home.SystemDataVO;
import io.openjob.server.admin.vo.home.TaskDataVO;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.dto.GroupCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final AppDAO appDAO;
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;
    private final WorkerDAO workerDAO;

    @Autowired
    public HomeServiceImpl(
            JobDAO jobDAO,
            JobInstanceDAO jobInstanceDAO,
            DelayDAO delayDAO,
            DelayInstanceDAO delayInstanceDAO,
            AppDAO appDAO,
            ServerDAO serverDAO,
            JobSlotsDAO jobSlotsDAO,
            WorkerDAO workerDAO) {
        this.jobDAO = jobDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.delayDAO = delayDAO;
        this.delayInstanceDAO = delayInstanceDAO;
        this.appDAO = appDAO;
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.workerDAO = workerDAO;
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
        taskDataVO.setJob(new DataItemVO(jobTotal, jobNew));

        // Job Instance
        Long jobInstanceTotal = this.jobInstanceDAO.countTotalByNamespace(namespaceId);
        Long jobInstanceNew = this.jobInstanceDAO.countTotalByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp, null);
        taskDataVO.setJobInstance(new DataItemVO(jobInstanceTotal, jobInstanceNew));

        //Delay
        Long delayTotal = this.delayDAO.countByNamespace(namespaceId);
        Long delayNew = this.delayDAO.countByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp);
        taskDataVO.setDelay(new DataItemVO(delayTotal, delayNew));

        //DelaInstance
        Long delayInstanceTotal = this.delayInstanceDAO.countTotalByNamespace(namespaceId);
        Long delayInstanceNew = this.delayInstanceDAO.countTotalByNamespaceAndCreateTime(namespaceId, zeroTimestamp, timestamp, null);
        taskDataVO.setDelayInstance(new DataItemVO(delayInstanceTotal, delayInstanceNew));
        return taskDataVO;
    }

    @Override
    public SystemDataVO systemData(SystemDataRequest systemDataRequest) {
        SystemDataVO systemDataVO = new SystemDataVO();
        Long namespaceId = systemDataRequest.getNamespaceId();

        // App
        Long appTotal = this.appDAO.countByNamespaceId(namespaceId);
        systemDataVO.setApp(new DataItemVO(appTotal, 0L));

        // Server
        Long serverTotal = this.serverDAO.countByStatus(ServerStatusEnum.OK.getStatus());
        systemDataVO.setServer(new DataItemVO(serverTotal, 0L));

        // Worker
        Long workerTotal = this.workerDAO.countByNamespaceIdAndStatus(namespaceId, WorkerStatusEnum.ONLINE.getStatus());
        systemDataVO.setWorker(new DataItemVO(workerTotal, 0L));

        // Slots
        Long slotsTotal = this.jobSlotsDAO.countByAll();
        systemDataVO.setSlot(new DataItemVO(slotsTotal, 0L));
        return systemDataVO;
    }

    @Override
    public JobChartVO jobChart(JobChartRequest jobChartRequest) {
        JobChartVO jobChartVO = new JobChartVO();
        List<String> xData = new ArrayList<>();
        List<Long> successData = new ArrayList<>();
        List<Long> failData = new ArrayList<>();
        List<Long> runningData = new ArrayList<>();

        // Query by date
        if (this.isQueryByDay(jobChartRequest.getBeginTime(), jobChartRequest.getEndTime())) {
            Map<Integer, Long> successDateMap = this.jobInstanceDAO.countByNamespaceGroupByDateTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.SUCCESS.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> failDateMap = this.jobInstanceDAO.countByNamespaceGroupByDateTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.FAIL.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> runningMap = this.jobInstanceDAO.countByNamespaceGroupByDateTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.RUNNING.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

            // Date list data
            ChartUtil.getDateList(jobChartRequest.getBeginTime(), jobChartRequest.getEndTime())
                    .forEach(d -> {
                        xData.add(DateUtil.formatDatePattern(String.valueOf(d)));
                        successData.add(Optional.ofNullable(successDateMap.get(d)).orElse(0L));
                        failData.add(Optional.ofNullable(failDateMap.get(d)).orElse(0L));
                        runningData.add(Optional.ofNullable(runningMap.get(d)).orElse(0L));
                    });
        } else {
            // Query by hour
            Map<Integer, Long> successHourMap = this.jobInstanceDAO.countByNamespaceGroupByHourTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.SUCCESS.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> failHourMap = this.jobInstanceDAO.countByNamespaceGroupByHourTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.FAIL.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> runningMap = this.jobInstanceDAO.countByNamespaceGroupByHourTime(
                            jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime(), InstanceStatusEnum.RUNNING.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

            // Hour list data
            ChartUtil.getHourList(jobChartRequest.getBeginTime(), jobChartRequest.getEndTime())
                    .forEach(d -> {
                        xData.add(DateUtil.formatHourPattern(String.valueOf(d)));
                        successData.add(Optional.ofNullable(successHourMap.get(d)).orElse(0L));
                        failData.add(Optional.ofNullable(failHourMap.get(d)).orElse(0L));
                        runningData.add(Optional.ofNullable(runningMap.get(d)).orElse(0L));
                    });
        }

        // Status map
        Map<Integer, Long> statusGroupMap = this.jobInstanceDAO.countByNamespaceGroupByStatus(jobChartRequest.getNamespaceId(), jobChartRequest.getBeginTime(), jobChartRequest.getEndTime())
                .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

        // Percent list
        long total = statusGroupMap.values().stream().mapToLong(Long::longValue).sum();
        List<Long> percentList = Stream.of(
                        InstanceStatusEnum.WAITING.getStatus(),
                        InstanceStatusEnum.RUNNING.getStatus(),
                        InstanceStatusEnum.SUCCESS.getStatus(),
                        InstanceStatusEnum.FAIL.getStatus(),
                        InstanceStatusEnum.STOP.getStatus()
                ).map(s -> Math.round(Optional.ofNullable(statusGroupMap.get(s)).orElse(0L) * 10000.0 / total))
                .collect(Collectors.toList());

        jobChartVO.setAxisData(xData);
        jobChartVO.setSuccessData(successData);
        jobChartVO.setFailData(failData);
        jobChartVO.setRunningData(runningData);
        jobChartVO.setPercentList(percentList);
        return jobChartVO;
    }


    @Override
    public DelayChartVO delayChart(DelayChartRequest delayChartRequest) {
        DelayChartVO delayChartVO = new DelayChartVO();
        List<String> xData = Lists.newArrayList();
        List<Long> successData = new ArrayList<>();
        List<Long> failData = new ArrayList<>();
        List<Long> runningData = new ArrayList<>();

        // Query by date
        if (this.isQueryByDay(delayChartRequest.getBeginTime(), delayChartRequest.getEndTime())) {
            Map<Integer, Long> successDateMap = this.delayInstanceDAO.countByNamespaceGroupByDateTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.SUCCESS.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> failDateMap = this.delayInstanceDAO.countByNamespaceGroupByDateTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.FAILED.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> runningDateMap = this.delayInstanceDAO.countByNamespaceGroupByDateTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.RUNNING.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

            // Date list data
            ChartUtil.getDateList(delayChartRequest.getBeginTime(), delayChartRequest.getEndTime())
                    .forEach(d -> {
                        successData.add(Optional.ofNullable(successDateMap.get(d)).orElse(0L));
                        failData.add(Optional.ofNullable(failDateMap.get(d)).orElse(0L));
                        runningData.add(Optional.ofNullable(runningDateMap.get(d)).orElse(0L));
                        xData.add(DateUtil.formatDatePattern(String.valueOf(d)));
                    });
        } else {
            // Query by hour
            Map<Integer, Long> successHourMap = this.delayInstanceDAO.countByNamespaceGroupByHourTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.SUCCESS.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> failHourMap = this.delayInstanceDAO.countByNamespaceGroupByHourTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.FAILED.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));
            Map<Integer, Long> runningHourMap = this.delayInstanceDAO.countByNamespaceGroupByHourTime(
                            delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime(), TaskStatusEnum.RUNNING.getStatus())
                    .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

            // Hour list data
            ChartUtil.getHourList(delayChartRequest.getBeginTime(), delayChartRequest.getEndTime())
                    .forEach(d -> {
                        successData.add(Optional.ofNullable(successHourMap.get(d)).orElse(0L));
                        failData.add(Optional.ofNullable(failHourMap.get(d)).orElse(0L));
                        runningData.add(Optional.ofNullable(runningHourMap.get(d)).orElse(0L));
                        xData.add(DateUtil.formatHourPattern(String.valueOf(d)));
                    });
        }

        // Status map
        Map<Integer, Long> statusGroupMap = this.delayInstanceDAO.countByNamespaceGroupByStatus(delayChartRequest.getNamespaceId(), delayChartRequest.getBeginTime(), delayChartRequest.getEndTime())
                .stream().collect(Collectors.toMap(GroupCountDTO::getGroupBy, GroupCountDTO::getCount));

        // Percent list
        long total = statusGroupMap.values().stream().mapToLong(Long::longValue).sum();
        List<Long> percentList = Stream.of(
                        TaskStatusEnum.INIT.getStatus(),
                        TaskStatusEnum.RUNNING.getStatus(),
                        TaskStatusEnum.SUCCESS.getStatus(),
                        TaskStatusEnum.FAILED.getStatus(),
                        TaskStatusEnum.STOP.getStatus()
                ).map(s -> Math.round(Optional.ofNullable(statusGroupMap.get(s)).orElse(0L) * 10000.0 / total))
                .collect(Collectors.toList());

        delayChartVO.setAxisData(xData);
        delayChartVO.setSuccessData(successData);
        delayChartVO.setFailData(failData);
        delayChartVO.setRunningData(runningData);
        delayChartVO.setPercentList(percentList);
        return delayChartVO;
    }

    private Boolean isQueryByDay(Long beginTime, Long endTime) {
        return (endTime - beginTime) / TimeUnit.DAYS.toSeconds(1) >= 3;
    }
}
