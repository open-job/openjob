package io.openjob.server.log.dao.impl;

import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.h2.H2ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class H2LogDAOImpl implements LogDAO {
    private final H2ConnectionPool h2ConnectionPool;

    @Autowired
    public H2LogDAOImpl(H2ConnectionPool h2ConnectionPool) {
        this.h2ConnectionPool = h2ConnectionPool;
    }
}
