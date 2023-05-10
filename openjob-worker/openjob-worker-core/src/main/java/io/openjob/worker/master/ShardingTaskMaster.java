package io.openjob.worker.master;

import akka.actor.ActorContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
    public void submit() {
        Map<Long, String> shardingMap = Maps.newHashMap();
        String[] shardingParams = StringUtils.split(this.jobInstanceDTO.getJobParams(), "&");
        Arrays.stream(shardingParams).forEach(s -> {
            String[] params = StringUtils.split(s, "=");
            shardingMap.put(Long.valueOf(params[0]), params[1]);
        });

        int shardingNum = shardingMap.size();
        List<MasterStartContainerRequest> startContainerRequestList = Lists.newArrayList();
        for (Map.Entry<Long, String> entry : shardingMap.entrySet()) {
            MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();
            startRequest.setShardingId(entry.getKey());
            startRequest.setShardingParam(entry.getValue());
            startRequest.setShardingNum(shardingNum);
            startContainerRequestList.add(startRequest);
        }

        this.dispatchTasks(startContainerRequestList, false, Collections.emptySet());
    }
}
