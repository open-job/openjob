package io.openjob.server.scheduler.timer.util;

import io.openjob.server.scheduler.util.DelaySlotUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelaySlotUtilTest {

    @Test
    public void testGetCurrentSlots() {
        List<Long> slots = DelaySlotUtil.getCurrentSlots(256, 3);
        Assertions.assertEquals(slots.size(), 3);

        List<Long> slots2 = DelaySlotUtil.getCurrentSlots(256, 6);
        for (int i = 1; i < 7; i++) {
            Assertions.assertTrue(slots2.contains((long) i));
        }
    }
}
