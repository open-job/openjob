package io.openjob.worker.util;

import io.openjob.common.util.DateUtil;
import io.openjob.common.util.IpUtil;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerIdUtil {
    public static String workerId;

    public static String getId(Integer port) {
        if (Objects.isNull(workerId)) {
            synchronized (WorkerIdUtil.class) {
                if (Objects.isNull(workerId)) {
                    workerId = String.format("%s-%d-%d", IpUtil.getFormatAddress(), port, DateUtil.now());
                }
            }
        }
        return workerId;
    }
}
