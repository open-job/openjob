package io.openjob.common.util;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.Patterns;
import akka.util.Timeout;
import io.openjob.common.response.Result;
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

    /**
     *
     * @param ref
     * @param msg
     * @param seconds
     * @return
     * @throws Exception
     */
    public static Object ask(ActorRef ref, Object msg, Long seconds) throws Exception {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(ref, msg, timeout);
        return Await.result(future, timeout.duration());
    }

    /**
     *
     * @param selection
     * @param msg
     * @param seconds
     * @return
     * @throws Exception
     */
    public static Object ask(ActorSelection selection, Object msg, Long seconds) throws Exception {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(selection, msg, timeout);
        return Await.result(future, timeout.duration());
    }

    /**
     *
     * @param selection
     * @param request
     * @param ignoredType
     * @param seconds
     * @param <T>
     * @return
     */
    public static <T> T mustAsk(ActorSelection selection, Object request, Class<T> ignoredType, Long seconds) {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(selection, request, timeout);
        try {
            @SuppressWarnings("unchecked")
            Result<T> result = (Result<T>) Await.result(future, timeout.duration());

            if (!ResultUtil.isSuccess(result)) {
                throw new RuntimeException("Must ask fail! message=" + result.getMessage());
            }

            return result.getData();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
}
