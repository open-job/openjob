package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 13:33:32
 * @since 1.0.0
 */
public interface JobAdminUserRepository extends JpaRepository<JobAdminUser, Long> {
}

