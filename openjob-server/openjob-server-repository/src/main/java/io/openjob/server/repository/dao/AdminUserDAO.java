package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminUserDAO {

    /**
     * add AdminUser
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminUser entity);

    /**
     * batch add AdminUser
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminUser> entityList);

    /**
     * Update login
     *
     * @param id   id
     * @param ip   ip
     * @param time time
     */
    void updateLogin(Long id, String ip, Long time);


    /**
     * Update password
     *
     * @param id       id
     * @param nickname
     * @param password password
     * @param token
     */
    void updatePassword(Long id, String nickname, String password, String token);

    /**
     * get AdminUser by Id
     *
     * @param id id
     * @return AdminUser
     */
    AdminUser getById(Long id);

    /**
     * get AdminUser by username
     *
     * @param username username
     * @return AdminUser
     */
    AdminUser getByUsername(String username);

    /**
     * get AdminUser list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminUser list
     */
    Page<AdminUser> getPageList(Integer page, Integer size);

    /**
     * get AdminUser by token
     *
     * @param token token
     * @return AdminUser or null
     */
    AdminUser getByToken(String token);

    /**
     * get AdminUser by session key.
     *
     * @param sessionKey session key
     * @return AdminUser or null
     */
    AdminUser getBySessionKey(String sessionKey);
}

