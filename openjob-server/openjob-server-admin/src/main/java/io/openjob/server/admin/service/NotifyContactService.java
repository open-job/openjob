package io.openjob.server.admin.service;

import io.openjob.server.admin.request.notify.NotifyContactAddRequest;
import io.openjob.server.admin.vo.notify.NotifyContactAddVO;
import io.openjob.server.admin.request.notify.NotifyContactQueryRequest;
import io.openjob.server.admin.vo.notify.NotifyContactQueryVO;
import io.openjob.server.admin.request.notify.NotifyContactUpdateRequest;
import io.openjob.server.admin.vo.notify.NotifyContactUpdateVO;
import io.openjob.server.admin.request.notify.NotifyContactDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyContactListRequest;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @date 2022-11-14 20:21:23
 * @since 1.0.0
 */
public interface NotifyContactService  {

    /**
     * Add NotifyContact
     *
     * @param reqDTO reqDTO
     * @return NotifyContactAddVO
     */
    NotifyContactAddVO add(NotifyContactAddRequest reqDTO);

    /**
     * Get one NotifyContact
     *
     * @param reqDTO reqDTO
     * @return NotifyContactQueryVO
     */
    NotifyContactQueryVO query(NotifyContactQueryRequest reqDTO);

    /**
     * Update one NotifyContact
     *
     * @param reqDTO reqDTO
     * @return NotifyContactUpdateVO
     */
    NotifyContactUpdateVO update(NotifyContactUpdateRequest reqDTO);

    /**
     * Delete one NotifyContact
     *
     * @param reqDTO reqDTO
     * @return NotifyContactUpdateVO
     */
    NotifyContactUpdateVO delete(NotifyContactDeleteRequest reqDTO);

    /**
     * Get page list NotifyContact
     *
     * @param reqDTO reqDTO
     * @return NotifyContactListVO
     */
    PageDTO<NotifyContactQueryVO> getPageList(NotifyContactListRequest reqDTO);
}

