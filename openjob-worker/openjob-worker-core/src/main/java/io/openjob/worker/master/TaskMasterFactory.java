package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.worker.dto.JobInstanceDTO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;

/**
 * @author stelin swoft@qq.com
 * @see StandaloneTaskMaster
 * @see MapReduceTaskMaster
 * @see BroadcastTaskMaster
 * @see ShardingTaskMaster
 * @since 1.0.0
 */
public class TaskMasterFactory {
    /**
     * Create
     * @param jobInstanceDTO jobInstanceDTO
     * @param actorContext actorContext
     * @return TaskMaster
     */
    public static TaskMaster create(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        try {
            String className = String.format("io.openjob.worker.master.%sTaskMaster", StringUtils.capitalize(jobInstanceDTO.getExecuteType()));
            @SuppressWarnings("unchecked")
            Constructor<? extends TaskMaster> constructor = ((Class<? extends TaskMaster>) Class.forName(className))
                    .getConstructor(JobInstanceDTO.class, ActorContext.class);
            return constructor.newInstance(jobInstanceDTO, actorContext);
        } catch (Throwable ex) {
            throw new RuntimeException("Task master is not exist! executeType=" + jobInstanceDTO.getExecuteType(), ex);
        }
    }
}
