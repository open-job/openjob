package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.AdminSession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2023-02-08 14:21:14
 * @since 1.0.0
 */
public interface AdminSessionRepository extends JpaRepository<AdminSession, Long> {
}

