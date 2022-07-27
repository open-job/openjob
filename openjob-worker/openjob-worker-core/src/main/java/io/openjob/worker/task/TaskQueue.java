package io.openjob.worker.task;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskQueue<T> {
    private Long id;
    private Integer capacity;
    private BlockingQueue<T> queues;

    public TaskQueue(Long id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
        this.queues = new LinkedBlockingQueue<>(capacity);
    }

    public void submit(T task) throws InterruptedException {
        assert task != null;
        queues.put(task);
    }

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

    public void clear() {
        queues.clear();
    }

    public Integer size() {
        return queues.size();
    }

    public Long getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
