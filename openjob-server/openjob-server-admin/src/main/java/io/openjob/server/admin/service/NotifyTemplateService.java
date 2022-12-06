package io.openjob.server.admin.service;

import io.openjob.server.admin.request.notify.NotifyTemplateAddRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateListRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateQueryRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateUpdateRequest;
import io.openjob.server.admin.vo.notify.NotifyTemplateAddVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateQueryVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface NotifyTemplateService {

    /**
     * Add NotifyTemplate
     *
     * @param reqDTO reqDTO
     * @return NotifyTemplateAddVO
     */
    NotifyTemplateAddVO add(NotifyTemplateAddRequest reqDTO);

    /**
     * Get one NotifyTemplate
     *
     * @param reqDTO reqDTO
     * @return NotifyTemplateQueryVO
     */
    NotifyTemplateQueryVO query(NotifyTemplateQueryRequest reqDTO);

    /**
     * Update one NotifyTemplate
     *
     * @param reqDTO reqDTO
     * @return NotifyTemplateUpdateVO
     */
    NotifyTemplateUpdateVO update(NotifyTemplateUpdateRequest reqDTO);

    /**
     * Delete one NotifyTemplate
     *
     * @param reqDTO reqDTO
     * @return NotifyTemplateUpdateVO
     */
    NotifyTemplateUpdateVO delete(NotifyTemplateDeleteRequest reqDTO);

    /**
     * Get page list NotifyTemplate
     *
     * @param reqDTO reqDTO
     * @return NotifyTemplateListVO
     */
    PageDTO<NotifyTemplateQueryVO> getPageList(NotifyTemplateListRequest reqDTO);
}

