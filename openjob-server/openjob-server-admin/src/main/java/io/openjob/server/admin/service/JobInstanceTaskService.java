package io.openjob.server.admin.service;

import io.openjob.server.admin.request.task.ListChildTaskRequest;
import io.openjob.server.admin.request.task.ListTaskRequest;
import io.openjob.server.admin.request.task.ListTaskLogRequest;
import io.openjob.server.admin.request.task.StopTaskRequest;
import io.openjob.server.admin.vo.task.ListChildTaskVO;
import io.openjob.server.admin.vo.task.ListTaskVO;
import io.openjob.server.admin.vo.task.ListTaskLogVO;
import io.openjob.server.admin.vo.task.StopTaskVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
public interface JobInstanceTaskService {

    /**
     * Get task list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListTaskVO> getTaskList(ListTaskRequest request);


    /**
     * Get child list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListChildTaskVO> getChildList(ListChildTaskRequest request);

    /**
     * Stop task
     *
     * @param request request
     * @return StopTaskVO
     */
    StopTaskVO stopTask(StopTaskRequest request);

    /**
     * Get task log list
     *
     * @param request request
     * @return ListTaskLogVO
     */
    ListTaskLogVO getTaskLogList(ListTaskLogRequest request);
}
