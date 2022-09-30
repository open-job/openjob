package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.entity.Delay;
import org.springframework.stereotype.Component;
import scala.Long;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayDAOImpl implements DelayDAO {
    @Override
    public Long save(Delay delay) {
        return null;
    }

    @Override
    public Delay findByTopic(String topic) {
        return null;
    }
}
