package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    /**
     * find by username
     *
     * @param username username
     * @return AdminUser
     */
    AdminUser findByUsername(String username);
}

