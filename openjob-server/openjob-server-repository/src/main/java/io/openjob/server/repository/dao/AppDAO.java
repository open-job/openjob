package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.entity.App;

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
     * Update
     *
     * @param app app
     * @return Long
     */
    Long update(App app);

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
     * @param namespaceId namespaceId
     * @param page        current page
     * @param size        limit size
     * @return Apps
     */
    PageDTO<App> pageList(Long namespaceId, String searchName, Integer page, Integer size);
}
