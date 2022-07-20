package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
