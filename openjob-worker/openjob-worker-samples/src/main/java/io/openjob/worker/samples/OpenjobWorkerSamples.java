package io.openjob.worker.samples;

import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.samples.processor.JavaProcessorSample;
import io.openjob.worker.samples.processor.DelayProcessorSample;
import io.openjob.worker.samples.processor.MapReduceProcessorSample;

/**
 * @author stelin <swoft@qq.com>
 * @see JavaProcessorSample
 * @see DelayProcessorSample
 * @see MapReduceProcessorSample
 * @since 1.0.0
 */
public class OpenjobWorkerSamples {

    /**
     * Main
     *
     * @param args args
     */
    public static void main(String[] args) {
        try {
            OpenjobWorker openjobWorker = new OpenjobWorker();
            openjobWorker.init();

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
