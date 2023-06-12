package io.openjob.server.repository.repository;

import io.openjob.server.repository.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface WorkerRepository extends JpaRepository<Worker, Long>, JpaSpecificationExecutor<Worker> {

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

    /**
     * Find by slot ids.
     *
     * @param slotIds slotIds
     * @return List
     */
    List<Worker> findBySlotsIdIsIn(List<Long> slotIds);

    /**
     * Count by namespace id
     *
     * @param namespaceId namespaceId
     * @param status      status
     * @param deleted     deleted
     * @return Long
     */
    Long countByNamespaceIdAndStatusAndDeleted(Long namespaceId, Integer status, Integer deleted);

    /**
     * Update last heartbeat time
     *
     * @param addresses         addresses
     * @param lastHeartbeatTime lastHeartbeatTime
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update Worker as w set w.lastHeartbeatTime=?2 where w.address in (?1)")
    Integer updateLastHeartbeatTimeByAddresses(List<String> addresses, Long lastHeartbeatTime);


    /**
     * Delete sever by create time and status
     *
     * @param lastTime lastTime
     * @param status   status
     * @return Long
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Long deleteByCreateTimeLessThanEqualAndStatus(Long lastTime, Integer status);
}
