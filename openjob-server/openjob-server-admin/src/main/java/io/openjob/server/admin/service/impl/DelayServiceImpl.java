package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.delay.AddDelayRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayRequest;
import io.openjob.server.admin.request.delay.UpdateDelayRequest;
import io.openjob.server.admin.request.delay.UpdateStatusDelayRequest;
import io.openjob.server.admin.service.DelayService;
import io.openjob.server.admin.vo.Delay.AddDelayVO;
import io.openjob.server.admin.vo.Delay.DeleteDelayVO;
import io.openjob.server.admin.vo.Delay.ListDelayVO;
import io.openjob.server.admin.vo.Delay.UpdateDelayVO;
import io.openjob.server.admin.vo.Delay.UpdateStatusDelayVO;
import io.openjob.server.common.vo.PageVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayServiceImpl implements DelayService {
    @Override
    public AddDelayVO add(AddDelayRequest addRequest) {
        return null;
    }

    @Override
    public PageVO<ListDelayVO> list(ListDelayRequest listDelayRequest) {
        return null;
    }

    @Override
    public DeleteDelayVO delete(DeleteDelayRequest deleteDelayRequest) {
        return null;
    }

    @Override
    public UpdateDelayVO update(UpdateDelayRequest updateDelayRequest) {
        return null;
    }

    @Override
    public UpdateStatusDelayVO updateStatus(UpdateStatusDelayRequest updateRequest) {
        return null;
    }
}
