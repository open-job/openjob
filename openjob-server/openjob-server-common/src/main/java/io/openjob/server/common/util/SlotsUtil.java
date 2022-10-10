package io.openjob.server.common.util;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SlotsUtil {
    /**
     * Max slots.
     */
    private static final Integer MAX_SLOTS = 18364;

    /**
     * Get slots id
     *
     * @param key key
     * @return Integer
     */
    public static Long getSlotsId(String key) {
        return (long) (CrcUtil.crc16(key.getBytes()) % MAX_SLOTS);
    }
}
