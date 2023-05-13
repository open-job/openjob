package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.entity.Namespace;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
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
     * Update
     *
     * @param namespace namespace
     * @return Long
     */
    Long update(Namespace namespace);

    /**
     * List by page.
     *
     * @param searchName search name
     * @param page       page
     * @param size       size
     * @return List
     */
    PageDTO<Namespace> pageList(String searchName, Integer page, Integer size);

    /**
     * Get by id.
     *
     * @param id id
     * @return Optional
     */
    Optional<Namespace> getById(Long id);

    /**
     * Get by ids.
     *
     * @param ids ids
     * @return List
     */
    List<Namespace> getByIds(List<Long> ids);
}
