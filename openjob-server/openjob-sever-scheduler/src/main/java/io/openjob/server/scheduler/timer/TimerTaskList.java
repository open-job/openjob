package io.openjob.server.scheduler.timer;

import io.openjob.common.util.DateUtil;

import javax.swing.tree.ExpandVetoException;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TimerTaskList implements Delayed {

    private AtomicInteger taskCounter;

    private TimerTaskEntry root;

    private AtomicLong expiration;

    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.root = new TimerTaskEntry(null, -1L);
        this.root.setNext(root);
        this.root.setPrev(root);
        this.expiration = new AtomicLong(-1L);
    }

    public void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while (!done) {
            timerTaskEntry.remove();

            synchronized (this) {
                synchronized (timerTaskEntry) {
                    if (Objects.nonNull(timerTaskEntry.getTimerTaskList())) {
                        TimerTaskEntry tail = root.getPrev();
                        timerTaskEntry.setNext(root);
                        timerTaskEntry.setPrev(tail);
                        timerTaskEntry.setTimerTaskList(this);
                        tail.setNext(timerTaskEntry);
                        root.setPrev(timerTaskEntry);
                        taskCounter.incrementAndGet();
                        done = true;
                    }
                }
            }
        }
    }

    public void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (this) {
            synchronized (timerTaskEntry) {
                if (timerTaskEntry.getTimerTaskList() == this) {
                    timerTaskEntry.getNext().setPrev(timerTaskEntry.getPrev());
                    timerTaskEntry.getPrev().setNext(timerTaskEntry.getNext());
                    timerTaskEntry.setNext(null);
                    timerTaskEntry.setPrev(null);
                    timerTaskEntry.setTimerTaskList(null);
                    taskCounter.decrementAndGet();
                }
            }
        }
    }

    public void flush(Function<TimerTaskEntry, Void> function) {
        synchronized (this) {
            TimerTaskEntry head = root.getNext();
            while (head != root) {
                this.remove(head);
                function.apply(head);
                head = root.getNext();
            }
            this.expiration.set(-1L);
        }
    }

    public void setExpiration(AtomicLong expiration) {
        this.expiration = expiration;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.getExpiration() - DateUtil.now(), TimeUnit.SECONDS);
    }

    public Long getExpiration() {
        return this.expiration.get();
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
