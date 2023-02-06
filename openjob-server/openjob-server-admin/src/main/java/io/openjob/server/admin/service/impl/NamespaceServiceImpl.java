package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.AddNamespaceRequest;
import io.openjob.server.admin.request.ListNamespaceRequest;
import io.openjob.server.admin.request.UpdateNamespaceRequest;
import io.openjob.server.admin.request.UpdateStatusNamespaceRequest;
import io.openjob.server.admin.service.NamespaceService;
import io.openjob.server.admin.vo.AddNamespaceVO;
import io.openjob.server.admin.vo.ListNamespaceVO;
import io.openjob.server.admin.vo.UpdateNamespaceStatusVO;
import io.openjob.server.admin.vo.UpdateNamespaceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import org.springframework.beans.BeanUtils;
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
        long id = this.namespaceDAO.save(namespace);
        return new AddNamespaceVO().setId(id);
    }

    @Override
    public UpdateNamespaceVO update(UpdateNamespaceRequest updateRequest) {
        Namespace namespace = new Namespace();
        namespace.setId(updateRequest.getId());
        namespace.setName(updateRequest.getName());

        this.namespaceDAO.save(namespace);
        return new UpdateNamespaceVO();
    }

    @Override
    public UpdateNamespaceStatusVO updateStatus(UpdateStatusNamespaceRequest updateStatusRequest) {
        Namespace namespace = new Namespace();
        namespace.setId(updateStatusRequest.getId());
        namespace.setStatus(updateStatusRequest.getStatus());

        this.namespaceDAO.save(namespace);
        return new UpdateNamespaceStatusVO();
    }

    @Override
    public PageVO<ListNamespaceVO> list(ListNamespaceRequest listRequest) {
        PageDTO<Namespace> pageDTO = this.namespaceDAO.pageList(listRequest.getName(), listRequest.getPage(), listRequest.getSize());
        return PageUtil.convert(pageDTO, n -> {
            ListNamespaceVO namespaceVO = new ListNamespaceVO();
            BeanUtils.copyProperties(n, namespaceVO);
            namespaceVO.setUuid(UUID.randomUUID().toString());
            return namespaceVO;
        });
    }
}
