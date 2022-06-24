package io.openjob.server.scheduler.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TimingWheel {

    /**
     * second.
     */
    private Long tickTime;

    private Integer wheelSize;

    private Long interval;

    /**
     * second.
     */
    private Long startTime;

    private AtomicInteger taskCounter;

    private DelayQueue<TimerTaskList> delayQueue;

    /**
     * second.
     */
    private Long currentTime;

    private volatile TimingWheel overflowWheel;

    private TimerTaskList[] buckets;

    public TimingWheel(Long tickTime, Integer wheelSize, Long startTime, AtomicInteger taskCounter,
                       DelayQueue<TimerTaskList> delayQueue) {
        this.tickTime = tickTime;
        this.wheelSize = wheelSize;
        this.startTime = startTime;
        this.taskCounter = taskCounter;
        this.delayQueue = delayQueue;
        this.interval = tickTime * wheelSize;
        this.currentTime = tickTime - (startTime % tickTime);

        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < buckets.length; i++) {
            this.buckets[i] = new TimerTaskList(taskCounter);
        }
    }
}
