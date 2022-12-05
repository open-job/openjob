package io.openjob.server.admin.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.CommonUtil;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.autoconfigure.AdminUserProperties;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.request.user.AdminUserLoginRequest;
import io.openjob.server.admin.request.user.AdminUserLogoutRequest;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.part.MenuItemVO;
import io.openjob.server.admin.vo.part.MenuMetaVO;
import io.openjob.server.admin.vo.part.PermItemVO;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.admin.vo.user.AdminUserLogoutVO;
import io.openjob.server.common.exception.BusinessException;
import io.openjob.server.common.util.HmacUtil;
import io.openjob.server.repository.data.AdminMenuData;
import io.openjob.server.repository.data.AdminRoleData;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminMenuDTO;
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

    private final AdminMenuData adminMenuData;

    private final AdminUserProperties userProperties;

    private final Cache<String, AdminUserLoginVO> loginCache;

    @Autowired
    public AdminLoginServiceImpl(
            AdminRoleData adminRoleData,
            AdminUserData adminUserData,
            AdminMenuData adminMenuData, AdminUserProperties userProperties,
            Cache<String, AdminUserLoginVO> loginCache
    ) {
        this.adminRoleData = adminRoleData;
        this.adminUserData = adminUserData;
        this.adminMenuData = adminMenuData;
        this.userProperties = userProperties;

        this.loginCache = loginCache;
    }

    @Override
    public AdminUserLoginVO login(AdminUserLoginRequest reqDTO) {
        AdminUserDTO userDto = adminUserData.getByUsername(reqDTO.getUsername());
        checkLoginUser(userDto, reqDTO.getPasswd());

        // build return vo
        AdminUserLoginVO vo = AdminUserLoginVO.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .build();

        // query user perms and menus
        appendMenuAndPerms(vo, userDto, false);

        return vo;
    }

    @Override
    public AdminUserLoginVO authByToken(String token) {
        AdminUserDTO userDto = adminUserData.getByToken(token);
        if (Objects.isNull(userDto)) {
            return null;
        }

        // build return vo
        AdminUserLoginVO vo = AdminUserLoginVO.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .build();

        // query user perms and menus
        appendMenuAndPerms(vo, userDto, true);

        return vo;
    }

    private void checkLoginUser(AdminUserDTO entDTO, String passwd) {
        if (Objects.isNull(entDTO)) {
            throw new BusinessException("input username is not exists");
        }

        if (CommonUtil.isTrue(entDTO.getDeleted())) {
            throw new BusinessException("input username is invalid");
        }

        if (!HmacUtil.verifyPasswd(entDTO.getPasswd(), passwd, userProperties.getPasswdSalt())) {
            throw new BusinessException("input user password is error");
        }

        if (CollectionUtils.isEmpty(entDTO.getRoleIds())) {
            throw new BusinessException("not set role for user, please contact administrator");
        }
    }

    private String userSessionKey(String username) {
        return DigestUtils.md5DigestAsHex((DateUtil.milliLongTime() + username).getBytes());
    }

    private void appendMenuAndPerms(AdminUserLoginVO vo, AdminUserDTO entDTO, Boolean onlyPerms) {
        // query user role and perms
        List<AdminRoleDTO> roles = adminRoleData.getByIds(entDTO.getRoleIds());
        if (CollectionUtils.isEmpty(roles)) {
            throw new BusinessException("login user roles not found");
        }

        boolean isAdmin = false;
        List<Long> menuIds = new ArrayList<>();

        // collect admin_menu.id list
        for (AdminRoleDTO roleDto : roles) {
            if (CommonUtil.isTrue(roleDto.getAdmin())) {
                isAdmin = true;
            }

            if (!onlyPerms) {
                menuIds.addAll(roleDto.getMenus());
            }
            menuIds.addAll(roleDto.getPerms());
        }

        vo.setSupperAdmin(isAdmin);

        List<PermItemVO> userPerms = new ArrayList<>();

        // query perms and menus
        List<AdminMenuDTO> dbMenuDtos = adminMenuData.getByIds(menuIds.stream().distinct().collect(Collectors.toList()));
        List<AdminMenuDTO> menuDtos = new ArrayList<>();

        for (AdminMenuDTO menuDto : dbMenuDtos) {
            if (AdminConstant.MENU_TYPE_PERM.equals(menuDto.getType())) {
                PermItemVO pItem = new PermItemVO();
                pItem.setPath(menuDto.getPath());
                pItem.setName(menuDto.getName());
                userPerms.add(pItem);
            } else if (AdminConstant.MENU_TYPE_MENU.equals(menuDto.getType())) {
                menuDtos.add(menuDto);
            }
        }

        vo.setPerms(userPerms);
        if (onlyPerms) {
            return;
        }

        // format menus
        menuDtos.sort(Comparator.comparingInt(AdminMenuDTO::getSort));
        List<MenuItemVO> userMenus = formatTreeMenus(menuDtos);

        // storage session data
        String sessKey = userSessionKey(entDTO.getUsername());
        loginCache.put(sessKey, vo);

        vo.setMenus(userMenus);
        vo.setSessionKey(sessKey);
    }

    private List<MenuItemVO> formatTreeMenus(List<AdminMenuDTO> dtoList) {

        List<String> sortProperties = new ArrayList<>();
        Map<Long, MenuItemVO> nodeList = new HashMap<>(dtoList.size());
        List<MenuItemVO> menuVos = new ArrayList<>();

        for (AdminMenuDTO dataRecord : dtoList) {
            MenuItemVO node = new MenuItemVO();
            node.setId(dataRecord.getId());
            node.setPid(dataRecord.getPid());
            node.setName(dataRecord.getName());
            node.setPath(dataRecord.getPath());
            node.setSort(dataRecord.getSort());

            // build meta info
            MenuMetaVO menuMeta = new MenuMetaVO();
            menuMeta.setIcon(dataRecord.getMeta().getIcon());
            menuMeta.setTitle(dataRecord.getMeta().getTitle());
            node.setMeta(menuMeta);

            // init sub menus
            List<MenuItemVO> temp = new ArrayList<>();
            node.setChildren(temp);

            nodeList.put(node.getId(), node);
        }

        for (AdminMenuDTO dataRecord : dtoList) {
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
        AdminUserLoginVO user = loginCache.getIfPresent(sessKey);
        if (Objects.isNull(user)) {
            throw new BusinessException("can not call logout");
        }

        // remove session data
        loginCache.invalidate(sessKey);

        return new AdminUserLogoutVO();
    }

}
