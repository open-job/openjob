package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.worker.WorkerListRequest;
import io.openjob.server.admin.service.WorkerService;
import io.openjob.server.admin.vo.worker.WorkerListVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.dto.WorkerPageDTO;
import io.openjob.server.repository.entity.Worker;
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
    public PageVO<WorkerListVO> getPage(WorkerListRequest request) {
        PageDTO<Worker> paging = this.workerDAO.getPage(BeanMapperUtil.map(request, WorkerPageDTO.class));
        return PageUtil.convert(paging, n -> BeanMapperUtil.map(n, WorkerListVO.class));
    }
}
