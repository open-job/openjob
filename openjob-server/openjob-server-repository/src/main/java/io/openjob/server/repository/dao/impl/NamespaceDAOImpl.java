package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import io.openjob.server.repository.repository.NamespaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class NamespaceDAOImpl implements NamespaceDAO {
    private final NamespaceRepository namespaceRepository;

    @Autowired
    public NamespaceDAOImpl(NamespaceRepository namespaceRepository) {
        this.namespaceRepository = namespaceRepository;
    }

    @Override
    public Long save(Namespace namespace) {
        Long timestamp = DateUtil.timestamp();
        namespace.setDeleted(CommonConstant.NO);
        namespace.setDeleteTime(0L);
        namespace.setCreateTime(timestamp);
        namespace.setUpdateTime(timestamp);
        return this.namespaceRepository.save(namespace).getId();
    }

    @Override
    public List<Namespace> list(Integer page, Integer size) {
        return this.namespaceRepository.findAll(PageRequest.of(page, size, Sort.by("create_time"))).toList();
    }

    @Override
    public Optional<Namespace> getById(Long id) {
        Namespace namespace = new Namespace();
        namespace.setId(id);
        return this.namespaceRepository.findOne(Example.of(namespace));
    }
}
