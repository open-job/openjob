package io.openjob.server.repository.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.entity.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/worker.sql")
public class WorkerDAOTest extends RepositoryTest {
    private final WorkerDAO workerDAO;

    @Autowired
    public WorkerDAOTest(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }

    @BeforeEach
    public void beforeMethod() {
        Worker worker = new Worker();
        worker.setAppId(1L);
        worker.setStatus(WorkerStatusEnum.ONLINE.getStatus());
        worker.setAppName("xxx-service");
        worker.setAddress("127.0.0.1:25200");
        worker.setSlotsId(1L);
        workerDAO.save(worker);

        Worker worker2 = new Worker();
        worker2.setAppId(1L);
        worker2.setStatus(WorkerStatusEnum.ONLINE.getStatus());
        worker2.setAppName("xxx-service");
        worker2.setAddress("127.0.0.1:25300");
        worker.setSlotsId(1L);
        workerDAO.save(worker2);


        Worker worker3 = new Worker();
        worker3.setAppId(1L);
        worker3.setStatus(WorkerStatusEnum.OFFLINE.getStatus());
        worker3.setAppName("xxx-service");
        worker3.setAddress("127.0.0.1:25600");
        worker.setSlotsId(1L);
        workerDAO.save(worker3);
    }

    @Test
    public void testSave() {
        int now = DateUtil.now();
        Worker worker = workerDAO.getByAddress("127.0.0.1:25600");
        worker.setUpdateTime(now);
        worker.setSlotsId(1L);
        workerDAO.save(worker);

        Worker worker2 = workerDAO.getByAddress("127.0.0.1:25600");
        Assertions.assertEquals(worker2.getUpdateTime(), now);
    }

    @Test
    public void testGetByAddress() {
        Worker worker = workerDAO.getByAddress("127.0.0.1:25300");

        Assertions.assertNotNull(worker);
        Assertions.assertEquals("xxx-service", worker.getAppName());
    }

    @Test
    public void testListOnlineWorkersByAppName() {
        List<Worker> workers = workerDAO.listOnlineWorkers();

        Assertions.assertNotNull(workers);
        Assertions.assertEquals(workers.size(), 2);
    }
}
