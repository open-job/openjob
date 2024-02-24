package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.constant.CodeEnum;
import io.openjob.server.admin.request.app.AddAppRequest;
import io.openjob.server.admin.request.app.DeleteAppRequest;
import io.openjob.server.admin.request.app.ListAppRequest;
import io.openjob.server.admin.request.app.UpdateAppRequest;
import io.openjob.server.admin.service.AppService;
import io.openjob.server.admin.vo.app.AddAppVO;
import io.openjob.server.admin.vo.app.DeleteAppVO;
import io.openjob.server.admin.vo.app.ListAppVO;
import io.openjob.server.admin.vo.app.UpdateAppVO;
import io.openjob.server.cluster.data.RefreshData;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.entity.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Service
public class AppServiceImpl implements AppService {

    private final AppDAO appDAO;
    private final NamespaceDAO namespaceDAO;
    private final JobDAO jobDAO;
    private final DelayDAO delayDAO;
    private final RefreshData refreshData;


    @Autowired
    public AppServiceImpl(AppDAO appDAO, NamespaceDAO namespaceDAO, JobDAO jobDAO, DelayDAO delayDAO, RefreshData refreshData) {
        this.appDAO = appDAO;
        this.namespaceDAO = namespaceDAO;
        this.jobDAO = jobDAO;
        this.delayDAO = delayDAO;
        this.refreshData = refreshData;
    }

    @Override
    public AddAppVO add(AddAppRequest addRequest) {
        App app = this.appDAO.getAppByName(addRequest.getName());
        CodeEnum.APP_NAME_EXIST.assertIsTrue(Objects.isNull(app));

        Long id = this.appDAO.save(BeanMapperUtil.map(addRequest, App.class));

        AddAppVO addAppVO = new AddAppVO();
        addAppVO.setId(id);
        return addAppVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateAppVO update(UpdateAppRequest updateRequest) {
        // App name is exist and not self!
        App nameApp = this.appDAO.getAppByName(updateRequest.getName());
        if (Objects.nonNull(nameApp) && !nameApp.getId().equals(updateRequest.getId())) {
            CodeEnum.APP_NAME_EXIST.throwException();
        }

        // Update
        App app = BeanMapperUtil.map(BeanMapperUtil.map(updateRequest, App.class), App.class);
        this.appDAO.update(app);

        // Refresh cluster version.
        this.refreshData.refreshSystemClusterVersion();
        return new UpdateAppVO();
    }

    @Override
    public DeleteAppVO delete(DeleteAppRequest deleteAppRequest) {
        App byId = this.appDAO.getById(deleteAppRequest.getId());

        // Job/delay/workflow
        Job firstJob = this.jobDAO.getFirstByNamespaceAndAppid(byId.getNamespaceId(), byId.getId());
        Delay firstDelay = this.delayDAO.getFirstByNamespaceAndAppid(byId.getNamespaceId(), byId.getId());
        if (Objects.nonNull(firstJob) || Objects.nonNull(firstDelay)) {
            CodeEnum.APP_DELETE_INVALID.throwException();
        }

        this.appDAO.deleteById(deleteAppRequest.getId());
        return new DeleteAppVO();
    }

    @Override
    public PageVO<ListAppVO> list(ListAppRequest listRequest) {
        // App list
        PageDTO<App> appPageDTO = this.appDAO.pageList(listRequest.getNamespaceId(), listRequest.getName(), listRequest.getPage(), listRequest.getSize());
        if (CollectionUtils.isEmpty(appPageDTO.getList())) {
            return PageUtil.emptyList(ListAppVO.class);
        }

        // Name space list.
        List<Long> nsIds = appPageDTO.getList().stream()
                .map(App::getNamespaceId).distinct().collect(Collectors.toList());
        Map<Long, Namespace> nsMap = this.namespaceDAO.getByIds(nsIds)
                .stream().collect(Collectors.toMap(Namespace::getId, namespace -> namespace));

        // Page vo
        return PageUtil.convert(appPageDTO, a -> {
            ListAppVO listAppVO = BeanMapperUtil.map(a, ListAppVO.class);
            Namespace namespace = nsMap.get(a.getNamespaceId());
            if (Objects.nonNull(namespace)) {
                listAppVO.setNamespaceName(namespace.getName());
            }
            return listAppVO;
        });
    }
}
