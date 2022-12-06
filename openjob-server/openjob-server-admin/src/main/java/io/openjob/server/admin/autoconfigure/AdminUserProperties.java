package io.openjob.server.admin.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author inhere <inhere@okzl.com>
 * @date 2022/12/1 19:50
 */
@Data
@ConfigurationProperties(prefix = "openjob.admin.user")
public class AdminUserProperties {
    /**
     * passwd salt
     */
    private String passwdSalt;

    private Integer cacheInitSize;

    private Long cacheMaxSize;

}
