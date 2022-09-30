package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.DelayInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceRepository extends JpaRepository<DelayInstance, Long> {
    List<DelayInstance> findBySlotsIdAndStatusAndAndExecuteTimeLessThanEqual(List<Long> slotIds, Integer status, Integer maxExecuteTime);
}
