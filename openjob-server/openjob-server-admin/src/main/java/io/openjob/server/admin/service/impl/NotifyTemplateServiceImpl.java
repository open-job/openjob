package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.notify.NotifyTemplateAddRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateListRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateQueryRequest;
import io.openjob.server.admin.request.notify.NotifyTemplateUpdateRequest;
import io.openjob.server.admin.service.NotifyTemplateService;
import io.openjob.server.admin.vo.notify.NotifyTemplateAddVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateQueryVO;
import io.openjob.server.admin.vo.notify.NotifyTemplateUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.data.NotifyTemplateData;
import io.openjob.server.repository.dto.NotifyTemplateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @date 2022-11-14 20:20:50
 * @since 1.0.0
 */
@Service
public class NotifyTemplateServiceImpl implements NotifyTemplateService {

    private final NotifyTemplateData notifyTemplateData;

    @Autowired
    public NotifyTemplateServiceImpl(NotifyTemplateData notifyTemplateData) {
        this.notifyTemplateData = notifyTemplateData;
    }

    @Override
    public NotifyTemplateAddVO add(NotifyTemplateAddRequest reqDTO) {
        NotifyTemplateDTO entDTO = new NotifyTemplateDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyTemplateAddVO retVo = new NotifyTemplateAddVO();
        retVo.setId(notifyTemplateData.add(entDTO));

        return retVo;
    }

    @Override
    public NotifyTemplateUpdateVO update(NotifyTemplateUpdateRequest reqDTO) {
        NotifyTemplateDTO entDTO = new NotifyTemplateDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyTemplateUpdateVO retVo = new NotifyTemplateUpdateVO();
        notifyTemplateData.updateById(entDTO);
        return retVo;
    }

    @Override
    public NotifyTemplateUpdateVO delete(NotifyTemplateDeleteRequest reqDTO) {
        NotifyTemplateDTO entDTO = new NotifyTemplateDTO();
        entDTO.setDeleted(CommonConstant.YES);

        NotifyTemplateUpdateVO retVo = new NotifyTemplateUpdateVO();
        notifyTemplateData.updateById(entDTO);

        return retVo;
    }

    @Override
    public NotifyTemplateQueryVO query(NotifyTemplateQueryRequest reqDTO) {
        NotifyTemplateDTO entDTO = notifyTemplateData.getById(reqDTO.getId());
        NotifyTemplateQueryVO retVo = new NotifyTemplateQueryVO();

        BeanUtils.copyProperties(entDTO, retVo);

        return retVo;
    }

    @Override
    public PageDTO<NotifyTemplateQueryVO> getPageList(NotifyTemplateListRequest reqDTO) {
        // TODO
        return null;
    }
}

