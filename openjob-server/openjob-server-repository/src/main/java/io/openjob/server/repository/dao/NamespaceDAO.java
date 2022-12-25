package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Namespace;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface NamespaceDAO {

    /**
     * Save
     *
     * @param namespace namespace
     * @return Long
     */
    Long save(Namespace namespace);

    /**
     * List by page.
     *
     * @param page page
     * @param size size
     * @return List
     */
    Page<Namespace> list(Integer page, Integer size);

    /**
     * Get by id.
     *
     * @param id id
     * @return Optional
     */
    Optional<Namespace> getById(Long id);
}
