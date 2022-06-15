package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Server;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface ServerDAO {
    Long save(Server server);

    Integer update(Long id, Integer status);

    List<Server> listServers(Integer status);
}
