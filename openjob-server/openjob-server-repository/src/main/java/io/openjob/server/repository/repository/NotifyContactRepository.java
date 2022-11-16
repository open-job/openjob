package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.NotifyContact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 21:34:41
 * @since 1.0.0
 */
public interface NotifyContactRepository extends JpaRepository<NotifyContact, Long> {
}

