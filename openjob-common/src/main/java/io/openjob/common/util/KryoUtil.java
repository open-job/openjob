package io.openjob.common.util;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class KryoUtil {
    private static final ThreadLocal<Kryo> KRYO_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();

        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        kryo.setClassLoader(Thread.currentThread().getContextClassLoader());
        return kryo;
    });

    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);

        Kryo kryo = KRYO_LOCAL.get();
        kryo.writeClassAndObject(output, object);
        output.flush();

        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] byteArray) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        Input input = new Input(byteArrayInputStream);

        Kryo kryo = KRYO_LOCAL.get();
        return kryo.readClassAndObject(input);
    }

    public static void remove() {
        KRYO_LOCAL.remove();
    }
}
