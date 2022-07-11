package io.openjob.server.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class CRC16UtilTest {
    @Test
    public void testCRC16() {
        int v = CRCUtil.crc16("test".getBytes());
        Assertions.assertEquals(1928, v);
    }
}
