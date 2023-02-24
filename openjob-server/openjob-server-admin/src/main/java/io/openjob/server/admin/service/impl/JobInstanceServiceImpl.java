package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.common.vo.PageVO;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceServiceImpl implements JobInstanceService {
    @Override
    public PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request) {
        return null;
    }
}
