package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.server.JobSlotRequest;
import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.service.ServerService;
import io.openjob.server.admin.vo.server.JobSlotListVO;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riki
 * @since 1.0.0
 */
@Service
public class ServerServiceImpl implements ServerService {

    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public ServerServiceImpl(ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO) {
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
    }

    @Override
    public PageVO<ServerListVO> getServerList(ServerListRequest request) {
        PageDTO<Server> paging = this.serverDAO.pageList(request.getPage(), request.getSize());
        return PageUtil.convert(paging, s -> ObjectUtil.mapObject(s, ServerListVO.class));
    }

    @Override
    public PageVO<JobSlotListVO> getJobSlotsList(JobSlotRequest request) {
        PageDTO<JobSlots> paging = this.jobSlotsDAO.pageList(request.getPage(), request.getSize());
        return PageUtil.convert(paging, s -> ObjectUtil.mapObject(s, JobSlotListVO.class));
    }
}
