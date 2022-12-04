package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.admin.request.notify.NotifyGroupAddRequest;
import io.openjob.server.admin.request.notify.NotifyGroupDeleteRequest;
import io.openjob.server.admin.request.notify.NotifyGroupListRequest;
import io.openjob.server.admin.request.notify.NotifyGroupQueryRequest;
import io.openjob.server.admin.request.notify.NotifyGroupUpdateRequest;
import io.openjob.server.admin.service.NotifyGroupService;
import io.openjob.server.admin.vo.notify.NotifyGroupAddVO;
import io.openjob.server.admin.vo.notify.NotifyGroupQueryVO;
import io.openjob.server.admin.vo.notify.NotifyGroupUpdateVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.data.NotifyGroupData;
import io.openjob.server.repository.dto.NotifyGroupDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class NotifyGroupServiceImpl implements NotifyGroupService {

    private final NotifyGroupData notifyGroupData;

    @Autowired
    public NotifyGroupServiceImpl(NotifyGroupData notifyGroupData) {
        this.notifyGroupData = notifyGroupData;
    }

    @Override
    public NotifyGroupAddVO add(NotifyGroupAddRequest reqDTO) {
        NotifyGroupDTO entDTO = new NotifyGroupDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyGroupAddVO retVo = new NotifyGroupAddVO();
        retVo.setId(notifyGroupData.add(entDTO));

        return retVo;
    }

    @Override
    public NotifyGroupUpdateVO update(NotifyGroupUpdateRequest reqDTO) {
        NotifyGroupDTO entDTO = new NotifyGroupDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        NotifyGroupUpdateVO retVo = new NotifyGroupUpdateVO();
        notifyGroupData.updateById(entDTO);
        return retVo;
    }

    @Override
    public NotifyGroupUpdateVO delete(NotifyGroupDeleteRequest reqDTO) {
        NotifyGroupDTO entDTO = new NotifyGroupDTO();
        entDTO.setDeleted(CommonConstant.YES);

        NotifyGroupUpdateVO retVo = new NotifyGroupUpdateVO();
        notifyGroupData.updateById(entDTO);

        return retVo;
    }

    @Override
    public NotifyGroupQueryVO query(NotifyGroupQueryRequest reqDTO) {
        NotifyGroupDTO entDTO = notifyGroupData.getById(reqDTO.getId());

        return ObjectUtil.mapObject(entDTO, NotifyGroupQueryVO.class);
    }

    @Override
    public PageDTO<NotifyGroupQueryVO> getPageList(NotifyGroupListRequest reqDTO) {
        // TODO
        return null;
    }
}

