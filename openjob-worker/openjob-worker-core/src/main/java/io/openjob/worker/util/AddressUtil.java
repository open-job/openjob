package io.openjob.worker.util;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class AddressUtil {

    /**
     * Get worker address
     *
     * @param localWorkerAddress like `akka://openjob-worker@172.20.1.33:25531`
     * @return 172.20.1.33:25531
     */
    public static String getWorkerAddressByLocal(String localWorkerAddress) {
        int index = localWorkerAddress.indexOf("@");
        return localWorkerAddress.substring(index + 1);
    }
}
