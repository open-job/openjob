package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobNotifyTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 13:44:56
 * @since 1.0.0
 */
public interface JobNotifyTemplateRepository extends JpaRepository<JobNotifyTemplate, Long> {
}

