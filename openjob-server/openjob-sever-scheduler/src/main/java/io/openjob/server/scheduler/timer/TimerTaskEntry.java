package io.openjob.server.scheduler.timer;

import lombok.Getter;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
public class TimerTaskEntry implements Comparable<TimerTaskEntry> {
    private volatile TimerTaskList timerTaskList;
    private TimerTaskEntry next;
    private TimerTaskEntry prev;
    private final TimerTask timerTask;
    private final Long expiration;

    public TimerTaskEntry(TimerTask timerTask, Long expiration) {
        if (Objects.nonNull(timerTask)) {
            timerTask.setTimerTaskEntry(this);
        }

        this.timerTask = timerTask;
        this.expiration = expiration;
    }

    public Boolean canceled() {
        return timerTask.getTimerTaskEntry() != this;
    }

    public void remove() {
        TimerTaskList currentTaskList = timerTaskList;

        while (Objects.nonNull(currentTaskList)) {
            currentTaskList.remove(this);
            currentTaskList = timerTaskList;
        }
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        if (Objects.isNull(o)) {
            throw new NullPointerException("Compare TimerTaskEntry is null.");
        }

        return Long.compare(this.expiration, o.expiration);
    }

    public void setNext(TimerTaskEntry next) {
        this.next = next;
    }

    public void setPrev(TimerTaskEntry prev) {
        this.prev = prev;
    }

    public void setTimerTaskList(TimerTaskList timerTaskList) {
        this.timerTaskList = timerTaskList;
    }
}
