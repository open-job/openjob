package io.openjob.worker.delay;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayStarter {
    public static final DelayStarter INSTANCE = new DelayStarter();

    private final DelayTaskMaster delayTaskMaster;

    private DelayStarter() {
        this.delayTaskMaster = new DelayTaskMaster();
    }


    public void init() {
        this.delayTaskMaster.init();
    }

    public void stop() {
        this.delayTaskMaster.stop();
    }
}
