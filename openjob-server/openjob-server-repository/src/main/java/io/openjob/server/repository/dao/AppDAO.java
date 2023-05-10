package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.entity.App;

import java.util.List;

/**
 * @author stelin swoft@qq.com
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
     * Get by ids.
     *
     * @param ids ids
     * @return List
     */
    List<App> getByIds(List<Long> ids);

    /**
     * List apps
     *
     * @param namespaceId namespaceId
     * @param searchName  search name
     * @param page        current page
     * @param size        limit size
     * @return Apps
     */
    PageDTO<App> pageList(Long namespaceId, String searchName, Integer page, Integer size);
}
