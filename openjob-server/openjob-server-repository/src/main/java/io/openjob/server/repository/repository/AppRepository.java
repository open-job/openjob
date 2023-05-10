package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface AppRepository extends JpaRepository<App, Long> {

    /**
     * Find by name.
     *
     * @param name name
     * @return App
     */
    App findAppByName(String name);
}