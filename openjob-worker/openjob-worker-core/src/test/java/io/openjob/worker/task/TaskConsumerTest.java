package io.openjob.worker.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class TaskConsumerTest {
    private MyTestTaskConsumer<MyTestTask> handler;
    private TaskQueue<MyTestTask> queues;

    public void before() {
        queues = new TaskQueue<>(1L, 256);
        handler = new MyTestTaskConsumer<>(
                1L,
                2,
                2,
                "handler",
                10,
                "poll",
                queues
        );

        handler.start();
    }

    public void after() {
        handler.stop();
    }


    @Test
    public void testHandle() throws InterruptedException {
        this.before();

        int max = (new Random()).nextInt(1000) + 1000;
        for (int i = 0; i < max; i++) {
            queues.submit(new MyTestTask("task" + i));
        }

        Thread.sleep(3000L);
        Assertions.assertEquals(handler.getCounter().get(), max);

        this.after();
    }

    @Data
    @AllArgsConstructor
    public static class MyTestTask {
        private String name;
    }
}
