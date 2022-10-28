package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.System;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface SystemRepository extends JpaRepository<System, Integer> {
}
