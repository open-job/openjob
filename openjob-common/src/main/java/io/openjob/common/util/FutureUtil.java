package io.openjob.common.util;

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
     * @param selection selection
     * @param msg       msg
     * @param seconds   seconds
     * @return Object
     * @throws Exception Exception
     */
    public static Object ask(ActorSelection selection, Object msg, Long seconds) throws Exception {
        Timeout timeout = new Timeout(Duration.create(seconds, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(selection, msg, timeout);
        return Await.result(future, timeout.duration());
    }


    /**
     * @param selection   selection
     * @param request     request
     * @param ignoredType ignored type
     * @param ms          milliseconds
     * @param <T>         ignoredType
     * @return ignoredType
     */
    public static <T> T mustAsk(ActorSelection selection, Object request, Class<T> ignoredType, Long ms) {
        Timeout timeout = new Timeout(Duration.create(ms, TimeUnit.MILLISECONDS));
        Future<Object> future = Patterns.ask(selection, request, timeout);

        String path = selection.pathString();
        String anchor = selection.anchorPath().toString();
        try {
            @SuppressWarnings("unchecked")
            Result<T> result = (Result<T>) Await.result(future, timeout.duration());

            if (!ResultUtil.isSuccess(result)) {
                throw new RuntimeException(String.format("Must ask result fail! message=%s path=%s anchor=%s", result.getMessage(), path, anchor));
            }

            return result.getData();
        } catch (Throwable ex) {
            throw new RuntimeException(String.format("Must ask fail! path=%s anchor=%s", path, anchor), ex);
        }
    }
}
