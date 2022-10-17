package io.openjob.server.scheduler.timer;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkflowTimerTask extends TimerTask {

    /**
     * Timer task.
     *
     * @param taskId     taskId
     * @param slotsId    slotsId
     * @param expiration expiration
     */
    public WorkflowTimerTask(Long taskId, Long slotsId, Long expiration) {
        super(taskId, slotsId, expiration);
    }

    @Override
    public void run() {

    }
}
