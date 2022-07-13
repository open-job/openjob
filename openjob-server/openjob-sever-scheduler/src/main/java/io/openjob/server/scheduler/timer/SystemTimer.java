package io.openjob.server.scheduler.timer;

import io.openjob.common.util.DateUtil;
import io.openjob.server.scheduler.constant.TimerConstant;
import io.openjob.server.scheduler.contract.Timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SystemTimer implements Timer {
    private final ExecutorService taskExecutor;
    private final DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
    private final AtomicInteger taskCounter = new AtomicInteger(0);

    private final TimingWheel timingWheel;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    /**
     * System timer.
     *
     * @param executorName executorName
     */
    public SystemTimer(String executorName) {
        taskExecutor = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(Integer.MAX_VALUE), r -> new Thread(r, executorName));

        timingWheel = new TimingWheel(TimerConstant.TICK_TIME, TimerConstant.WHEEL_SIZE, DateUtil.instant().getEpochSecond(),
                taskCounter, delayQueue);
    }

    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try {
            this.addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.getExecuteTime()));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Boolean advanceClock(Long timeout) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (Objects.nonNull(bucket)) {
                writeLock.lock();
                try {
                    while (Objects.nonNull(bucket)) {
                        timingWheel.advanceClock(bucket.getExpiration());
                        bucket.flush(e -> {
                            this.addTimerTaskEntry(e);
                            return null;
                        });

                        bucket = delayQueue.poll();
                        if (bucket != null) {
                            System.out.println(bucket.getExpiration());
                        }
                    }
                } finally {
                    writeLock.unlock();
                }

                return true;
            }
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Remove by taskId.
     *
     * @param taskId taskId
     */
    public void removeByTaskId(Long taskId) {
        timingWheel.removeByTaskId(taskId);
    }

    /**
     * Remove by slots' id.
     *
     * @param slotsId slotsId
     */
    public void removeBySlotsId(Long slotsId) {
        timingWheel.removeBySlotsId(slotsId);
    }

    @Override
    public Integer size() {
        return taskCounter.get();
    }

    @Override
    public void shuntDown() {
        taskExecutor.shutdown();
    }

    /**
     * Add timer task entry.
     *
     * @param timerTaskEntry timerTaskEntry
     */
    public void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        if (!timingWheel.add(timerTaskEntry)) {
            if (!timerTaskEntry.canceled()) {
                Date date = new Date();
                String strDateFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                System.out.println(sdf.format(date));
                taskExecutor.submit(timerTaskEntry.getTimerTask());
            }
        }
    }
}
