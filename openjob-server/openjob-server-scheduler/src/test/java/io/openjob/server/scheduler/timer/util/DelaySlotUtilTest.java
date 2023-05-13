package io.openjob.server.scheduler.timer.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class DelaySlotUtilTest {

    @Test
    public void testGetCurrentSlots() {
        ClusterContext.refreshCurrentSlots(new HashSet<Long>() {{
            add(1L);
            add(3L);
            add(6L);
        }});
        List<Long> slots = DelaySlotUtil.getCurrentSlots(256, 3);
        Assertions.assertEquals(slots.size(), 2);

        List<Long> slots2 = DelaySlotUtil.getCurrentSlots(256, 6);
        Assertions.assertEquals(slots2.size(), 3);
        Assertions.assertTrue(slots2.contains(1L));
        Assertions.assertTrue(slots2.contains(3L));
        Assertions.assertTrue(slots2.contains(6L));
    }
}
