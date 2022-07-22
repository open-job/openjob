package io.openjob.common.util;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class FutureUtil {
    private FutureUtil() {

    }

    public static Object ask(ActorRef ref, Object msg, Long seconds) throws Exception {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(ref, msg, timeout);
        return Await.result(future, timeout.duration());
    }

    public static Object ask(ActorSelection selection, Object msg, Long seconds) throws Exception {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(selection, msg, timeout);
        return Await.result(future, timeout.duration());
    }
}
