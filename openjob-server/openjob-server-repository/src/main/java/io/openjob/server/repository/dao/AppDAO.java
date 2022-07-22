package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.App;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface AppDAO {
    Long save(App app);

    /**
     * Get app by name.
     *
     * @param appName appName
     * @return App
     */
    App getAppByName(String appName);
}
