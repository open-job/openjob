package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.repository.AppRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class AppDAOImpl implements AppDAO {

    private final AppRepository appRepository;

    @Autowired
    public AppDAOImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public Long save(App app) {
        return appRepository.save(app).getId();
    }

    @Override
    public App getAppByName(String appName) {
        return appRepository.findAppByName(appName);
    }

    @Override
    public Page<App> list(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return this.appRepository.findAll(pageReq);
    }
}
