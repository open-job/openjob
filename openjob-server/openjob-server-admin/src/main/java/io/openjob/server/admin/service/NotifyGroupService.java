package io.openjob.server.admin.service;

import io.openjob.server.admin.request.notify.NotifyGroupAddRequest;
import io.openjob.server.admin.vo.notify.NotifyGroupAddVO;
import io.openjob.server.admin.request.notify.NotifyGroupQueryRequest;
import io.openjob.server.admin.vo.notify.NotifyGroupQueryVO;
import io.openjob.server.admin.request.notify.NotifyGroupUpdateRequest;
import io.openjob.server.admin.vo.notify.NotifyGroupUpdateVO;
import io.openjob.server.admin.request.notify.NotifyGroupDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyGroupListRequest;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @date 2022-11-15 14:20:00
 * @since 1.0.0
 */
public interface NotifyGroupService  {

    /**
     * Add NotifyGroup
     *
     * @param reqDTO reqDTO
     * @return NotifyGroupAddVO
     */
    NotifyGroupAddVO add(NotifyGroupAddRequest reqDTO);

    /**
     * Get one NotifyGroup
     *
     * @param reqDTO reqDTO
     * @return NotifyGroupQueryVO
     */
    NotifyGroupQueryVO query(NotifyGroupQueryRequest reqDTO);

    /**
     * Update one NotifyGroup
     *
     * @param reqDTO reqDTO
     * @return NotifyGroupUpdateVO
     */
    NotifyGroupUpdateVO update(NotifyGroupUpdateRequest reqDTO);

    /**
     * Delete one NotifyGroup
     *
     * @param reqDTO reqDTO
     * @return NotifyGroupUpdateVO
     */
    NotifyGroupUpdateVO delete(NotifyGroupDeleteRequest reqDTO);

    /**
     * Get page list NotifyGroup
     *
     * @param reqDTO reqDTO
     * @return NotifyGroupListVO
     */
    PageDTO<NotifyGroupQueryVO> getPageList(NotifyGroupListRequest reqDTO);
}

