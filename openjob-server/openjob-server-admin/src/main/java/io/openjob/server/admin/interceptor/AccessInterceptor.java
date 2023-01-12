package io.openjob.server.admin.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.constant.AdminHttpStatusEnum;
import io.openjob.server.admin.dto.AdminUserSessionDTO;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.part.PermItemVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 请求访问拦截器
 * - 跨域设置
 *
 * @author inhere
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private static final String MAX_AGE = "18000L";

    private final AdminLoginService adminLoginService;

    private final Cache<String, AdminUserSessionDTO> loginCache;

    private final List<String> noLoginRoutes;

    private final List<String> noLoginPrefixes;

    /**
     * can access after login, not need config perms.
     * - eg: logout
     */
    private final List<String> notAuthRoutes;

    @Autowired
    public AccessInterceptor(AdminLoginService adminLoginService, Cache<String, AdminUserSessionDTO> loginCache) {
        this.adminLoginService = adminLoginService;
        this.loginCache = loginCache;

        noLoginRoutes = new ArrayList<>();
        noLoginRoutes.add("/");
        noLoginRoutes.add("/csrf");
        noLoginRoutes.add("/error");
        noLoginRoutes.add("/admin/login");
        noLoginRoutes.add("/favicon.ico");
        noLoginRoutes.add("/swagger-ui.html");

        noLoginPrefixes = new ArrayList<>();
        noLoginPrefixes.add("/webjars/");
        noLoginPrefixes.add("/swagger-resources");
        noLoginPrefixes.add("/null/swagger-resources");

        notAuthRoutes = new ArrayList<>();
        notAuthRoutes.add("/admin/logout");
        notAuthRoutes.add("/admin/user-info");
    }

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
        String route = request.getRequestURI();

        // api auth
        String token = request.getHeader(AdminConstant.HEADER_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)) {
            AdminUserSessionDTO user = adminLoginService.authByToken(token);

            // check perms for user
            if (!checkUserPerm(route, user)) {
                AdminHttpStatusEnum.FORBIDDEN.throwException();
            }
            return true;
        }

        // web login
        if (!isNoLoginRoute(route)) {
            String sessKey = request.getHeader(AdminConstant.HEADER_SESSION_KEY);
            if (StringUtils.isBlank(sessKey)) {
                AdminHttpStatusEnum.UNAUTHORIZED.throwException();
            }

            // check perms for user
            AdminUserSessionDTO user = loginCache.getIfPresent(sessKey);
            if (!checkUserPerm(route, user)) {
                AdminHttpStatusEnum.FORBIDDEN.throwException();
            }
        }

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

    /**
     * is no login required route path.
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

    private Boolean checkUserPerm(String route, AdminUserSessionDTO user) {
        if (Objects.isNull(user)) {
            AdminHttpStatusEnum.FORBIDDEN.throwException();
        }

        if (user.getSupperAdmin() || notAuthRoutes.contains(route)) {
            return true;
        }

        if (CollectionUtils.isEmpty(user.getPerms())) {
            return false;
        }

        // match by route path.
        PermItemVO perm = user.getPerms()
                .stream()
                .filter(item -> Objects.equals(item.getPath(), route))
                .findAny()
                .orElse(null);

        return Objects.nonNull(perm);
    }
}
