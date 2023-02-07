package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import io.openjob.server.repository.repository.NamespaceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;
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
        namespace.setStatus(CommonConstant.YES);
        namespace.setCreateTime(timestamp);
        namespace.setUpdateTime(timestamp);
        return this.namespaceRepository.save(namespace).getId();
    }

    @Override
    public Long update(Namespace namespace) {
        Optional<Namespace> findNs = this.namespaceRepository.findById(namespace.getId());
        findNs.ifPresent(n -> {
            // Update name.
            if (StringUtils.isNotEmpty(namespace.getName())) {
                n.setName(namespace.getName());
            }

            // Update status
            if (Objects.nonNull(namespace.getStatus())) {
                n.setStatus(namespace.getStatus());
            }

            if (Objects.nonNull(namespace.getDeleted())){
                n.setDeleted(namespace.getDeleted());
            }

            n.setUpdateTime(DateUtil.timestamp());
            this.namespaceRepository.save(n);
        });
        return namespace.getId();
    }

    @Override
    public PageDTO<Namespace> pageList(String searchName, Integer page, Integer size) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        Namespace namespace = new Namespace();
        namespace.setDeleted(CommonConstant.NO);

        if (StringUtils.isNotEmpty(searchName)) {
            namespace.setName(searchName);
            matching = matching.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<Namespace> example = Example.of(namespace, matching);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<Namespace> pageDTO = new PageDTO<>();

        // Query
        Page<Namespace> pageList = this.namespaceRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(page);
            pageDTO.setSize(size);
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }

    @Override
    public Optional<Namespace> getById(Long id) {
        Namespace namespace = new Namespace();
        namespace.setId(id);
        return this.namespaceRepository.findOne(Example.of(namespace));
    }
}
