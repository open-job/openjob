package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.dto.WorkerPageDTO;
import io.openjob.server.repository.entity.Namespace;
import io.openjob.server.repository.entity.Worker;
import io.openjob.server.repository.repository.WorkerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
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
    public PageDTO<Worker> getPage(WorkerPageDTO workerPageDTO) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        Worker worker = new Worker();
        worker.setDeleted(CommonConstant.NO);

        // Namespace id.
        if (Objects.nonNull(workerPageDTO.getNamespaceId())) {
            worker.setNamespaceId(workerPageDTO.getNamespaceId());
        }

        // App id.
        if (Objects.nonNull(workerPageDTO.getAppId())) {
            worker.setAppId(workerPageDTO.getAppId());
        }

        // Address.
        if (StringUtils.isNotEmpty(workerPageDTO.getAddress())) {
            worker.setAddress(workerPageDTO.getAddress());
            matching = matching.withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<Worker> example = Example.of(worker, matching);
        PageRequest pageRequest = PageRequest.of(workerPageDTO.getPage() - 1, workerPageDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<Worker> pageDTO = new PageDTO<>();

        // Query
        Page<Worker> pageList = this.workerRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(workerPageDTO.getPage());
            pageDTO.setSize(workerPageDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }

    @Override
    public List<Worker> listAllWorkersBySlotIds(List<Long> slotsIds) {
        return this.workerRepository.findBySlotsIdIsIn(slotsIds);
    }
}
