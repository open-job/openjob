package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.worker.WorkerListRequest;
import io.openjob.server.admin.service.WorkerService;
import io.openjob.server.admin.vo.worker.WorkerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.dto.WorkerListReqDTO;
import io.openjob.server.repository.entity.Worker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author riki
 * @since 1.0.0
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerDAO workerDAO;

    public WorkerServiceImpl(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }

    @Override
    public PageDTO<WorkerListVO> page(WorkerListRequest request) {
        PageDTO<Worker> paging =
                workerDAO.getPageList(ObjectUtil.mapObject(request, WorkerListReqDTO.class));
        PageDTO<WorkerListVO> result = new PageDTO<>();
        BeanUtils.copyProperties(paging, result);
        return result;
    }
}
