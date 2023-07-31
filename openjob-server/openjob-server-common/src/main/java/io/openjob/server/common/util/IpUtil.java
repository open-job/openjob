package io.openjob.server.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public class IpUtil {

    /**
     * default
     */
    private static final String LOCAL = "127.0.0.1";

    /**
     * unknown
     */
    private static final String UNKNOWN = "unknown";

    private IpUtil() {

    }

    /**
     * Get request ip
     *
     * @return ip
     */
    public static String getRequestIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return LOCAL;
        }
        HttpServletRequest webRequest = servletRequestAttributes.getRequest();
        String ip = webRequest.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = webRequest.getHeader("remoteip");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = webRequest.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = webRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = webRequest.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = webRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = LOCAL;
        }
        ip = ip.contains(",") ? ip.split(",")[0].trim() : ip;
        return ip;
    }
}
