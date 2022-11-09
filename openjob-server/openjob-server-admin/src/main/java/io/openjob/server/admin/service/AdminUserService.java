package io.openjob.server.admin.service;

import io.openjob.server.admin.entity.JobAdminUser;
import io.openjob.server.admin.request.JobAdminUserAddRequest;
import io.openjob.server.admin.vo.JobAdminUserAddVO;
import io.openjob.server.admin.request.JobAdminUserDetailRequest;
import io.openjob.server.admin.vo.JobAdminUserDetailVO;
import io.openjob.server.admin.request.JobAdminUserUpdateRequest;
import io.openjob.server.admin.vo.JobAdminUserUpdateVO;
import io.openjob.server.admin.request.JobAdminUserDeleteRequest;
import io.openjob.server.admin.vo.JobAdminUserDeleteVO;
import io.openjob.server.admin.request.JobAdminUserListRequest;
import io.openjob.server.admin.vo.JobAdminUserListVO;

/**
 * @author inhere
 * @date 2022-11-07 20:29:06
 * @since 1.0.0
 */
public interface AdminUserService  {

    /**
     * Add JobAdminUser
     *
     * @param reqDTO reqDTO
     * @return JobAdminUserAddVO
     */
    JobAdminUserAddVO add(JobAdminUserAddRequest reqDTO);

    /**
     * Get one JobAdminUser
     *
     * @param reqDTO reqDTO
     * @return JobAdminUserDetailDTO
     */
    JobAdminUserDetailDTO getById(JobAdminUserQueryRequest reqDTO);

    /**
     * Update one JobAdminUser
     *
     * @param reqDTO reqDTO
     * @return JobAdminUserUpdateVO
     */
    JobAdminUserUpdateVO update(JobAdminUserUpdateRequest reqDTO);

    /**
     * @param reqDTO reqDTO
     * @return JobAdminUserUpdateVO
     */
    JobAdminUserUpdateVO deleteById(JobAdminUserDeleteRequest reqDTO);

    /**
     * Get page list JobAdminUser
     *
     * @param reqDTO reqDTO
     * @return JobAdminUserListVO
     */
    PageDTO<JobAdminUserListVO> getPageList(JobAdminUserListRequest reqDTO);
}

