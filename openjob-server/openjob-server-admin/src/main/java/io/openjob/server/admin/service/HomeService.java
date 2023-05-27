package io.openjob.server.admin.service;

import io.openjob.server.admin.request.home.DelayCircleRequest;
import io.openjob.server.admin.request.home.DelayPercentageRequest;
import io.openjob.server.admin.request.home.JobCircleRequest;
import io.openjob.server.admin.request.home.JobPercentageRequest;
import io.openjob.server.admin.request.home.SystemDataRequest;
import io.openjob.server.admin.request.home.TaskDataRequest;
import io.openjob.server.admin.vo.home.DelayCircleVO;
import io.openjob.server.admin.vo.home.DelayPercentageVO;
import io.openjob.server.admin.vo.home.JobCircleVO;
import io.openjob.server.admin.vo.home.JobPercentageVO;
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
     * @param jobCircleRequest jobCircleRequest
     * @return JobCircleVO
     */
    JobCircleVO jobCircle(JobCircleRequest jobCircleRequest);

    /**
     * Job percentage
     *
     * @param jobPercentageRequest jobPercentageRequest
     * @return JobPercentageVO
     */
    JobPercentageVO jobPercentage(JobPercentageRequest jobPercentageRequest);

    /**
     * Delay circle
     *
     * @param delayCircleRequest delayCircleRequest
     * @return DelayCircleVO
     */
    DelayCircleVO delayCircle(DelayCircleRequest delayCircleRequest);

    /**
     * Delay percentage
     *
     * @param delayPercentageRequest delayPercentageRequest
     * @return DelayPercentageVO
     */
    DelayPercentageVO delayPercentage(DelayPercentageRequest delayPercentageRequest);
}
