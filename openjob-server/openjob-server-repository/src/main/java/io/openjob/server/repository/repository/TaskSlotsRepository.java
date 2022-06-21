package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.TaskSlots;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskSlotsRepository extends JpaRepository<TaskSlots, Long> {
}