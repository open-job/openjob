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
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        namespace.setDesc(addRequest.getDesc());
        namespace.setSecret(addRequest.getSecret());

        long id = this.namespaceDAO.save(namespace);

        return new AddNamespaceVO().setId(id);
    }

    @Override
    public UpdateNamespaceVO update(UpdateNamespaceRequest updateRequest) {
        Namespace namespace = new Namespace();

        namespace.setId(updateRequest.getId());
        namespace.setName(updateRequest.getName());
        namespace.setDesc(updateRequest.getDesc());

        if (StringUtils.isNotBlank(updateRequest.getSecret())) {
            namespace.setSecret(updateRequest.getSecret());
        }

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
    public ListNamespaceVO list(ListNamespaceRequest listRequest) {
        List<ListNamespaceVO.NamespaceVO> namespaceList = new ArrayList<>();

        this.namespaceDAO.list(listRequest.getName(), listRequest.getPage() - 1, listRequest.getSize()).forEach(n -> {
            ListNamespaceVO.NamespaceVO namespaceVO = new ListNamespaceVO.NamespaceVO();

            namespaceVO.setId(n.getId());
            namespaceVO.setName(n.getName());
            namespaceVO.setStatus(n.getStatus());
            namespaceVO.setUuid(UUID.randomUUID().toString());
            namespaceVO.setCreateTime(n.getCreateTime());
            namespaceList.add(namespaceVO);
        });

        ListNamespaceVO listNamespaceVO = new ListNamespaceVO();
        listNamespaceVO.setPage(listRequest.getPage());
        listNamespaceVO.setTotal(8);
        listNamespaceVO.setList(namespaceList);
        return listNamespaceVO;
    }
}
