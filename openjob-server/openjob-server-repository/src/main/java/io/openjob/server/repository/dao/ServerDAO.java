package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Server;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerDAO {
    /**
     * Save server.
     *
     * @param server server
     * @return Save id
     */
    Long save(Server server);

    /**
     * Update server by id and status
     *
     * @param id     id
     * @param status status
     * @return Effected rows.
     */
    Integer update(Long id, Integer status);

    /**
     * Get one server by akkaAddress.
     *
     * @param akkaAddress akkaAddress
     * @return Server
     */
    Optional<Server> getOne(String akkaAddress);

    /**
     * List servers by status.
     *
     * @param status status
     * @return List
     */
    List<Server> listServers(Integer status);
}
