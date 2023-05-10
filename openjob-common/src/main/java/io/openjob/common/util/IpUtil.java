package io.openjob.common.util;

import org.apache.commons.lang3.StringUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class IpUtil {
    private IpUtil() {

    }

    /**
     * Get format address.
     *
     * @return String
     */
    public static String getFormatAddress() {
        String ipv4 = getLocalAddress();
        if (Objects.isNull(ipv4)) {
            return String.valueOf(System.currentTimeMillis());
        }

        StringBuilder ipv4Builder = new StringBuilder();
        String[] ipTokens = ipv4.split("\\.");
        for (String token : ipTokens) {
            ipv4Builder.append(StringUtils.leftPad(token, 3, "0"));
        }

        return ipv4Builder.toString();
    }

    /**
     * Get local ip address.
     *
     * @return String
     */
    public static String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4Result = new ArrayList<>();
            ArrayList<String> ipv6Result = new ArrayList<>();

            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                Enumeration<InetAddress> en = networkInterface.getInetAddresses();

                while (en.hasMoreElements()) {
                    InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(normalizeAddress(address));
                        } else {
                            ipv4Result.add(normalizeAddress(address));
                        }
                    }
                }
            }

            if (!ipv4Result.isEmpty()) {
                Iterator<String> iterator = ipv4Result.iterator();

                String ip;
                do {
                    if (!iterator.hasNext()) {
                        return ipv4Result.get(0);
                    }

                    ip = iterator.next();
                } while (ip.startsWith("127.0"));

                return ip;
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            } else {
                InetAddress localHost = InetAddress.getLocalHost();
                return normalizeAddress(localHost);
            }
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Normalize address.
     *
     * @param localHost localHost
     * @return String
     */
    private static String normalizeAddress(InetAddress localHost) {
        return localHost instanceof Inet6Address ? "[" + localHost.getHostAddress() + "]" : localHost.getHostAddress();
    }
}
