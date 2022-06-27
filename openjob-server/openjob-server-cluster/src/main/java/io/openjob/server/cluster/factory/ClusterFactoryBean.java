package io.openjob.server.cluster.factory;

import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.repository.constant.ServerStatusConstant;
import io.openjob.server.repository.repository.ServerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
public class ClusterFactoryBean implements DisposableBean {
    private final ServerRepository serverRepository;

    @Autowired
    public ClusterFactoryBean(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void destroy() {
        Node currentNode = ClusterStatus.getCurrentNode();
        serverRepository.update(currentNode.getServerId(), ServerStatusConstant.FAIL.getStatus(), DateUtil.now());

        log.info("Cluster server shutdown {}", currentNode.getAkkaAddress());
    }
}
