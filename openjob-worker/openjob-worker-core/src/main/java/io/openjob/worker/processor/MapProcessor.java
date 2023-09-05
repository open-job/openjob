package io.openjob.worker.processor;

import akka.actor.ActorSelection;
import com.google.common.collect.Lists;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.KryoUtil;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.request.ProcessorMapTaskRequest;
import io.openjob.worker.util.ThreadLocalUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface MapProcessor extends JavaProcessor {

    /**
     * Map
     *
     * @param tasks    sub tasks.
     * @param taskName task name.
     * @return ProcessResult
     */
    default ProcessResult map(List<?> tasks, String taskName) {
        ProcessResult result = new ProcessResult(false);

        // Empty task
        if (CollectionUtils.isEmpty(tasks)) {
            return result;
        }

        JobContext jobContext = ThreadLocalUtil.getJobContext();
        ActorSelection masterSelection = WorkerActorSystem.getActorSystem().actorSelection(jobContext.getMasterActorPath());

        // Batch size
        int batchSize = OpenjobConfig.getInteger(WorkerConstant.WORKER_TASK_MAP_BATCH_SIZE, WorkerConstant.DEFAULT_WORKER_TASK_MAP_BATCH_SIZE);
        @SuppressWarnings("unchecked")
        List<List<Object>> splitTasks = Lists.partition((List<Object>) tasks, batchSize);

        AtomicInteger index = new AtomicInteger(1);
        AtomicInteger initValueId = new AtomicInteger(1);
        splitTasks.forEach((bt) -> {
            ProcessorMapTaskRequest mapTaskRequest = new ProcessorMapTaskRequest();
            mapTaskRequest.setJobId(jobContext.getJobId());
            mapTaskRequest.setJobInstanceId(jobContext.getJobInstanceId());
            mapTaskRequest.setTaskId(jobContext.getTaskId());
            mapTaskRequest.setTaskName(taskName);
            mapTaskRequest.setTaskNum(0);
            mapTaskRequest.setInitValueId(initValueId.get());

            // Last partition to set task num
            if (splitTasks.size() == index.get()) {
                mapTaskRequest.setTaskNum(tasks.size());
            }

            List<byte[]> byteList = new ArrayList<>();
            bt.forEach(t -> byteList.add(KryoUtil.serialize(t)));
            mapTaskRequest.setTasks(byteList);

            // Map task
            FutureUtil.mustAsk(masterSelection, mapTaskRequest, WorkerResponse.class, 5000L);
            result.setStatus(TaskStatusEnum.SUCCESS);
            index.getAndIncrement();
            initValueId.addAndGet(batchSize);
        });
        return result;
    }
}
