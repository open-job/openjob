package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminMenuRepository extends JpaRepository<AdminMenu, Long> {
}

