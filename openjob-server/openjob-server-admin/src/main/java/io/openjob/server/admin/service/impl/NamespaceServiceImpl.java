package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.namespace.AddNamespaceRequest;
import io.openjob.server.admin.request.namespace.ListNamespaceRequest;
import io.openjob.server.admin.request.namespace.UpdateNamespaceRequest;
import io.openjob.server.admin.request.namespace.UpdateStatusNamespaceRequest;
import io.openjob.server.admin.request.namespace.DeleteNamespaceRequest;
import io.openjob.server.admin.vo.namespace.DeleteNamespaceVO;
import io.openjob.server.admin.service.NamespaceService;
import io.openjob.server.admin.vo.namespace.AddNamespaceVO;
import io.openjob.server.admin.vo.namespace.ListNamespaceVO;
import io.openjob.server.admin.vo.namespace.UpdateNamespaceStatusVO;
import io.openjob.server.admin.vo.namespace.UpdateNamespaceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.ObjectUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class NamespaceServiceImpl implements NamespaceService {
    private final NamespaceDAO namespaceDAO;

    @Autowired
    public NamespaceServiceImpl(NamespaceDAO namespaceDAO) {
        this.namespaceDAO = namespaceDAO;
    }

    @Override
    public AddNamespaceVO add(AddNamespaceRequest addRequest) {
        Namespace namespace = new Namespace();
        namespace.setName(addRequest.getName());
        namespace.setUuid(UUID.randomUUID().toString());

        // Save
        long id = this.namespaceDAO.save(namespace);
        return new AddNamespaceVO().setId(id);
    }

    @Override
    public UpdateNamespaceVO update(UpdateNamespaceRequest updateRequest) {
        this.namespaceDAO.save(ObjectUtil.mapObject(updateRequest, Namespace.class));
        return new UpdateNamespaceVO();
    }

    @Override
    public DeleteNamespaceVO delete(DeleteNamespaceRequest deleteNamespaceRequest) {
        return null;
    }

    @Override
    public UpdateNamespaceStatusVO updateStatus(UpdateStatusNamespaceRequest updateStatusRequest) {
        this.namespaceDAO.save(ObjectUtil.mapObject(updateStatusRequest, Namespace.class));
        return new UpdateNamespaceStatusVO();
    }

    @Override
    public PageVO<ListNamespaceVO> list(ListNamespaceRequest listRequest) {
        PageDTO<Namespace> pageDTO = this.namespaceDAO.pageList(listRequest.getName(), listRequest.getPage(), listRequest.getSize());
        return PageUtil.convert(pageDTO, n -> ObjectUtil.mapObject(n, ListNamespaceVO.class));
    }
}
