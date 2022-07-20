package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.Worker;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface WorkerDAO {

    Long save(Worker worker);

    Worker getByAddress(String address);

    List<Worker> listOnlineWorkersByAppName(String appName);
}
