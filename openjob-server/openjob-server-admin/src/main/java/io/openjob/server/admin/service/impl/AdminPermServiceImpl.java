package io.openjob.server.admin.service.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.CommonUtil;
import io.openjob.server.admin.request.perm.AdminPermAddRequest;
import io.openjob.server.admin.request.perm.AdminPermDeleteRequest;
import io.openjob.server.admin.request.perm.AdminPermListRequest;
import io.openjob.server.admin.request.perm.AdminPermQueryRequest;
import io.openjob.server.admin.request.perm.AdminPermUpdateRequest;
import io.openjob.server.admin.request.perm.AdminPermissionMenusRequest;
import io.openjob.server.admin.service.AdminPermService;
import io.openjob.server.admin.vo.part.MenuItemVO;
import io.openjob.server.admin.vo.part.MenuMetaVO;
import io.openjob.server.admin.vo.perm.AdminPermAddVO;
import io.openjob.server.admin.vo.perm.AdminPermQueryVO;
import io.openjob.server.admin.vo.perm.AdminPermUpdateVO;
import io.openjob.server.admin.vo.perm.AdminPermissionMenusVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.constant.PermissionTypeEnum;
import io.openjob.server.repository.dao.AdminPermissionDAO;
import io.openjob.server.repository.dao.AdminRoleDAO;
import io.openjob.server.repository.dao.AdminUserDAO;
import io.openjob.server.repository.data.AdminPermissionData;
import io.openjob.server.repository.dto.AdminPermissionDTO;
import io.openjob.server.repository.entity.AdminPermission;
import io.openjob.server.repository.entity.AdminRole;
import io.openjob.server.repository.entity.AdminUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author inhere
 * @since 1.0.0
 */
@Service
public class AdminPermServiceImpl implements AdminPermService {

    private final AdminRoleDAO adminRoleDAO;
    private final AdminUserDAO adminUserDAO;
    private final AdminPermissionData adminPermData;
    private final AdminPermissionDAO adminPermissionDAO;


    @Autowired
    public AdminPermServiceImpl(AdminPermissionData adminPermData, AdminPermissionDAO adminPermissionDAO, AdminRoleDAO adminRoleDAO, AdminUserDAO adminUserDAO) {
        this.adminPermData = adminPermData;
        this.adminPermissionDAO = adminPermissionDAO;
        this.adminRoleDAO = adminRoleDAO;
        this.adminUserDAO = adminUserDAO;
    }

    @Override
    public AdminPermAddVO add(AdminPermAddRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminPermAddVO retVo = new AdminPermAddVO();
        retVo.setId(adminPermData.add(entDTO));
        return retVo;
    }

    @Override
    public AdminPermUpdateVO update(AdminPermUpdateRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        BeanUtils.copyProperties(reqDTO, entDTO);

        AdminPermUpdateVO retVo = new AdminPermUpdateVO();
        adminPermData.updateById(entDTO);
        return retVo;
    }

    @Override
    public AdminPermUpdateVO delete(AdminPermDeleteRequest reqDTO) {
        AdminPermissionDTO entDTO = new AdminPermissionDTO();
        entDTO.setId(reqDTO.getId());
        entDTO.setDeleted(CommonConstant.YES);

        AdminPermUpdateVO retVo = new AdminPermUpdateVO();
        adminPermData.updateById(entDTO);

        return retVo;
    }

    @Override
    public AdminPermQueryVO query(AdminPermQueryRequest reqDTO) {
        AdminPermissionDTO entDTO = adminPermData.getById(reqDTO.getId());

        return BeanMapperUtil.map(entDTO, AdminPermQueryVO.class);
    }

    @Override
    public PageDTO<AdminPermQueryVO> getPageList(AdminPermListRequest reqDTO) {
        return null;
    }

    @Override
    public AdminPermissionMenusVO getMenus(AdminPermissionMenusRequest request) {
        AdminUser user = this.adminUserDAO.getById(request.getUid());
        List<AdminRole> roles = this.adminRoleDAO.getByIds(user.getRoleIds());

        // Menu ids.
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        Set<Long> menuIds = new HashSet<>();
        roles.forEach(r -> {
            if (CommonUtil.isTrue(r.getAdmin())) {
                isAdmin.set(true);
                return;
            }
            menuIds.addAll(r.getMenuIds());
        });

        //Menu list
        List<AdminPermission> permissionList;
        if (isAdmin.get()) {
            permissionList = this.adminPermissionDAO.getPermissionList(PermissionTypeEnum.MENU);
        } else {
            permissionList = this.adminPermissionDAO.getByIds(new ArrayList<>(menuIds));
        }

        List<MenuItemVO> menuItemList = this.formatTreeMenus(permissionList);
        AdminPermissionMenusVO adminPermissionMenusVO = new AdminPermissionMenusVO();
        adminPermissionMenusVO.setList(menuItemList);
        return adminPermissionMenusVO;
    }

    private List<MenuItemVO> formatTreeMenus(List<AdminPermission> dtoList) {
        Map<Long, MenuItemVO> nodeList = new HashMap<>(dtoList.size());
        List<MenuItemVO> menuVos = new ArrayList<>();

        for (AdminPermission dataRecord : dtoList) {
            MenuItemVO node = new MenuItemVO();
            node.setId(dataRecord.getId());
            node.setName(dataRecord.getName());
            node.setPath(dataRecord.getPath());
            node.setComponent(dataRecord.getMeta().getComponent());

            MenuMetaVO menuMetaVO = BeanMapperUtil.map(dataRecord.getMeta(), MenuMetaVO.class);
            menuMetaVO.setIsHide(CommonUtil.isTrue(dataRecord.getHidden()));
            node.setMeta(menuMetaVO);

            // init sub menus
            List<MenuItemVO> temp = new ArrayList<>();
            node.setChildren(temp);

            nodeList.put(node.getId(), node);
        }

        for (AdminPermission dataRecord : dtoList) {
            MenuItemVO vo = nodeList.get(dataRecord.getId());
            if (CommonConstant.LONG_ZERO.equals(dataRecord.getPid())) {
                menuVos.add(vo);
            } else {
                nodeList.get(dataRecord.getPid()).getChildren().add(vo);
            }
        }

        return menuVos;
    }
}

