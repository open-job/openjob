package io.openjob.server.admin.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.CommonUtil;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.autoconfigure.AdminUserProperties;
import io.openjob.server.admin.constant.AdminConstant;
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
import io.openjob.server.admin.vo.part.PermItemVO;
import io.openjob.server.common.util.HmacUtil;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.repository.data.AdminPermissionData;
import io.openjob.server.repository.data.AdminRoleData;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminPermissionDTO;
import io.openjob.server.repository.dto.AdminRoleDTO;
import io.openjob.server.repository.dto.AdminUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRoleData adminRoleData;

    private final AdminUserData adminUserData;

    private final AdminPermissionData adminPermData;

    private final AdminUserProperties userProperties;

    private final Cache<String, AdminUserSessionDTO> loginCache;

    @Autowired
    public AdminLoginServiceImpl(
            AdminRoleData adminRoleData,
            AdminUserData adminUserData,
            AdminPermissionData adminPermData,
            AdminUserProperties userProperties,
            Cache<String, AdminUserSessionDTO> loginCache
    ) {
        this.adminRoleData = adminRoleData;
        this.adminUserData = adminUserData;
        this.adminPermData = adminPermData;
        this.userProperties = userProperties;

        this.loginCache = loginCache;
    }

    @Override
    public AdminUserLoginVO login(AdminUserLoginRequest reqDTO) {
        AdminUserDTO userDto = adminUserData.getByUsername(reqDTO.getUsername());
        checkLoginUser(userDto, reqDTO.getPassword());

        // query user perms and menus
        AdminUserSessionDTO sess = buildUserSessionDTO(userDto, false);

        List<String> permNames = new ArrayList<>();
        sess.getPerms().forEach(piVo -> permNames.add(piVo.getName()));

        // build return vo
        return AdminUserLoginVO.builder()
                .id(userDto.getId())
                .menus(sess.getMenus())
                .permNames(permNames)
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .supperAdmin(sess.getSupperAdmin())
                .sessionKey(sess.getSessionKey())
                .build();
    }

    @Override
    public AdminUserSessionDTO authByToken(String token) {
        AdminUserDTO userDto = adminUserData.getByToken(token);
        if (Objects.isNull(userDto)) {
            return null;
        }

        // query user perms and menus
        return buildUserSessionDTO(userDto, true);
    }

    @Override
    public LoginUserInfoVO loginUserInfo(LoginUserInfoRequest request, String sessKey) {
        AdminUserSessionDTO sess = loginCache.getIfPresent(sessKey);
        if (Objects.isNull(sess)) {
            throw AdminHttpStatusEnum.FORBIDDEN.newException();
        }

        LoginUserInfoVO vo = LoginUserInfoVO.builder()
                .id(sess.getId())
                .username(sess.getUsername())
                .nickname(sess.getNickname())
                .supperAdmin(sess.getSupperAdmin())
                .build();

        if (request.getWithMenus()) {
            vo.setMenus(sess.getMenus());
        }

        if (request.getWithPerms()) {
            vo.setPerms(sess.getPerms());
        }

        return vo;
    }

    private void checkLoginUser(AdminUserDTO entDTO, String passwd) {
        if (Objects.isNull(entDTO)) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        if (CommonUtil.isTrue(entDTO.getDeleted())) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        if (!HmacUtil.verifyPasswd(entDTO.getPasswd(), passwd, userProperties.getPasswdSalt())) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }

        if (CollectionUtils.isEmpty(entDTO.getRoleIds())) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }
    }

    private String userSessionKey(String username) {
        return DigestUtils.md5DigestAsHex((DateUtil.milliLongTime() + username).getBytes());
    }

    private AdminUserSessionDTO buildUserSessionDTO(AdminUserDTO userDto, Boolean onlyPerms) {
        AdminUserSessionDTO sess = AdminUserSessionDTO.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .build();

        // query user role and perms
        List<AdminRoleDTO> roles = adminRoleData.getByIds(userDto.getRoleIds());
        if (CollectionUtils.isEmpty(roles)) {
            AdminHttpStatusEnum.NOT_FOUND.throwException();
        }

        boolean isAdmin = false;
        List<Long> menuIds = new ArrayList<>();

        // collect admin_menu.id list
        for (AdminRoleDTO roleDto : roles) {
            if (CommonUtil.isTrue(roleDto.getAdmin())) {
                isAdmin = true;
            }

            if (!onlyPerms) {
                menuIds.addAll(roleDto.getMenuIds());
            }
            menuIds.addAll(roleDto.getPermIds());
        }

        sess.setSupperAdmin(isAdmin);

        List<PermItemVO> userPerms = new ArrayList<>();

        // query perms and menus. if is admin, query all valid menus.
        List<AdminPermissionDTO> dbMenuDtos;
        if (isAdmin) {
            dbMenuDtos = adminPermData.getAllMenus();
        } else {
            dbMenuDtos = adminPermData.getByIds(menuIds.stream().distinct().collect(Collectors.toList()));
        }

        List<AdminPermissionDTO> menuDtos = new ArrayList<>();

        for (AdminPermissionDTO menuDto : dbMenuDtos) {
            if (AdminConstant.MENU_TYPE_PERM.equals(menuDto.getType())) {
                PermItemVO pItem = new PermItemVO();
                pItem.setPath(menuDto.getPath());
                pItem.setName(menuDto.getName());
                userPerms.add(pItem);
            } else if (AdminConstant.MENU_TYPE_MENU.equals(menuDto.getType())) {
                menuDtos.add(menuDto);
            }
        }

        sess.setPerms(userPerms);
        if (onlyPerms) {
            return sess;
        }

        // format menus
        menuDtos.sort(Comparator.comparingInt(AdminPermissionDTO::getSort));
        List<MenuItemVO> userMenus = formatTreeMenus(menuDtos);

        // storage session data
        String sessKey = userSessionKey(userDto.getUsername());
        loginCache.put(sessKey, sess);

        sess.setMenus(userMenus);
        sess.setSessionKey(sessKey);

        return sess;
    }

    private List<MenuItemVO> formatTreeMenus(List<AdminPermissionDTO> dtoList) {

        List<String> sortProperties = new ArrayList<>();
        Map<Long, MenuItemVO> nodeList = new HashMap<>(dtoList.size());
        List<MenuItemVO> menuVos = new ArrayList<>();

        for (AdminPermissionDTO dataRecord : dtoList) {
            MenuItemVO node = new MenuItemVO();
            node.setId(dataRecord.getId());
            node.setPid(dataRecord.getPid());
            node.setName(dataRecord.getName());
            node.setPath(dataRecord.getPath());
            node.setSort(dataRecord.getSort());

            // build meta info
            MenuMetaVO menuMeta = new MenuMetaVO();
            ObjectUtil.copyObject(dataRecord.getMeta(), menuMeta);
            node.setMeta(menuMeta);

            // init sub menus
            List<MenuItemVO> temp = new ArrayList<>();
            node.setChildren(temp);

            nodeList.put(node.getId(), node);
        }

        for (AdminPermissionDTO dataRecord : dtoList) {
            MenuItemVO vo = nodeList.get(dataRecord.getId());

            if (CommonConstant.LONG_ZERO.equals(dataRecord.getPid())) {
                menuVos.add(vo);
            } else {
                nodeList.get(dataRecord.getPid()).getChildren().add(vo);
            }
        }

        return menuVos;
    }

    @Override
    public AdminUserLogoutVO logout(AdminUserLogoutRequest reqDTO, String sessKey) {
        AdminUserSessionDTO user = loginCache.getIfPresent(sessKey);
        if (Objects.isNull(user)) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }

        // remove session data
        loginCache.invalidate(sessKey);

        return new AdminUserLogoutVO();
    }

}
