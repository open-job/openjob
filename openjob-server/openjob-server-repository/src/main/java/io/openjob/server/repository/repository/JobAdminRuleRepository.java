package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobAdminRule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 13:40:57
 * @since 1.0.0
 */
public interface JobAdminRuleRepository extends JpaRepository<JobAdminRule, Long> {
}

