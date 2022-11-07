package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobAdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 13:32:59
 * @since 1.0.0
 */
public interface JobAdminConfigRepository extends JpaRepository<JobAdminConfig, Long> {
}

