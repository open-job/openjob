package io.openjob.server.admin.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求访问拦截器
 * - 跨域设置
 *
 * @author inhere <inhere@okzl.com>
 * @date 2022/4/18 21:16
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private static final String MAX_AGE = "18000L";

    /**
     * 前置处理器
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return bool
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 跨域设置
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
            response.setStatus(HttpStatus.OK.value());
        }

        return true;
    }

}
