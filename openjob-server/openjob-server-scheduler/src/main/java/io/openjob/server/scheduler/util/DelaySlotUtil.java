package io.openjob.server.scheduler.util;

import io.openjob.server.common.ClusterContext;
import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelaySlotUtil {

    /**
     * Get current zset slots.
     *
     * @return List
     */
    public static List<Long> getCurrentZsetSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayZsetMaxSlot = ClusterContext.getSystem().getDelayZsetMaxSlot();
        return getCurrentSlots(maxSlot, delayZsetMaxSlot);
    }

    /**
     * Get current list slots.
     *
     * @return List
     */
    public static List<Long> getCurrentListSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayListMaxSlot();
        return getCurrentSlots(maxSlot, delayListMaxSlot);
    }

    /**
     * Get current slots.
     *
     * @param maxSlot     max slot
     * @param currentSlot current slot
     * @return List
     */
    private static List<Long> getCurrentSlots(Integer maxSlot, Integer currentSlot) {
        int step = maxSlot / currentSlot;

        Set<Long> slots = new HashSet<>();
        for (int i = 1; i < currentSlot + 1; i++) {
            slots.add((long) i * step);
        }

        slots.retainAll(ClusterContext.getCurrentSlots());
        return new ArrayList<>(slots);
    }
}
