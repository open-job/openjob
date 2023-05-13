package io.openjob.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class IpUtilTest {
    @Test
    public void testGetLocalIp() {
        String localAddress = IpUtil.getLocalAddress();
        Assertions.assertNotNull(localAddress);

        String formatAddress = IpUtil.getFormatAddress();
        Assertions.assertNotNull(formatAddress);
    }
}
