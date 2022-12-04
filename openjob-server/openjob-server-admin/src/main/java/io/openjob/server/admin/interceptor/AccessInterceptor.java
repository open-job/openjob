package io.openjob.server.admin.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.service.AdminLoginService;
import io.openjob.server.admin.vo.part.PermItemVO;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import io.openjob.server.common.exception.BusinessException;
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

    private final Cache<String, AdminUserLoginVO> loginCache;

    private final List<String> noLoginRoutes;

    @Autowired
    public AccessInterceptor(AdminLoginService adminLoginService, Cache<String, AdminUserLoginVO> loginCache) {
        this.adminLoginService = adminLoginService;
        this.loginCache = loginCache;

        noLoginRoutes = new ArrayList<>();
        noLoginRoutes.add("/admin/login");
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
            AdminUserLoginVO user = adminLoginService.authByToken(token);

            // check perms for user
            if (!checkUserPerm(route, user)) {
                throw new BusinessException("no permission for " + route);
            }
            return true;
        }

        // web login
        if (!noLoginRoutes.contains(route)) {
            String sessKey = request.getHeader(AdminConstant.HEADER_SESSION_KEY);
            if (StringUtils.isBlank(sessKey)) {
                throw new BusinessException("need login for " + route);
            }

            // check perms for user
            AdminUserLoginVO user = loginCache.getIfPresent(sessKey);
            if (!checkUserPerm(route, user)) {
                throw new BusinessException("no permission for " + route);
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

    private Boolean checkUserPerm(String route, AdminUserLoginVO user) {
        if (Objects.isNull(user)) {
            throw new BusinessException("invalid login information");
        }

        if (user.getSupperAdmin()) {
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
