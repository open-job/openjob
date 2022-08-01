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
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MapReduceTaskMaster extends BaseTaskMaster {
    public MapReduceTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
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
