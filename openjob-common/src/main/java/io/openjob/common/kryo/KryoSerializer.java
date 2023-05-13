package io.openjob.common.kryo;

import akka.serialization.JSerializer;
import io.openjob.common.util.KryoUtil;

/**
 * @author stelin swoft@qq.com
 * @see akka https://doc.akka.io/docs/akka/current/serialization.html
 * @since 1.0.0
 */
public class KryoSerializer extends JSerializer {

    /**
     * Deserialize the given array to object.
     * using the type hint (if any, see "includeManifest" above)
     *
     * @param bytes    bytes
     * @param manifest manifest
     * @return object
     */
    @Override
    public Object fromBinaryJava(byte[] bytes, Class<?> manifest) {
        return KryoUtil.deserialize(bytes);
    }

    /**
     * Pick a unique identifier for your Serializer,
     * you've got a couple of billions to choose from,
     * 0 - 40 is reserved by Akka itself
     *
     * @return int
     */
    @Override
    public int identifier() {
        return 123456789;
    }

    /**
     * Serialize the given object to an Array of Bytes
     *
     * @param o object
     * @return byte
     */
    @Override
    public byte[] toBinary(Object o) {
        return KryoUtil.serialize(o);
    }

    /**
     * Whether "fromBinary" requires a "clazz" or not, default is false.
     *
     * @return boolean
     */
    @Override
    public boolean includeManifest() {
        return false;
    }
}
