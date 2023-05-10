package io.openjob.worker.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class OpenjobConfigTest {
    @Test
    public void testGetConfig() {
        Assertions.assertEquals(OpenjobConfig.getConfig("openjob.worker.test"), "test");
        Assertions.assertEquals(OpenjobConfig.getString("openjob.worker.test"), "test");

        Assertions.assertEquals(OpenjobConfig.getInteger("openjob.worker.int.value"), 1);
        Assertions.assertEquals(OpenjobConfig.getLong("openjob.worker.long.value"), 1L);
    }
}
