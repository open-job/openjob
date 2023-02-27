package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.request.job.DeleteDelayInstanceVO;
import io.openjob.server.admin.service.DelayInstanceService;
import io.openjob.server.admin.vo.delay.DeleteDelayVO;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.common.vo.PageVO;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    @Override
    public PageVO<ListDelayInstanceVO> pageList(ListDelayInstanceRequest request) {
        return null;
    }

    @Override
    public DeleteDelayInstanceVO delete(DeleteDelayInstanceVO request) {
        return null;
    }
}
