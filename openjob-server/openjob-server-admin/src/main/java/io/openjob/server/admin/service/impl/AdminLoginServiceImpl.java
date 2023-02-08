package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.CommonUtil;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.autoconfigure.AdminUserProperties;
import io.openjob.server.admin.constant.AdminHttpStatusEnum;
import io.openjob.server.admin.dto.AdminUserSessionDTO;
import io.openjob.server.admin.request.admin.AdminUserLoginRequest;
import io.openjob.server.admin.request.admin.AdminUserLogoutRequest;
import io.openjob.server.admin.request.admin.LoginUserInfoRequest;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.admin.AdminUserLoginVO;
import io.openjob.server.admin.vo.admin.AdminUserLogoutVO;
import io.openjob.server.admin.vo.admin.LoginUserInfoVO;
import io.openjob.server.admin.vo.part.MenuItemVO;
import io.openjob.server.admin.vo.part.MenuMetaVO;
import io.openjob.server.common.util.HmacUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.constant.PermissionTypeEnum;
import io.openjob.server.repository.dao.AdminPermissionDAO;
import io.openjob.server.repository.dao.AdminRoleDAO;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.dto.AdminPermissionDTO;
import io.openjob.server.repository.entity.AdminPermission;
import io.openjob.server.repository.entity.AdminRole;
import io.openjob.server.repository.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminUserDAO adminUserDAO;

    private final AdminRoleDAO adminRoleDAO;

    private final AdminPermissionDAO adminPermissionDAO;

    private final AdminUserProperties userProperties;

    @Autowired
    public AdminLoginServiceImpl(
            AdminUserDAO adminUserDAO,
            AdminRoleDAO adminRoleDAO, AdminPermissionDAO adminPermissionDAO, AdminUserProperties userProperties) {
        this.adminUserDAO = adminUserDAO;
        this.adminRoleDAO = adminRoleDAO;
        this.adminPermissionDAO = adminPermissionDAO;
        this.userProperties = userProperties;
    }

    @Override
    public AdminUserLoginVO login(AdminUserLoginRequest reqDTO) {
        AdminUser user = this.adminUserDAO.getByUsername(reqDTO.getUsername());

        // Check login user.
        checkLoginUser(user, reqDTO.getPassword());
        return buildUserLoginVO(user);
    }

    @Override
    public LoginUserInfoVO loginUserInfo(LoginUserInfoRequest request, String sessKey) {
//        AdminUserSessionDTO sess = loginCache.getIfPresent(sessKey);
//        if (Objects.isNull(sess)) {
//            throw AdminHttpStatusEnum.FORBIDDEN.newException();
//        }
//
//        LoginUserInfoVO vo = LoginUserInfoVO.builder()
//                .id(sess.getId())
//                .username(sess.getUsername())
//                .nickname(sess.getNickname())
//                .supperAdmin(sess.getSupperAdmin())
//                .build();
//
//        if (request.getWithMenus()) {
//            vo.setMenus(sess.getMenus());
//        }
//
//        if (request.getWithPerms()) {
//            vo.setPerms(sess.getPerms());
//        }
//
//        return vo;
        return null;
    }

    private void checkLoginUser(AdminUser user, String passwd) {
        if (Objects.isNull(user)) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        if (CommonUtil.isTrue(user.getDeleted())) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        if (!HmacUtil.verifyPasswd(user.getPasswd(), passwd, userProperties.getPasswdSalt())) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }

        if (CollectionUtils.isEmpty(user.getRoleIds())) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }
    }

    private String userSessionKey(String username) {
        return DigestUtils.md5DigestAsHex((DateUtil.milliLongTime() + username).getBytes());
    }

    private AdminUserLoginVO buildUserLoginVO(AdminUser user) {
        // Session key expire
        String sessionKey = user.getSessionKey();

        AdminUserLoginVO loginVO = AdminUserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .sessionKey(userSessionKey(sessionKey))
                .build();

        // Query user role and perms
        List<AdminRole> roles = this.adminRoleDAO.getByIds(user.getRoleIds());
        if (CollectionUtils.isEmpty(roles)) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        // Admin and permission ids
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        Set<Long> permIds = new HashSet<>();
        roles.forEach(r -> {
            if (CommonUtil.isTrue(r.getAdmin())) {
                isAdmin.set(true);
                return;
            }
            permIds.addAll(r.getPermIds());
        });
        loginVO.setSupperAdmin(isAdmin.get());

        //Admin permission
        if (isAdmin.get()) {
            List<String> perms = this.adminPermissionDAO.getPermissionList(PermissionTypeEnum.PERMISSION)
                    .stream().map(AdminPermission::getName)
                    .collect(Collectors.toList());

            loginVO.setPermNames(perms);
            return loginVO;
        }

        // Not admin permission
        List<String> perms = this.adminPermissionDAO.getByIds(new ArrayList<>(permIds))
                .stream().map(AdminPermission::getName)
                .collect(Collectors.toList());
        loginVO.setPermNames(perms);
        return loginVO;
    }


    @Override
    public AdminUserLogoutVO logout(AdminUserLogoutRequest reqDTO, String sessKey) {
//        AdminUserSessionDTO user = loginCache.getIfPresent(sessKey);
//        if (Objects.isNull(user)) {
//            AdminHttpStatusEnum.FORBIDDEN.throwException();
//        }
//
//        // remove session data
//        loginCache.invalidate(sessKey);

        return new AdminUserLogoutVO();
    }

}
