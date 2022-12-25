package io.openjob.server.common.util;

import io.openjob.server.common.ClusterContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SlotsUtil {

    /**
     * Get slots id
     *
     * @param key key
     * @return Long
     */
    public static Long getSlotsId(String key) {
        return (long) (CrcUtil.crc16(key.getBytes()) % ClusterContext.getSystem().getMaxSlot());
    }

    /**
     * Get slots id
     *
     * @param key key
     * @return Long
     */
    public static Long getWorkerSupervisorSlotsId(String key) {
        return (long) (CrcUtil.crc16(key.getBytes()) % ClusterContext.getSystem().getWorkerSupervisorSlot());
    }
}
