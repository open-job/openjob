package io.openjob.server.admin.autoconfigure;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.vo.user.AdminUserLoginVO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author inhere
 */
@Configuration
@EnableConfigurationProperties(AdminUserProperties.class)
public class AdminAutoConfiguration {

    private final AdminUserProperties userProperties;

    public AdminAutoConfiguration(AdminUserProperties userProperties) {
        this.userProperties = userProperties;
    }

    @Bean
    public Cache<String, AdminUserLoginVO> loginCache() {
        return Caffeine.newBuilder()
                // cache的初始容量
                .initialCapacity(userProperties.getCacheInitSize())
                // cache最大缓存数
                .maximumSize(userProperties.getCacheMaxSize())
                // 设置写缓存后n秒钟过期
                .expireAfterWrite(AdminConstant.LOGIN_EXPIRE_TIME, TimeUnit.SECONDS)
                // 设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
    }
}
