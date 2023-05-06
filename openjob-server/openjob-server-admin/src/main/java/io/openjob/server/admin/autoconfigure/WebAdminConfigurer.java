package io.openjob.server.admin.autoconfigure;

import io.openjob.server.admin.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author inhere <inhere@okzl.com>
 */
@Configuration
public class WebAdminConfigurer implements WebMvcConfigurer {

    private final AccessInterceptor accessInterceptor;

    @Autowired()
    public WebAdminConfigurer(AccessInterceptor accessInterceptor) {
        this.accessInterceptor = accessInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor).addPathPatterns("/admin/**");
    }
}
