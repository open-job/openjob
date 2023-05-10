package io.openjob.server.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class HmacUtilTest {
    @Test
    public void testHashPasswd() {
        String key = "3TJPjpUanNqZ0N1";
        String password = "openjob.io";
        String hashValue = HmacUtil.hashPasswd(password, key);
        Assertions.assertTrue(HmacUtil.verifyPasswd(hashValue, password, key));
    }
}
