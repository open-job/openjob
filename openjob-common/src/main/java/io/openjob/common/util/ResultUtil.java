package io.openjob.common.util;

import io.openjob.common.response.Result;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ResultUtil {
    public static Boolean isSuccess(Result<?> result) {
        return Result.SUCCESS.equals(result.getStatus());
    }
}
