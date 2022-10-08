package io.openjob.worker.delay;

import akka.actor.ActorSelection;
import com.google.common.collect.Lists;
import io.openjob.common.request.WorkerDelayPullItemRequest;
import io.openjob.common.request.WorkerDelayPullRequest;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayPullResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.dto.DelayInstanceDTO;
import io.openjob.worker.entity.Delay;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayTaskMasterExecutor implements Runnable {
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                this.start();
            } catch (Throwable e) {
                log.error("DelayTaskMasterExecutor start failed!", e);
            }
        }
    }

    private void start() {
        try {
            // Pull topic items.
            List<WorkerDelayPullItemRequest> pullTopicItems = Lists.newArrayList();
            DelayDAO.INSTANCE.findPullList()
                    .forEach(d -> {
                        int size = OpenjobConfig.getInteger(WorkerConstant.WORKER_DELAY_PULL_SIZE, WorkerConstant.DEFAULT_WORKER_DELAY_PULL_SIZE);
                        int pullSize = d.getPullSize() > size ? size : d.getPullSize();
                        pullTopicItems.add(new WorkerDelayPullItemRequest(d.getTopic(), pullSize));
                    });

            if (CollectionUtils.isEmpty(pullTopicItems)) {
                Thread.sleep(1000L);
                return;
            }

            WorkerDelayPullRequest delayPullRequest = new WorkerDelayPullRequest();
            delayPullRequest.setPullItems(pullTopicItems);

            ActorSelection instanceActor = WorkerUtil.getServerDelayInstanceActor();
            ServerDelayPullResponse delayPullResponse = FutureUtil.mustAsk(instanceActor, delayPullRequest, ServerDelayPullResponse.class, 3L);

            //  All topic empty.
            if (CollectionUtils.isEmpty(delayPullResponse.getDelayInstanceResponses())) {
                Thread.sleep(1000L);
                return;
            }

            Map<String, List<ServerDelayInstanceResponse>> topicMap = delayPullResponse.getDelayInstanceResponses().stream()
                    .collect(Collectors.groupingBy(ServerDelayInstanceResponse::getTopic));

            // Execute delay task.
            this.execute(topicMap);

        } catch (Throwable e) {
            log.error("DelayTaskMasterExecutor must ask failed!", e);
        }
    }

    private void execute(Map<String, List<ServerDelayInstanceResponse>> topicMap) {
        topicMap.forEach((t, instanceResponses) -> {
            ServerDelayInstanceResponse firstDelay = instanceResponses.get(0);
            DelayTaskContainer delayTaskContainer = DelayTaskContainerPool.get(firstDelay.getTopic(), topic -> {
                int now = DateUtil.now();
                Delay delay = new Delay();
                delay.setId(firstDelay.getDelayId());
                delay.setPullSize(10);
                delay.setTopic(topic);
                delay.setUpdateTime(now);
                delay.setCreateTime(now);
                DelayDAO.INSTANCE.batchSave(Collections.singletonList(delay));
                return new DelayTaskContainer(firstDelay.getDelayId(), topic, firstDelay.getConcurrency());
            });

            List<DelayInstanceDTO> instanceList = Lists.newArrayList();
            instanceResponses.forEach(i -> {
                DelayInstanceDTO delayInstanceDTO = new DelayInstanceDTO();
                delayInstanceDTO.setTopic(i.getTopic());
                delayInstanceDTO.setDelayId(i.getDelayId());
                delayInstanceDTO.setDelayParams(i.getDelayParams());
                delayInstanceDTO.setDelayExtra(i.getDelayExtra());
                delayInstanceDTO.setProcessorInfo(i.getProcessorInfo());
                delayInstanceDTO.setFailRetryInterval(i.getFailRetryInterval());
                delayInstanceDTO.setFailRetryTimes(i.getFailRetryTimes());
                delayInstanceDTO.setExecuteTimeout(i.getExecuteTimeout());
                delayInstanceDTO.setConcurrency(i.getConcurrency());
                instanceList.add(delayInstanceDTO);
            });
            delayTaskContainer.execute(instanceList);
        });
    }
}
