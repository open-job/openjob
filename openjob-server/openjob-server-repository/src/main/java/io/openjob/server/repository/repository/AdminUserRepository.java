package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * Update login
     *
     * @param id        id
     * @param ip        ip
     * @param loginTime loginTime
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update AdminUser as a set a.loginIp=?2, a.loginTime=?3 where a.id=?1")
    void updateLogin(Long id, String ip, Long loginTime);

    /**
     * Update password
     *
     * @param id        id
     * @param nickname  nickname
     * @param password  password
     * @param token     token
     * @param timestamp timestamp
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update AdminUser as a set a.nickname=?2,a.passwd=?3,a.token=?4,a.updateTime=?5 where a.id=?1")
    void updatePassword(Long id, String nickname, String password, String token, Long timestamp);

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

