package io.openjob.server.cluster.service;

import io.openjob.server.cluster.manager.JoinManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Service
public class JoinService {
    private final JoinManager joinManager;

    @Autowired
    public JoinService(JoinManager joinManager) {
        this.joinManager = joinManager;
    }

    /**
     * Cluster node start.
     *
     * @param hostname hostname
     * @param port     port
     */
    public void join(String hostname, Integer port) {
        this.joinManager.join(hostname, port);
    }
}
