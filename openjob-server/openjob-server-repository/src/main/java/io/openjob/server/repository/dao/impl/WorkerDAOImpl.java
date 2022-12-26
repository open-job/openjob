package io.openjob.server.repository.dao.impl;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.dto.WorkerListReqDTO;
import io.openjob.server.repository.entity.Worker;
import io.openjob.server.repository.repository.WorkerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    public PageDTO<Worker> getPageList(WorkerListReqDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("id").descending());
        Specification<Worker> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getAppName())) {
                predicates.add(criteriaBuilder.like(root.get("appName"), request.getAppName()));
            }
            if (request.getAppId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("appId"), request.getAppId()));
            }
            if (request.getNamespaceId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("namespaceId"), request.getNamespaceId()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
        Page<Worker> pageResult = workerRepository.findAll(specification, pageable);
        PageDTO<Worker> paging = new PageDTO<>();
        paging.setList(pageResult.getContent());
        paging.setTotal(pageResult.getTotalElements());
        return paging;
    }

    @Override
    public List<Worker> listAllWorkersBySlotIds(List<Long> slotsIds) {
        return this.workerRepository.findBySlotsIdIsIn(slotsIds);
    }
}
