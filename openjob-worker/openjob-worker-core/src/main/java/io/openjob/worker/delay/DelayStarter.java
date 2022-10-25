package io.openjob.worker.delay;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayStarter {
    public static final DelayStarter INSTANCE = new DelayStarter();

    private final DelayTaskMaster delayTaskMaster;

    /**
     * Delay starter.
     */
    private DelayStarter() {
        this.delayTaskMaster = new DelayTaskMaster();
    }

    /**
     * Init
     */
    public void init() {
        this.delayTaskMaster.init();
    }

    /**
     * Stop
     */
    public void stop() {
        // Stop  delay master.
        this.delayTaskMaster.stop();

        // Stop task container.
        DelayTaskContainerPool.getAllDelayTaskContainer().forEach((t, c) -> {
            c.stop();
        });
    }
}
