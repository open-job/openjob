package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConst;
import io.openjob.server.admin.request.notify.NotifyContactAddRequest;
import io.openjob.server.admin.request.notify.NotifyContactDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyContactListRequest;
import io.openjob.server.admin.request.notify.NotifyContactQueryRequest;
import io.openjob.server.admin.request.notify.NotifyContactUpdateRequest;
import io.openjob.server.admin.service.NotifyContactService;
import io.openjob.server.admin.vo.notify.NotifyContactAddVO;
import io.openjob.server.admin.vo.notify.NotifyContactQueryVO;
import io.openjob.server.admin.vo.notify.NotifyContactUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.data.NotifyContactData;
import io.openjob.server.repository.dto.NotifyContactDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @date 2022-11-14 20:21:24
 * @since 1.0.0
 */
@Service
public class NotifyContactServiceImpl implements NotifyContactService {

    private final NotifyContactData notifyContactData;

    @Autowired
    public NotifyContactServiceImpl(NotifyContactData notifyContactData) {
        this.notifyContactData = notifyContactData;
    }

    @Override
    public NotifyContactAddVO add(NotifyContactAddRequest reqDTO) {
        NotifyContactDTO entDTO = new NotifyContactDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyContactAddVO retVo = new NotifyContactAddVO();
        retVo.setId(notifyContactData.add(entDTO));

        return retVo;
    }

    @Override
    public NotifyContactUpdateVO update(NotifyContactUpdateRequest reqDTO) {
        NotifyContactDTO entDTO = new NotifyContactDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyContactUpdateVO retVo = new NotifyContactUpdateVO();
        notifyContactData.updateById(entDTO);
        return retVo;
    }

    @Override
    public NotifyContactUpdateVO delete(NotifyContactDeleteRequest reqDTO) {
        NotifyContactDTO entDTO = new NotifyContactDTO();
        entDTO.setDeleted(CommonConst.YES);

        NotifyContactUpdateVO retVo = new NotifyContactUpdateVO();
        notifyContactData.updateById(entDTO);

        return retVo;
    }

    @Override
    public NotifyContactQueryVO query(NotifyContactQueryRequest reqDTO) {
        NotifyContactDTO entDTO = notifyContactData.getById(reqDTO.getId());
        NotifyContactQueryVO retVo = new NotifyContactQueryVO();

        BeanUtils.copyProperties(entDTO, retVo);

        return retVo;
    }

    @Override
    public PageDTO<NotifyContactQueryVO> getPageList(NotifyContactListRequest reqDTO) {
        // TODO
        return null;
    }
}

