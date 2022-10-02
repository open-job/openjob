package io.openjob.worker.dao;

import io.openjob.worker.persistence.DelayPersistence;
import io.openjob.worker.persistence.H2DelayMemoryPersistence;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayDAO {
    public final static DelayDAO INSTANCE = new DelayDAO();
    private final DelayPersistence delayPersistence;

    private DelayDAO() {
        this.delayPersistence = new H2DelayMemoryPersistence();
    }
}
