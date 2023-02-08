package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminPermissionRepository extends JpaRepository<AdminPermission, Long> {
    /**
     * find by ID In given list
     *
     * @param ids ids
     * @return list
     */
    List<AdminPermission> findByIdIn(Collection<Long> ids);
}

