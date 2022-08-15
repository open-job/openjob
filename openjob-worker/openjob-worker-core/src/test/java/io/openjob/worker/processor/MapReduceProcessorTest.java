package io.openjob.worker.processor;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.task.MrTaskTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MapReduceProcessorTest implements MapReduceProcessor {
    @Override
    public ProcessResult process(JobContext context) throws Exception {
        if (context.isRoot()) {
            List<MrTaskTest> tasks = new ArrayList<>();
            for (int i = 1; i < 201; i++) {
                System.out.println("root"+i);
                tasks.add(new MrTaskTest(i));
            }

            return this.map(tasks, "TASK_TWO");
        }

        if (context.isTask("TASK_TWO")) {
            MrTaskTest task = (MrTaskTest) context.getTask();
            System.out.println("two"+task.getId());
            return new ProcessResult(true, String.valueOf(task.getId() * 2));
        }
        return new ProcessResult(false);
    }

    @Override
    public ProcessResult reduce(JobContext jobContext) {
        return null;
    }
}
