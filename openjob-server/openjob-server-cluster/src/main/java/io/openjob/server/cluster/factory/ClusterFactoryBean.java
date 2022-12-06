package io.openjob.server.cluster.factory;

import io.openjob.server.common.ClusterContext;
import io.openjob.common.context.Node;
import io.openjob.server.cluster.manager.FailManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class ClusterFactoryBean implements DisposableBean {

    private final FailManager failManager;

    @Autowired
    public ClusterFactoryBean(FailManager failManager) {
        this.failManager = failManager;
    }

    @Override
    public void destroy() {
        Node currentNode = ClusterContext.getCurrentNode();
        this.failManager.fail(currentNode, true);

        log.info("Server shutdown {}", currentNode.getAkkaAddress());
    }
}
