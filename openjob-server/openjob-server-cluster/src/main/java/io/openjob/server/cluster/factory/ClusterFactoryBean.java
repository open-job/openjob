package io.openjob.server.cluster.factory;

import io.openjob.server.cluster.service.StopService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ClusterFactoryBean implements DisposableBean {

    private final StopService stopService;

    @Autowired
    public ClusterFactoryBean(StopService stopService) {
        this.stopService = stopService;
    }

    @Override
    public void destroy() {
        this.stopService.stop();
    }
}
