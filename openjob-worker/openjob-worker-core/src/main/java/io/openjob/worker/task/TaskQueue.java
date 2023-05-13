package io.openjob.worker.task;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class TaskQueue<T> {
    private final Long id;
    private final Integer capacity;
    private final BlockingQueue<T> queues;

    /**
     * Task queue.
     *
     * @param id       id
     * @param capacity capacity
     */
    public TaskQueue(Long id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
        this.queues = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * Submit
     *
     * @param task task
     * @throws InterruptedException InterruptedException
     */
    public void submit(T task) throws InterruptedException {
        assert task != null;
        queues.put(task);
    }

    /**
     * Poll
     *
     * @param size size
     * @return List
     */
    public List<T> poll(Integer size) {
        List<T> list = Lists.newLinkedList();
        for (int i = 0; i < size; i++) {
            T task = queues.poll();
            if (Objects.isNull(task)) {
                break;
            }

            list.add(task);
        }
        return list;
    }

    /**
     * Clear
     */
    public void clear() {
        queues.clear();
    }

    /**
     * Size
     *
     * @return Integer
     */
    public Integer size() {
        return queues.size();
    }

    /**
     * Get id.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Get capacity.
     *
     * @return Integer
     */
    public Integer getCapacity() {
        return capacity;
    }
}
