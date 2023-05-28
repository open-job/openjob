package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dto.WorkerPageDTO;
import io.openjob.server.repository.entity.Worker;

import java.util.List;

/**
 * @author stelin swoft@qq.com
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

    /**
     * List all workers.
     *
     * @param slotsIds slot ids.
     * @return List
     */
    List<Worker> listAllWorkersBySlotIds(List<Long> slotsIds);

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


    /**
     * Count by namespace id
     *
     * @param namespaceId namespaceId
     * @param status      status
     * @return Long
     */
    Long countByNamespaceIdAndStatus(Long namespaceId, Integer status);

    /**
     * page result
     *
     * @param workerPageDTO pageDTO
     * @return result
     */
    PageDTO<Worker> getPage(WorkerPageDTO workerPageDTO);
}
