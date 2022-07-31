package io.openjob.worker.task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskConsumerTest {
    private MyTestTaskConsumer<MyTestTask> handler;
    private TaskQueue<MyTestTask> queues;

    @BeforeEach
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

    @AfterEach
    public void after() {
        handler.stop();
    }


    @Test
    public void testHandle() throws InterruptedException {
        int max = (new Random()).nextInt(1000) + 1000;
        for (int i = 0; i < max; i++) {
            queues.submit(new MyTestTask("task" + i));
        }

        Thread.sleep(3000L);
        Assertions.assertEquals(handler.getCounter().get(), max);
    }
}
