package io.openjob.server.scheduler.timer;

import com.google.common.collect.Maps;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin swoft@qq.com
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

    private volatile DelayQueue<TimerTaskList> delayQueue;

    /**
     * second.
     */
    private Long currentTime;

    /**
     * overflowWheel can potentially be updated and read by two concurrent threads through add().
     * Therefore, it needs to be volatile due to Double-Checked Locking pattern with JVM
     */
    private TimingWheel overflowWheel;

    private TimerTaskList[] buckets;

    private Map<Long, TimerTaskEntry> taskEntryMap = Maps.newConcurrentMap();

    private Map<Long, Set<Long>> slotsToTaskMap = Maps.newConcurrentMap();


    /**
     * Timing wheel.
     *
     * @param tickTime    tickTime
     * @param wheelSize   wheelSize
     * @param startTime   startTime
     * @param taskCounter taskCounter
     * @param delayQueue  delayQueue
     */
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
            this.buckets[i] = new TimerTaskList(this, taskCounter);
        }
    }

    /**
     * Add timer task entry.
     *
     * @param timerTaskEntry timerTaskEntry
     * @return Boolean
     */
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
            timerTaskEntry.setCurrentBucket(index);
            bucket.add(timerTaskEntry);

            Long taskId = timerTaskEntry.getTimerTask().getTaskId();
            Long slotsId = timerTaskEntry.getTimerTask().getSlotsId();
            this.taskEntryMap.put(taskId, timerTaskEntry);
            Set<Long> defaultSet = new HashSet<>();
            this.slotsToTaskMap.getOrDefault(slotsId, defaultSet).add(taskId);
            this.slotsToTaskMap.putIfAbsent(slotsId, defaultSet);

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

    /**
     * Remove by task id.
     *
     * @param taskId task id.
     */
    public void removeByTaskId(Long taskId) {
        TimerTaskEntry timerTaskEntry = taskEntryMap.remove(taskId);
        if (Objects.nonNull(timerTaskEntry)) {
            // Remove slots.
            Set<Long> slotsIds = this.slotsToTaskMap.remove(timerTaskEntry.getTimerTask().getSlotsId());
            Optional.ofNullable(slotsIds).ifPresent(s -> s.remove(taskId));

            TimerTaskList bucket = buckets[timerTaskEntry.getCurrentBucket()];
            bucket.remove(timerTaskEntry);
            return;
        }

        if (Objects.nonNull(overflowWheel)) {
            overflowWheel.removeByTaskId(taskId);
        }
    }

    /**
     * Remove by slots' id.
     *
     * @param slotsId slotsId
     */
    public void removeBySlotsId(Long slotsId) {
        Optional.ofNullable(this.slotsToTaskMap.remove(slotsId))
                .ifPresent(s -> s.forEach(this::removeByTaskId));


        if (Objects.nonNull(overflowWheel)) {
            this.overflowWheel.removeBySlotsId(slotsId);
        }
    }

    /**
     * Advance clock by time.
     *
     * @param time time
     */
    public void advanceClock(Long time) {
        if (time >= currentTime + tickTime) {
            currentTime = time - (time % tickTime);

            if (Objects.nonNull(overflowWheel)) {
                overflowWheel.advanceClock(currentTime);
            }
        }
    }

    /**
     * Remove entry map by taskId.
     *
     * @param taskId taskId
     */
    public void removeFromEntryMap(Long taskId) {
        this.taskEntryMap.remove(taskId);
    }

    /**
     * Add overflow wheel.
     */
    private void addOverflowWheel() {
        synchronized (this) {
            if (Objects.isNull(overflowWheel)) {
                overflowWheel = new TimingWheel(this.interval, this.wheelSize, this.currentTime, this.taskCounter, this.delayQueue);
            }
        }
    }

}
