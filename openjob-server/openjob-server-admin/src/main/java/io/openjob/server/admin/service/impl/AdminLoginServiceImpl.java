package io.openjob.server.admin.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.openjob.common.util.CommonUtil;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.autoconfigure.AdminUserProperties;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.request.user.AdminUserLoginRequest;
import io.openjob.server.admin.request.user.AdminUserLogoutRequest;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.part.MenuMeta;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.admin.vo.user.AdminUserLogoutVO;
import io.openjob.server.common.exception.BusinessException;
import io.openjob.server.common.util.HmacUtil;
import io.openjob.server.repository.data.AdminMenuData;
import io.openjob.server.repository.data.AdminRuleData;
import io.openjob.server.repository.data.AdminUserData;
import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.dto.AdminRuleDTO;
import io.openjob.server.repository.dto.AdminUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author inhere
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRuleData adminRuleData;

    private final AdminUserData adminUserData;

    private final AdminMenuData adminMenuData;

    private final AdminUserProperties userProperties;

    private final Cache<String, AdminUserLoginVO> loginCache;

    @Autowired
    public AdminLoginServiceImpl(
            AdminRuleData adminRuleData,
            AdminUserData adminUserData,
            AdminMenuData adminMenuData, AdminUserProperties userProperties,
            Cache<String, AdminUserLoginVO> loginCache
    ) {
        this.adminRuleData = adminRuleData;
        this.adminUserData = adminUserData;
        this.adminMenuData = adminMenuData;
        this.userProperties = userProperties;

        this.loginCache = loginCache;
    }

    @Override
    public AdminUserLoginVO login(AdminUserLoginRequest reqDTO) {
        AdminUserDTO entDTO = adminUserData.getByUsername(reqDTO.getUsername());
        checkLoginUser(entDTO, reqDTO.getPasswd());

        // build return vo
        AdminUserLoginVO vo = AdminUserLoginVO.builder()
                .id(entDTO.getId())
                .username(entDTO.getUsername())
                .nickname(entDTO.getNickname())
                .build();

        // query user perms and menus
        appendUserMenuAndPerms(vo, entDTO);

        return vo;
    }

    private void appendUserMenuAndPerms(AdminUserLoginVO vo, AdminUserDTO entDTO) {
        // query user rule and perms
        List<AdminRuleDTO> rules = adminRuleData.getByIds(entDTO.getRuleIds());
        if (CollectionUtils.isEmpty(rules)) {
            throw new BusinessException("login user rules not found");
        }

        boolean isAdmin = false;
        List<Long> menuIds = new ArrayList<>();

        // collect admin_menu.id list
        for (AdminRuleDTO ruleDto : rules) {
            if (CommonUtil.isTrue(ruleDto.getAdmin())) {
                isAdmin = true;
            }

            menuIds.addAll(ruleDto.getMenus());
            menuIds.addAll(ruleDto.getPerms());
        }

        vo.setSupperAdmin(isAdmin);

        List<AdminUserLoginVO.MenuItem> userMenus = new ArrayList<>();
        List<AdminUserLoginVO.PermItem> userPerms = new ArrayList<>();

        // query perms and menus
        List<AdminMenuDTO> menuDtos = adminMenuData.getByIds(menuIds.stream().distinct().collect(Collectors.toList()));
        for (AdminMenuDTO menuDto : menuDtos) {
            if (AdminConstant.MENU_TYPE_PERM.equals(menuDto.getType())) {
                AdminUserLoginVO.PermItem pItem = new AdminUserLoginVO.PermItem();
                pItem.setPath(menuDto.getPath());
                pItem.setName(menuDto.getName());
                userPerms.add(pItem);
            } else if (AdminConstant.MENU_TYPE_MENU.equals(menuDto.getType())) {
                AdminUserLoginVO.MenuItem mItem = new AdminUserLoginVO.MenuItem();
                mItem.setPath(menuDto.getPath());
                mItem.setName(menuDto.getName());

                MenuMeta menuMeta = new MenuMeta();
                menuMeta.setIcon(menuDto.getMeta().getIcon());
                menuMeta.setTitle(menuDto.getMeta().getTitle());
                mItem.setMeta(menuMeta);

                userMenus.add(mItem);

                // TODO format child menus
            }
        }

        vo.setPerms(userPerms);

        // storage session data
        String sessKey = userSessionKey(entDTO.getUsername());
        loginCache.put(sessKey, vo);

        vo.setMenus(userMenus);
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

        if (CollectionUtils.isEmpty(entDTO.getRuleIds())) {
            throw new BusinessException("not set rule for user, please contact administrator");
        }
    }

    private String userSessionKey(String username) {
        String str = DateUtil.milliLongTime() + username;
        return HmacUtil.encrypt(str, "", HmacUtil.HMAC_MD5);
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
