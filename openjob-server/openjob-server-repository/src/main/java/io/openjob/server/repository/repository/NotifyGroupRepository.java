package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.NotifyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 21:21:27
 * @since 1.0.0
 */
public interface NotifyGroupRepository extends JpaRepository<NotifyGroup, Long> {
}

