package io.openjob.worker.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ConfigUtilTest {
    @Test
    public void testClassPathFile() {
        Properties properties = ConfigUtil.loadProperties("classpath:openjob.properties");
        Assertions.assertNotNull(properties);

        String value = properties.getProperty("openjob.worker.test", "none");
        Assertions.assertEquals(value, "test");
    }
}
