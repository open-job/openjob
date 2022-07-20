package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByAddress(String address);

    List<Worker> findByAppNameAndStatus(String appName, Integer status);
}
