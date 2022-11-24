package io.openjob.server.scheduler.util;

import io.openjob.server.common.ClusterContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelaySlotUtil {
    public static List<Long> getCurrentSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayZsetMaxSlot = ClusterContext.getSystem().getDelayZsetMaxSlot();
        int step = maxSlot / delayZsetMaxSlot;

        Set<Long> slots = new HashSet<>();
        for (int i = 1; i < delayZsetMaxSlot + 1; i++) {
            slots.add((long) i * step);
        }

        slots.retainAll(ClusterContext.getCurrentSlots());
        return new ArrayList<>(slots);
    }
}
