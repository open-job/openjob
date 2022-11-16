package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.Worker;
import io.openjob.server.repository.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class WorkerDAOImpl implements WorkerDAO {
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerDAOImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public Long save(Worker worker) {
        return workerRepository.save(worker).getId();
    }

    @Override
    public Worker getByAddress(String address) {
        return workerRepository.findByAddress(address);
    }

    @Override
    public List<Worker> listOnlineWorkers() {
        return workerRepository.findByStatus(WorkerStatusEnum.ONLINE.getStatus());
    }

    @Override
    public List<Worker> listAllWorkers() {
        return this.workerRepository.findAll();
    }
}
