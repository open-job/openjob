package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.JobInstance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface JobInstanceExecutorRepository extends JpaSpecificationExecutor<JobInstance> {
}
