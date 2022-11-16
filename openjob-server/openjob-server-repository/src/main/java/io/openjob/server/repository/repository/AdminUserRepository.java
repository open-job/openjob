package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 21:35:44
 * @since 1.0.0
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}

