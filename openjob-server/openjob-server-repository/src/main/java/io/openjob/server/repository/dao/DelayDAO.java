package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Delay;
import scala.Long;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayDAO {
    Long save(Delay delay);

    Delay findByTopic(String topic);
}
