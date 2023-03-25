package io.openjob.worker.delay;

import io.openjob.common.request.WorkerDelayTopicPullRequest;
import io.openjob.common.response.ServerDelayTopicPullResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.entity.Delay;
import io.openjob.worker.util.WorkerUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskMaster {
    private ExecutorService executorService;

    /**
     * Init
     */
    public void init() {
        executorService = new ThreadPoolExecutor(
                1,
                1,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1),
                r -> new Thread(r, "Openjob-delay-master")
        );

        this.executorService.submit(new DelayTaskMasterExecutor());
    }

    /**
     * Refresh delay task master.
     */
    public void refresh() {
        String appName = OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME);
        WorkerDelayTopicPullRequest pullRequest = new WorkerDelayTopicPullRequest();
        pullRequest.setAppName(appName);

        ServerDelayTopicPullResponse response = FutureUtil.mustAsk(WorkerUtil.getServerDelayInstanceActor(), pullRequest, ServerDelayTopicPullResponse.class, 3000L);
        Long timestamp = DateUtil.timestamp();

        // Delay topic
        List<Delay> saveTopicList = response.getTopicList().stream().map(t -> {
            Delay delay = new Delay();
            delay.setId(t.getId());
            delay.setTopic(t.getTopic());
            delay.setCreateTime(timestamp);
            delay.setUpdateTime(timestamp);
            delay.setPullSize(t.getConcurrency());
            delay.setPullTime(timestamp);
            return delay;
        }).collect(Collectors.toList());

        DelayDAO.INSTANCE.batchSave(saveTopicList);
    }

    /**
     * Stop
     */
    public void stop() {
        this.executorService.shutdown();
    }
}
