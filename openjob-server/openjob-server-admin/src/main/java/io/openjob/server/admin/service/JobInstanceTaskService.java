package io.openjob.server.admin.service;

import io.openjob.server.admin.request.task.ListChildTaskRequest;
import io.openjob.server.admin.request.task.ListSecondRequest;
import io.openjob.server.admin.request.task.ListTaskLogRequest;
import io.openjob.server.admin.request.task.ListTaskRequest;
import io.openjob.server.admin.vo.task.ListChildTaskVO;
import io.openjob.server.admin.vo.task.ListSecondVO;
import io.openjob.server.admin.vo.task.ListTaskLogVO;
import io.openjob.server.admin.vo.task.ListTaskVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
public interface JobInstanceTaskService {

    /**
     * Get circle list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListSecondVO> getCircleList(ListSecondRequest request);


    /**
     * Get child list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListChildTaskVO> getChildList(ListChildTaskRequest request);

    /**
     * Get task list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListTaskVO> getTaskList(ListTaskRequest request);

    /**
     * Get task log list
     *
     * @param request request
     * @return ListTaskLogVO
     */
    ListTaskLogVO getTaskLogList(ListTaskLogRequest request);
}
