package io.openjob.server.admin.service;

import io.openjob.server.admin.request.home.DelayChartRequest;
import io.openjob.server.admin.request.home.JobChartRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.vo.home.DelayChartVO;
import io.openjob.server.admin.vo.home.JobChartVO;
import io.openjob.server.admin.vo.home.SystemDataVO;
import io.openjob.server.admin.vo.home.TaskDataVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public interface HomeService {

    /**
     * Task data
     *
     * @param taskDataRequest taskDataRequest
     * @return TaskDataVO
     */
    TaskDataVO taskData(TaskDataRequest taskDataRequest);

    /**
     * System data
     *
     * @param systemDataRequest systemDataRequest
     * @return SystemDataVO
     */
    SystemDataVO systemData(SystemDataRequest systemDataRequest);

    /**
     * Job circle
     *
     * @param jobChartRequest jobChartRequest
     * @return JobCircleVO
     */
    JobChartVO jobChart(JobChartRequest jobChartRequest);

    /**
     * Delay circle
     *
     * @param delayChartRequest delayChartRequest
     * @return DelayCircleVO
     */
    DelayChartVO delayChart(DelayChartRequest delayChartRequest);
}
