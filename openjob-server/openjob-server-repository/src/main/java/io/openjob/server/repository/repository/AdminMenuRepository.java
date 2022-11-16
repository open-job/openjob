package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 21:34:57
 * @since 1.0.0
 */
public interface AdminMenuRepository extends JpaRepository<AdminMenu, Long> {
}

