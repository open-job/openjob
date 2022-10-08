package io.openjob.worker.delay;

import com.google.common.collect.Lists;
import io.openjob.common.request.WorkerDelayPullItemRequest;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.entity.Delay;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskMasterExecutor implements Runnable {

    private final DelayDAO delayDAO;

    public DelayTaskMasterExecutor() {
        this.delayDAO = new DelayDAO();
    }

    @Override
    public void run() {
        List<WorkerDelayPullItemRequest> pullItems = Lists.newArrayList();

        Set<Long> notIds = this.delayDAO.findNotPullList()
                .stream().map(Delay::getId)
                .collect(Collectors.toSet());

        DelayTaskContainerPool.getAllDelayTaskContainer().forEach((t, c) -> {
            int size = c.pullSize();
            if (!notIds.contains(c.getId()) && size > 0) {
                pullItems.add(new WorkerDelayPullItemRequest(c.getTopic(), size));
            }
        });
    }
}
