package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.service.ServerService;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author riki
 * @since 1.0.0
 */
@Service
public class ServerServiceImpl implements ServerService {

    private final ServerDAO serverDAO;

    public ServerServiceImpl(ServerDAO serverDAO) {
        this.serverDAO = serverDAO;
    }

    @Override
    public PageDTO<ServerListVO> getPageList(ServerListRequest request) {
        PageDTO<Server> paging =
                serverDAO.getPageList(request.getPage(), request.getSize());
        PageDTO<ServerListVO> result = new PageDTO<>();
        BeanUtils.copyProperties(paging, result);
        return result;
    }
}
