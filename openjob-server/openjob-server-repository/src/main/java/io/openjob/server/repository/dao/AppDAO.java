package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.App;
import org.springframework.data.domain.Page;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface AppDAO {

    /**
     * Save
     *
     * @param app app
     * @return Long
     */
    Long save(App app);

    /**
     * Get app by name.
     *
     * @param appName appName
     * @return App
     */
    App getAppByName(String appName);

    /**
     * List apps
     *
     * @param page current page
     * @param size limit size
     * @return Apps
     */
    Page<App> list(Integer page, Integer size);
}
