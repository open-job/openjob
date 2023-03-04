package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.server.JobSlotRequest;
import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.service.ServerService;
import io.openjob.server.admin.vo.server.JobSlotListVO;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        PageDTO<Server> paging = this.serverDAO.pageList(request.getAddress(), request.getPage(), request.getSize());
        return PageUtil.convert(paging, s -> BeanMapperUtil.map(s, ServerListVO.class));
    }

    @Override
    public PageVO<JobSlotListVO> getJobSlotsList(JobSlotRequest request) {
        // Job slots list.
        PageDTO<JobSlots> paging = this.jobSlotsDAO.pageList(request.getPage(), request.getSize());

        // Server map
        Set<Long> serverIdS = paging.getList().stream().map(JobSlots::getServerId).collect(Collectors.toSet());
        Map<Long, Server> serverMap = this.serverDAO.listServerByIds(new ArrayList<>(serverIdS)).stream()
                .collect(Collectors.toMap(Server::getId, s -> s));

        return PageUtil.convert(paging, s -> {
            JobSlotListVO jobSlotListVO = BeanMapperUtil.map(s, JobSlotListVO.class);
            Server server = serverMap.get(jobSlotListVO.getServerId());
            if (Objects.nonNull(server)) {
                jobSlotListVO.setAkkaAddress(server.getAkkaAddress());
                jobSlotListVO.setServerStatus(server.getStatus());
            }
            return jobSlotListVO;
        });
    }
}
