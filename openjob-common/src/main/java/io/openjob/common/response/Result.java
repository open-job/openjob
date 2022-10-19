package io.openjob.common.response;

import io.openjob.common.constant.StatusEnum;
import io.openjob.common.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class Result<T> implements Serializable {

    /**
     * data
     */
    private T data;

    /**
     * status
     */
    private Integer status = StatusEnum.SUCCESS.getStatus();

    /**
     * code
     */
    private Integer code = 0;

    /**
     * message
     */
    private String message = "";

    /**
     * server time.
     */
    private Long serverTime = DateUtil.milliLongTime();

    /**
     * init
     */
    public Result(T data) {
        this.data = data;
    }

    /**
     * init
     */
    public Result(T data, Integer code, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * init
     */
    public Result(T data, Integer status, Integer code, String message) {
        this.data = data;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * success
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * success
     */
    public static <T> Result<T> success(T data, Integer code, String message) {
        return new Result<>(data, code, message);
    }

    /**
     * fail
     */
    public static Result<Object> fatal(String message) {
        return new Result<>(new Object(), 1, 0, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(null, StatusEnum.FAIL.getStatus(), 0, message);
    }

    /**
     * normal
     */
    public static <T> Result<T> normal(T data, Integer status, Integer code, String message) {
        return new Result<>(data, status, code, message);
    }
}
