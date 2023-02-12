package io.openjob.server.admin.service;

import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceService {
    PageVO<ListJobInstanceVO> list(ListJobInstanceRequest request);
}