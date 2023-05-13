package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    /**
     * Find user by username
     *
     * @param username username
     * @return AdminUser
     */
    AdminUser findByUsername(String username);

    /**
     * Find user by token
     *
     * @param token   token
     * @param deleted deleted
     * @return AdminUser
     */
    AdminUser findByTokenAndDeleted(String token, Integer deleted);

    /**
     * Find user by sessionKey
     *
     * @param sessionKey session
     * @param deleted    deleted
     * @return AdminUser
     */
    AdminUser findBySessionKeyAndDeleted(String sessionKey, Integer deleted);
}

