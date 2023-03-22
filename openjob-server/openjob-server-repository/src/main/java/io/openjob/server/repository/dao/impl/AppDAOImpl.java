package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.repository.AppRepository;
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
import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class AppDAOImpl implements AppDAO {

    private final AppRepository appRepository;

    @Autowired
    public AppDAOImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public Long save(App app) {
        Long timestamp = DateUtil.timestamp();
        app.setDeleted(CommonConstant.NO);
        app.setDeleteTime(0L);
        app.setCreateTime(timestamp);
        app.setUpdateTime(timestamp);
        return appRepository.save(app).getId();
    }

    @Override
    public Long update(App app) {
        this.appRepository.findById(app.getId())
                .ifPresent(a -> {
                    // Update namespace.
                    if (Objects.nonNull(app.getNamespaceId())) {
                        a.setNamespaceId(app.getNamespaceId());
                    }

                    // Update name.
                    if (StringUtils.isNotEmpty(app.getName())) {
                        a.setName(app.getName());
                    }

                    // Update desc.
                    if (StringUtils.isNotEmpty(app.getDesc())) {
                        a.setDesc(app.getDesc());
                    }

                    if (Objects.nonNull(app.getDeleted())) {
                        a.setDeleted(app.getDeleted());
                    }

                    a.setUpdateTime(DateUtil.timestamp());
                    this.appRepository.save(a);
                });
        return app.getId();
    }

    @Override
    public App getAppByName(String appName) {
        return appRepository.findAppByName(appName);
    }

    @Override
    public List<App> getByIds(List<Long> ids) {
        return this.appRepository.findAllById(ids);
    }

    @Override
    public PageDTO<App> pageList(Long namespaceId, String searchName, Integer page, Integer size) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        App app = new App();
        app.setNamespaceId(namespaceId);
        app.setDeleted(CommonConstant.NO);
        if (StringUtils.isNotEmpty(searchName)) {
            app.setName(searchName);
            matching = matching.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<App> example = Example.of(app, matching);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<App> pageDTO = new PageDTO<>();

        // Query
        Page<App> pageList = this.appRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(page);
            pageDTO.setSize(size);
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
