package io.openjob.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class KryoUtilTest {
    @Test
    public void testKryo() {
        MyChildKryo cname = new MyChildKryo("cname", 2);
        MyKryo myKryo = new MyKryo("name", 1, cname);
        byte[] serialize = KryoUtil.serialize(myKryo);
        Assertions.assertTrue(serialize.length > 0);

        MyKryo deserialize = (MyKryo) KryoUtil.deserialize(serialize);
        Assertions.assertEquals(deserialize.getName(), "name");
        Assertions.assertEquals(deserialize.getMyChildKryo().getName(), "cname");
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MyKryo {
        private String name;
        private Integer id;
        private MyChildKryo myChildKryo;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class MyChildKryo {
        private String name;
        private Integer id;
    }
}
