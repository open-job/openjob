package io.openjob.common.actor;

import akka.actor.AbstractActor;
import io.openjob.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public abstract class BaseActor extends AbstractActor {
    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive, Object msg) {
        try {
            super.aroundReceive(receive, msg);
        } catch (Throwable ex) {
            log.error("BaseActor failed. match=" + msg.toString(), ex);
            getSender().tell(Result.fail(ex.getMessage()), getSelf());
        }
    }
}
