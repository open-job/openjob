package io.openjob.worker.actor;

import akka.actor.ActorSelection;
import akka.persistence.AbstractPersistentActorWithAtLeastOnceDelivery;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
import io.openjob.common.response.WorkerResponse;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerPersistentActor extends AbstractPersistentActorWithAtLeastOnceDelivery {
    private final Integer id;

    public WorkerPersistentActor(Integer id) {
        this.id = id;
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .matchAny(System.out::println)
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ContainerBatchTaskStatusRequest.class, this::handleBatchTaskStatus)
                .match(Result.class, this::handleResult)
                .build();
    }

    public void handleBatchTaskStatus(ContainerBatchTaskStatusRequest batchRequest) {
        ActorSelection masterSelection = getContext().actorSelection(batchRequest.getMasterActorPath());
        deliver(masterSelection, deliveryId -> {
            batchRequest.setDeliveryId(deliveryId);
            return batchRequest;
        });
    }

    public void handleResult(Result<?> result) {
        if (Result.FAIL.equals(result.getStatus()) || Objects.isNull(result.getData())) {
            log.error("Handle result fail! message={}", result.getMessage());
            return;
        }

        if (result.getData() instanceof WorkerResponse) {
            WorkerResponse workerResponse = (WorkerResponse) result.getData();
            confirmDelivery(workerResponse.getDeliveryId());
            return;
        }

        if (result.getData() instanceof ServerResponse) {
            ServerResponse serverResponse = (ServerResponse) result.getData();
            confirmDelivery(serverResponse.getDeliveryId());
            return;
        }

        log.error("Handle result data not defined data={}", result.getData().toString());
    }

    @Override
    public String persistenceId() {
        return String.format("persistence-id-%d", id);
    }
}
