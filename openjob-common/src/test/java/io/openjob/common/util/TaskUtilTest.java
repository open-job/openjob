package io.openjob.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskUtilTest {

    @Test
    public void testGetRandomUniqueIdLastId() {
        String uniqueId = "16_6_0_105";
        Long randomUniqueIdLastId = TaskUtil.getRandomUniqueIdLastId(uniqueId);
        Assertions.assertEquals(randomUniqueIdLastId, 105L);

        String uniqueId2 = "";
        Long randomUniqueIdLastId2 = TaskUtil.getRandomUniqueIdLastId(uniqueId2);
        Assertions.assertEquals(randomUniqueIdLastId2, 0L);
    }
}
