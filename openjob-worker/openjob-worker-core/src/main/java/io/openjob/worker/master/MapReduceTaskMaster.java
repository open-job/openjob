package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.google.common.collect.Lists;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.task.MapReduceTaskConsumer;
import io.openjob.worker.task.TaskQueue;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MapReduceTaskMaster extends BaseTaskMaster {

    /**
     * Child tasks.
     */
    protected TaskQueue<MasterStartContainerRequest> childTaskQueue;
    protected MapReduceTaskConsumer<MasterStartContainerRequest> childTaskConsumer;

    public MapReduceTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);

        childTaskQueue = new TaskQueue<>(this.jobInstanceDTO.getJobInstanceId(), 10240);
        childTaskConsumer = new MapReduceTaskConsumer<>(
                this.jobInstanceDTO.getJobInstanceId(),
                1,
                1,
                "Openjob-mapreduce-consumer",
                100,
                "Openjob-mapreduce-consumer-poll",
                childTaskQueue
        );

        childTaskConsumer.start();
    }

    @Override
    protected void init() {

    }

    public void map(List<Object> tasks, String taskName) {

    }

    @Override
    public void submit() {
        String workerAddress = this.jobInstanceDTO.getWorkerAddresses().get(0);
        ActorSelection workerSelection = OpenjobWorker.getActorSystem().actorSelection(workerAddress);
        MasterStartContainerRequest masterStartContainerRequest = this.wrapMasterStartContainerRequest();
        ArrayList<MasterStartContainerRequest> startRequests = Lists.newArrayList(masterStartContainerRequest);
        MasterBatchStartContainerRequest batchRequest = new MasterBatchStartContainerRequest();
        batchRequest.setJobId(this.jobInstanceDTO.getJobId());
        batchRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        batchRequest.setStartContainerRequests(startRequests);

        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(workerSelection, batchRequest, timeout);
        future.onComplete(new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable throwable, Object msg) {
                if (throwable != null) {
                    System.out.println("some thing wrong.{}" + throwable);
                } else {
                    System.out.println("success:" + msg);
                }
            }
        }, this.actorContext.dispatcher());
    }

    @Override
    public void stop() {

    }
}
