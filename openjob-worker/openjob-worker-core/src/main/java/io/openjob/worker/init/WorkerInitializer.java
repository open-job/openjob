package io.openjob.worker.init;

import io.openjob.worker.delay.DelayManager;
import io.openjob.worker.master.TaskMasterManager;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Getter
public class WorkerInitializer {
    private final DelayManager delayManager;
    private final TaskMasterManager taskMasterManager;

    public WorkerInitializer() {
        this.delayManager = new DelayManager();
        this.taskMasterManager = TaskMasterManager.INSTANCE;
    }

    public void init() {
        // Initialize task master.
        this.taskMasterManager.init();

        // Initialize delay.
        this.delayManager.init();
    }

    public void stop() {
        this.taskMasterManager.stop();

        this.delayManager.stop();
    }
}
