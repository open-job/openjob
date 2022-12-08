package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.AddAppRequest;
import io.openjob.server.admin.request.ListAppRequest;
import io.openjob.server.admin.request.UpdateAppRequest;
import io.openjob.server.admin.service.AppService;
import io.openjob.server.admin.vo.AddAppVO;
import io.openjob.server.admin.vo.ListAppVO;
import io.openjob.server.admin.vo.UpdateAppVO;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 */
@Service
public class AppServiceImpl implements AppService {

    private final AppDAO appDAO;

    @Autowired
    public AppServiceImpl(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    @Override
    public AddAppVO add(AddAppRequest addRequest) {
        App app = new App();
        app.setNamespaceId(addRequest.getNamespaceId());
        app.setName(addRequest.getName());
        app.setDesc(addRequest.getDesc());
        app.setDeleteTime(0L);
        app.setDeleted(2);

        long id = appDAO.save(app);

        return new AddAppVO().setId(id);
    }

    @Override
    public UpdateAppVO update(UpdateAppRequest updateRequest) {
        App app = new App();
        app.setId(updateRequest.getId());
        app.setName(updateRequest.getName());
        app.setDesc(updateRequest.getDesc());

        appDAO.save(app);

        return new UpdateAppVO();
    }

    @Override
    public ListAppVO list(ListAppRequest listRequest) {
        List<ListAppVO.AppVO> appList = new ArrayList<>();

        appDAO.list(listRequest.getPage(), listRequest.getSize()).forEach(app -> {
            ListAppVO.AppVO appVO = new ListAppVO.AppVO();

            appVO.setId(app.getId());
            appVO.setName(app.getName());
            appVO.setDesc(app.getDesc());

            appList.add(appVO);
        });

        ListAppVO listAppVO = new ListAppVO();
        listAppVO.setPage(listRequest.getPage());
        listAppVO.setAppList(appList);

        return listAppVO;
    }
}
