package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Server;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerDAO {
    Long save(Server server);

    Integer update(Long id, Integer status);

    Optional<Server> getOne(String akkaAddress);

    List<Server> listServers(Integer status);
}
