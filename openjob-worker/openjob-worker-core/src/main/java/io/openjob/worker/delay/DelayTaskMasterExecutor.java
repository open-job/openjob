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
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

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

    private final DelayDAO delayDAO;

    public DelayTaskMasterExecutor() {
        this.delayDAO = new DelayDAO();
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

    private void start() {
        // Pull topics.
        Set<Long> pullTopicIds = new HashSet<>();

        // Pull topic items.
        List<WorkerDelayPullItemRequest> pullTopicItems = Lists.newArrayList();

        // Not pull topics.
        Set<Long> notIds = this.delayDAO.findNotPullList()
                .stream().map(Delay::getId)
                .collect(Collectors.toSet());

        // All topic.
        DelayTaskContainerPool.getAllDelayTaskContainer().forEach((t, c) -> {
            int size = c.pullSize();
            if (!notIds.contains(c.getId()) && size > 0) {
                pullTopicIds.add(c.getId());
                pullTopicItems.add(new WorkerDelayPullItemRequest(c.getTopic(), size));
            }
        });

        WorkerDelayPullRequest delayPullRequest = new WorkerDelayPullRequest();
        delayPullRequest.setPullItems(pullTopicItems);

        try {
            ActorSelection instanceActor = WorkerUtil.getServerDelayInstanceActor();
            ServerDelayPullResponse delayPullResponse = FutureUtil.mustAsk(instanceActor, delayPullRequest, ServerDelayPullResponse.class, 3L);

            //  All topic empty.
            if (CollectionUtils.isEmpty(delayPullResponse.getDelayInstanceResponses())) {
                Thread.sleep(1000L);
            }

            Map<String, List<ServerDelayInstanceResponse>> topicMap = delayPullResponse.getDelayInstanceResponses().stream()
                    .collect(Collectors.groupingBy(ServerDelayInstanceResponse::getTopic));

            Set<Long> responseTopicIds = delayPullResponse.getDelayInstanceResponses().stream()
                    .collect(Collectors.groupingBy(ServerDelayInstanceResponse::getDelayId)).keySet();
            Set<Long> emptyTopicIds = new HashSet<>(pullTopicIds);
            emptyTopicIds.removeAll(responseTopicIds);
            if (!CollectionUtils.isEmpty(emptyTopicIds)) {
                this.updatePullTime(emptyTopicIds);
            }

            // Execute delay task.
            this.execute(topicMap);

        } catch (Throwable e) {
            log.error("DelayTaskMasterExecutor must ask failed!", e);
        }
    }

    private void updatePullTime(Set<Long> topicIds) {
        int pullTime = DateUtil.now() + 3;
        List<Delay> delayList = Lists.newArrayList();
        topicIds.forEach(id -> {
            Delay delay = new Delay();
            delay.setId(id);
            delay.setPullTime(pullTime);
            delayList.add(delay);
        });

        this.delayDAO.batchUpdatePullTimeById(delayList);
    }

    private void execute(Map<String, List<ServerDelayInstanceResponse>> topicMap) {
        topicMap.forEach((t, instanceResponses) -> instanceResponses.forEach(i -> {
            DelayTaskContainer delayTaskContainer = DelayTaskContainerPool.get(i.getTopic(), topic -> new DelayTaskContainer(i.getDelayId(), topic, i.getConcurrency()));
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
            delayTaskContainer.execute(delayInstanceDTO);
        }));
    }
}
