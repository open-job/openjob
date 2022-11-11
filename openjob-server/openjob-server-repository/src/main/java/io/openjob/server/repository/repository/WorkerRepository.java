package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    /**
     * Find by address.
     *
     * @param address address
     * @return Worker
     */
    Worker findByAddress(String address);

    /**
     * Find by status.
     *
     * @param status statusList<Long> slotsId, Integer status, Long time
     * @return List
     */
    List<Worker> findByStatus(Integer status);

    List<Worker> findBySlotsIdIsInAndStatusAndLastHeartbeatTimeLessThan(List<Long> slotsId, Integer status, Long time);

    List<Worker> findBySlotsIdIsInAndStatusAndLastHeartbeatTimeGreaterThan(List<Long> slotsId, Integer status, Long time);
}
