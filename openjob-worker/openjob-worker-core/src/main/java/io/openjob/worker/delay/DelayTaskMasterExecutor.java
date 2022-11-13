package io.openjob.worker.delay;

import akka.actor.ActorSelection;
import com.google.common.collect.Lists;
import io.openjob.common.request.WorkerDelayPullItemRequest;
import io.openjob.common.request.WorkerDelayPullRequest;
import io.openjob.common.response.ServerDelayInstanceResponse;
import io.openjob.common.response.ServerDelayPullResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.dto.DelayInstanceDTO;
import io.openjob.worker.entity.Delay;
import io.openjob.worker.util.OpenjobConfigUtil;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayTaskMasterExecutor implements Runnable {
    private final Long pullSleep;
    private final Long pullStep;

    public DelayTaskMasterExecutor() {
        this.pullStep = OpenjobConfigUtil.getDelayPullStep();
        this.pullSleep = OpenjobConfigUtil.getDelayPullSleep();
    }

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

    private void start() throws InterruptedException {
        // Pull topic items.
        List<WorkerDelayPullItemRequest> pullTopicItems = Lists.newArrayList();
        Set<Long> pullTopicIds = new HashSet<>();

        // Find pull topic.
        DelayDAO.INSTANCE.findPullList()
                .forEach(d -> {
                    int size = OpenjobConfigUtil.getDelayPullSize();
                    int pullSize = d.getPullSize() > size ? size : d.getPullSize();
                    pullTopicItems.add(new WorkerDelayPullItemRequest(d.getTopic(), pullSize));
                    pullTopicIds.add(d.getId());
                });

        // Empty pull.
        if (CollectionUtils.isEmpty(pullTopicItems)) {
            Thread.sleep(this.pullSleep);
            return;
        }

        // Pull request.
        WorkerDelayPullRequest delayPullRequest = new WorkerDelayPullRequest();
        delayPullRequest.setPullItems(pullTopicItems);

        // Pull delay instance by akka.
        ActorSelection instanceActor = WorkerUtil.getServerDelayInstanceActor();
        ServerDelayPullResponse delayPullResponse = FutureUtil.mustAsk(instanceActor, delayPullRequest, ServerDelayPullResponse.class, 3000L);

        //  All topic empty.
        if (CollectionUtils.isEmpty(delayPullResponse.getDelayInstanceResponses())) {
            Thread.sleep(this.pullSleep);
            return;
        }

        // Group by topic.
        Map<Long, List<ServerDelayInstanceResponse>> topicIdsMap = delayPullResponse.getDelayInstanceResponses().stream()
                .collect(Collectors.groupingBy(ServerDelayInstanceResponse::getDelayId));

        // Update pull time.
        this.updatePullTime(pullTopicIds, topicIdsMap.keySet());

        // Execute delay task.
        this.execute(topicIdsMap);
    }

    private void updatePullTime(Set<Long> pullTopicIds, Set<Long> responseTopicIds) {
        int now = DateUtil.now();
        long nowMill = DateUtil.milliLongTime();
        List<Delay> updateDelays = Lists.newArrayList();
        pullTopicIds.forEach(id -> {
            Delay delay = new Delay();
            delay.setId(id);
            delay.setUpdateTime(now);

            // Next pull time.
            if (!responseTopicIds.contains(id)) {
                delay.setPullTime(nowMill + this.pullStep);
            } else {
                // Reset pull time.
                delay.setPullTime(0L);
            }
        });

        // Batch update pull time.
        if (!CollectionUtils.isEmpty(updateDelays)) {
            DelayDAO.INSTANCE.batchUpdatePullTime(updateDelays);
        }
    }

    private void execute(Map<Long, List<ServerDelayInstanceResponse>> topicIdMap) {
        topicIdMap.forEach((t, instanceResponses) -> {
            ServerDelayInstanceResponse firstDelay = instanceResponses.get(0);
            DelayTaskContainer delayTaskContainer = DelayTaskContainerPool.get(firstDelay.getDelayId(), id -> {
                int now = DateUtil.now();
                Delay delay = new Delay();
                delay.setId(id);
                delay.setPullSize(10);
                delay.setPullTime(0L);
                delay.setTopic(firstDelay.getTopic());
                delay.setUpdateTime(now);
                delay.setCreateTime(now);
                DelayDAO.INSTANCE.batchSave(Collections.singletonList(delay));
                return new DelayTaskContainer(firstDelay.getDelayId(), firstDelay.getBlockingSize(), firstDelay.getConcurrency());
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
