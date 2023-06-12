package io.openjob.server.admin.interceptor;

import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.constant.AdminHttpStatusEnum;
import io.openjob.server.admin.service.AdminUserService;
import io.openjob.server.repository.entity.AdminUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求访问拦截器
 * - 跨域设置
 *
 * @author inhere
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private static final String MAX_AGE = "18000L";
    private final AdminUserService adminUserService;
    private final List<String> noLoginRoutes;
    private final List<String> noLoginPrefixes;

    @Autowired
    public AccessInterceptor(AdminUserService adminUserService) {
        // No login routes
        noLoginRoutes = new ArrayList<>();
        noLoginRoutes.add("/");
        noLoginRoutes.add("/csrf");
        noLoginRoutes.add("/error");
        noLoginRoutes.add("/admin/login");
        noLoginRoutes.add("/favicon.ico");
        noLoginRoutes.add("/swagger-ui.html");

        // No login prefix
        noLoginPrefixes = new ArrayList<>();
        noLoginPrefixes.add("/webjars/");
        noLoginPrefixes.add("/swagger-resources");
        noLoginPrefixes.add("/null/swagger-resources");

        this.adminUserService = adminUserService;
    }

    /**
     * Pre handle
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return bool
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, @Nonnull Object handler) throws IOException {
        String route = request.getRequestURI();

        // Cross domain
        response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

        // Options method
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        // Login
        if (!isNoLoginRoute(route)) {
            String sessKey = request.getHeader(AdminConstant.HEADER_SESSION_KEY);
            if (StringUtils.isEmpty(sessKey)) {
                AdminHttpStatusEnum.UNAUTHORIZED.throwException();
                return false;
            }

            AdminUser user = this.adminUserService.getBySessionKey(sessKey);
            request.setAttribute(AdminConstant.REQUEST_UID_KEY, user.getId());
        }

        return true;
    }

    /**
     * Whether is no login
     *
     * @param route route path
     * @return bool
     */
    private Boolean isNoLoginRoute(String route) {
        if (noLoginRoutes.contains(route)) {
            return true;
        }

        for (String prefix : noLoginPrefixes) {
            if (route.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}
