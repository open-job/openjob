package io.openjob.worker.master;

import akka.actor.ActorContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.request.MasterStartContainerRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class ShardingTaskMaster extends AbstractDistributeTaskMaster {
    public ShardingTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void doSubmit() {
        // Parse sharding
        Map<Long, String> shardingMap = Maps.newHashMap();
        String[] shardingParams = StringUtils.split(this.jobInstanceDTO.getJobParams(), "&");
        Arrays.stream(shardingParams).forEach(s -> {
            String[] params = StringUtils.split(s, "=");
            shardingMap.put(Long.valueOf(params[0]), params[1]);
        });

        // Second delay to persist circle task
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.persistCircleTask();
        }

        // Dispatch tasks
        int shardingNum = shardingMap.size();
        List<MasterStartContainerRequest> startContainerRequestList = Lists.newArrayList();
        shardingMap.forEach((i, p) -> {
            MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();
            startRequest.setShardingId(i);
            startRequest.setShardingParam(p);
            startRequest.setShardingNum(shardingNum);
            startContainerRequestList.add(startRequest);
        });
        this.dispatchTasks(startContainerRequestList, false, Collections.emptySet());

        // Add task manager
        this.addTask2Manager();
    }

    @Override
    public void stop(Integer type) {
        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Stop master
        super.stop(type);
    }

    @Override
    public void destroyTaskContainer() {
        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Destroy task container
        super.destroyTaskContainer();
    }
}
