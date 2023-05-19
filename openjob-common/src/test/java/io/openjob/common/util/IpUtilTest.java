package io.openjob.common.util;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    @Test
    public void testGetIpByHost() throws UnknownHostException {
        String ip = IpUtil.getIpByHost("localhost");
        Assertions.assertEquals(ip, "127.0.0.1");

        String ip2 = IpUtil.getIpByHost("127.0.0.1");
        Assertions.assertEquals(ip2, "127.0.0.1");

        String ip3 = IpUtil.getIpByHost("github.com");
        Assertions.assertNotNull(ip3);

        String ip4 = IpUtil.getIpByHost("20.205.243.166");
        Assertions.assertEquals(ip4, "20.205.243.166");

        String ip5 = IpUtil.getIpByHost("172.20.1.166");
        Assertions.assertEquals(ip5, "172.20.1.166");
    }
}
