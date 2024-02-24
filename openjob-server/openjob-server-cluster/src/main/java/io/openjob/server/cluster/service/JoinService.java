package io.openjob.server.cluster.service;

import io.openjob.server.cluster.common.JoinCommon;
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
    private final JoinCommon joinCommon;

    @Autowired
    public JoinService(JoinCommon joinCommon) {
        this.joinCommon = joinCommon;
    }

    /**
     * Cluster node start.
     *
     * @param hostname hostname
     * @param port     port
     */
    public void join(String hostname, Integer port) {
        this.joinCommon.join(hostname, port);
    }
}
