package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Worker;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface WorkerDAO {

    /**
     * Save.
     *
     * @param worker worker
     * @return Long
     */
    Long save(Worker worker);

    List<Worker> listFailWorkers(List<Long> slotsId, Long time);

    List<Worker> listJoinWorkers(List<Long> slotsId, Long time);

    /**
     * Get by address.
     *
     * @param address address
     * @return Worker
     */
    Worker getByAddress(String address);

    /**
     * List online workers.
     *
     * @return List
     */
    List<Worker> listOnlineWorkers();
}
