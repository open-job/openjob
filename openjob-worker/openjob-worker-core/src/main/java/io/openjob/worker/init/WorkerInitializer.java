package io.openjob.worker.init;

import io.openjob.worker.container.TaskContainerManager;
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
    private final TaskContainerManager taskContainerManager;

    public WorkerInitializer() {
        this.delayManager = new DelayManager();
        this.taskMasterManager = TaskMasterManager.INSTANCE;
        this.taskContainerManager = TaskContainerManager.INSTANCE;
    }

    /**
     * Init
     */
    public void init() {
        // Initialize task master.
        this.taskMasterManager.init();

        // Initialize task container.
        this.taskContainerManager.init();

        // Initialize delay.
        this.delayManager.init();
    }

    /**
     * Stop
     */
    public void stop() {
        this.taskMasterManager.stop();
        this.taskContainerManager.stop();
        this.delayManager.stop();
    }
}
