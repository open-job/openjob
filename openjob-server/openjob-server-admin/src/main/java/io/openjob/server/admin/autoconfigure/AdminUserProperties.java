package io.openjob.server.admin.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author inhere <inhere@okzl.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.admin.user")
public class AdminUserProperties {
    /**
     * passwd salt
     */
    private String passwdSalt;
}
