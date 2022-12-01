package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminRuleRepository extends JpaRepository<AdminRule, Long> {
    /**
     * find by ID In given list
     *
     * @param ids ids
     * @return list
     */
    List<AdminRule> findByIdIn(Collection<Long> ids);
}

