package io.openjob.server.scheduler.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.util.CrcUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
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
     * Get current add list slots.
     *
     * @return List
     */
    public static List<Long> getCurrentAddListSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayAddListMaxSlot();
        return getCurrentSlots(maxSlot, delayListMaxSlot);
    }

    /**
     * Get current status list slots.
     *
     * @return List
     */
    public static List<Long> getCurrentStatusListSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayStatusListMaxSlot();
        return getCurrentSlots(maxSlot, delayListMaxSlot);
    }

    /**
     * Get current delete list slots.
     *
     * @return List
     */
    public static List<Long> getCurrentDeleteListSlots() {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayDeleteListMaxSlot();
        return getCurrentSlots(maxSlot, delayListMaxSlot);
    }

    /**
     * Get add list slot id.
     *
     * @param key key
     * @return Long
     */
    public static Long getAddListSlotId(String key) {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayAddListMaxSlot();
        int index = CrcUtil.crc16(key.getBytes()) % delayListMaxSlot;

        List<Long> slots = getCurrentSlots(maxSlot, delayListMaxSlot);
        return slots.get(index);
    }

    /**
     * Get status list slot id.
     *
     * @param key key
     * @return Long
     */
    public static Long getStatusListSlotId(String key) {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayStatusListMaxSlot();
        int index = CrcUtil.crc16(key.getBytes()) % delayListMaxSlot;

        List<Long> slots = getCurrentSlots(maxSlot, delayListMaxSlot);
        return slots.get(index);
    }

    /**
     * Get delete list slot id.
     *
     * @param key key
     * @return Long
     */
    public static Long getDeleteListSlotId(String key) {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayListMaxSlot = ClusterContext.getSystem().getDelayDeleteListMaxSlot();
        int index = CrcUtil.crc16(key.getBytes()) % delayListMaxSlot;

        List<Long> slots = getCurrentSlots(maxSlot, delayListMaxSlot);
        return slots.get(index);
    }

    /**
     * Get zset slot id.
     *
     * @param key key
     * @return Long
     */
    public static Long getZsetSlotId(String key) {
        int maxSlot = ClusterContext.getSystem().getMaxSlot();
        int delayZsetMaxSlot = ClusterContext.getSystem().getDelayZsetMaxSlot();
        int index = CrcUtil.crc16(key.getBytes()) % delayZsetMaxSlot;

        List<Long> slots = getCurrentSlots(maxSlot, delayZsetMaxSlot);
        return slots.get(index);
    }

    /**
     * Get current slots.
     *
     * @param maxSlot     max slot
     * @param currentSlot current slot
     * @return List
     */
    public static List<Long> getCurrentSlots(Integer maxSlot, Integer currentSlot) {
        Set<Long> slots = new HashSet<>();
        if (currentSlot > maxSlot) {
            log.error("Current slot must less than max slot.");
            return new ArrayList<>(slots);
        }

        Set<Long> nodeSlots = ClusterContext.getCurrentSlots();
        for (int i = 1; i < currentSlot + 1; i++) {
            if (nodeSlots.contains((long) i)) {
                slots.add((long) i);
            }
        }
        return new ArrayList<>(slots);
    }
}
