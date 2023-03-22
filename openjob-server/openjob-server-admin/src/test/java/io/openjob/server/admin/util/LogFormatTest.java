package io.openjob.server.admin.util;

import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class LogFormatTest {
    @Test
    public void testFormatLocation() {
        String location = "io.openjob.worker.samples.processor.JavaProcessorSample.process(JavaProcessorSample.java:25)";
        System.out.println(LogFormatUtil.formatLocation(location));
    }
}
