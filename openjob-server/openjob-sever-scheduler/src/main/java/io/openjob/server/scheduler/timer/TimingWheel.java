package io.openjob.server.scheduler.timer;

import java.util.Objects;
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

    /**
     * overflowWheel can potentially be updated and read by two concurrent threads through add().
     * Therefore, it needs to be volatile due to Double-Checked Locking pattern with JVM
     */
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
        this.currentTime = startTime - (startTime % tickTime);

        this.buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < buckets.length; i++) {
            this.buckets[i] = new TimerTaskList(taskCounter);
        }
    }

    public Boolean add(TimerTaskEntry timerTaskEntry) {
        long expiration = timerTaskEntry.getExpiration();
        if (timerTaskEntry.canceled()) {
            return false;
        }

        if (expiration < currentTime + tickTime) {
            return false;
        }

        if (expiration < currentTime + interval) {
            long virtualId = expiration / tickTime;
            int index = (int) (virtualId % wheelSize);
            TimerTaskList bucket = buckets[index];
            System.out.println(String.format("%d-%d-%d", expiration, virtualId, index));
            bucket.add(timerTaskEntry);

            if (bucket.setExpiration(virtualId * tickTime)) {
                delayQueue.offer(bucket);
            }

            return true;
        }

        if (Objects.isNull(overflowWheel)) {
            addOverflowWheel();
        }

        return overflowWheel.add(timerTaskEntry);
    }

    public void advanceClock(Long time) {
        if (time >= currentTime + tickTime) {
            currentTime = time - (time % tickTime);

            if (Objects.nonNull(overflowWheel)) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }

    private void addOverflowWheel() {
        synchronized (this) {
            if (Objects.isNull(overflowWheel)) {
                overflowWheel = new TimingWheel(this.interval, this.wheelSize, this.currentTime, this.taskCounter, this.delayQueue);
            }
        }
    }

}
