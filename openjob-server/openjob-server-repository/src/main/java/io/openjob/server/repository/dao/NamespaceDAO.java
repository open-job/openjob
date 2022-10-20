package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Namespace;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface NamespaceDAO {
    Long save(Namespace namespace);

    List<Namespace> list(Integer page, Integer size);

    Optional<Namespace> getById(Long id);
}
