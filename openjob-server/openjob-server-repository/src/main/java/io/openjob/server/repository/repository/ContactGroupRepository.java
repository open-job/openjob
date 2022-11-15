package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.ContactGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Long> {
}

