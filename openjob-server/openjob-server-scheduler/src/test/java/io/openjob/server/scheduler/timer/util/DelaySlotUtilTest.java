package io.openjob.server.scheduler.timer.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

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

    @Test
    public void testGetAddListSlotId() {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setDelayAddListSlot(6);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long addListSlotId = DelaySlotUtil.getAddListSlotId(UUID.randomUUID().toString());
            Assertions.assertTrue(addListSlotId >= 0);
            Assertions.assertTrue(addListSlotId <= 6);
        }
    }

    @Test
    public void testGetStatusListSlotId() {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setDelayStatusListSlot(6);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long addListSlotId = DelaySlotUtil.getStatusListSlotId(UUID.randomUUID().toString());
            Assertions.assertTrue(addListSlotId >= 0);
            Assertions.assertTrue(addListSlotId <= 6);
        }
    }

    @Test
    public void testGetZsetSlotId() {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setDelayZsetSlot(6);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long addListSlotId = DelaySlotUtil.getZsetSlotId(UUID.randomUUID().toString());
            Assertions.assertTrue(addListSlotId >= 0);
            Assertions.assertTrue(addListSlotId <= 6);
        }
    }

    @Test
    public void testGetFailZsetSlotId() {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setDelayFailZsetSlot(6);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long addListSlotId = DelaySlotUtil.getFailZsetSlotId(UUID.randomUUID().toString());
            Assertions.assertTrue(addListSlotId >= 0);
            Assertions.assertTrue(addListSlotId <= 6);
        }
    }

}
