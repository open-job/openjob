package io.openjob.server.admin.service.impl;

import io.openjob.server.admin.request.AddNamespaceRequest;
import io.openjob.server.admin.request.ListNamespaceRequest;
import io.openjob.server.admin.request.UpdateNamespaceRequest;
import io.openjob.server.admin.vo.AddNamespaceVO;
import io.openjob.server.admin.vo.ListNamespaceVO;
import io.openjob.server.admin.vo.UpdateNamespaceVO;
import io.openjob.server.admin.service.NamespaceService;
import io.openjob.server.repository.dao.NamespaceDAO;
import io.openjob.server.repository.entity.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        this.namespaceDAO.save(namespace);
        return new AddNamespaceVO();
    }

    @Override
    public UpdateNamespaceVO update(UpdateNamespaceRequest updateRequest) {
        Namespace namespace = new Namespace();
        this.namespaceDAO.save(namespace);
        return new UpdateNamespaceVO();
    }

    @Override
    public ListNamespaceVO list(ListNamespaceRequest listRequest) {
        List<ListNamespaceVO.NamespaceVO> namespaceList = new ArrayList<>();
        this.namespaceDAO.list(listRequest.getPage(), listRequest.getSize())
                .forEach(n -> {
                    ListNamespaceVO.NamespaceVO namespaceVO = new ListNamespaceVO.NamespaceVO();
                    namespaceList.add(namespaceVO);
                });

        ListNamespaceVO listNamespaceVO = new ListNamespaceVO();
        listNamespaceVO.setPage(listRequest.getPage());
        listNamespaceVO.setNamespaceList(namespaceList);
        return listNamespaceVO;
    }
}
