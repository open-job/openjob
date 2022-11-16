package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.NotifyTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 21:33:20
 * @since 1.0.0
 */
public interface NotifyTemplateRepository extends JpaRepository<NotifyTemplate, Long> {
}

