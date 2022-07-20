package io.openjob.common.response;

import io.openjob.common.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lilin <lilin@kzl.com.cn>
 * @date 2020-07-23 19:52:46
 */
@Data
public class Result<T> implements Serializable {
    /**
     * 数据
     */
    private T data;

    /**
     * 请求状态
     */
    private Integer status = 0;

    /**
     * 业务状态
     */
    private Integer code = 0;

    /**
     * 消息
     */
    private String message = "";

    /**
     * 当前时间(ms)
     */
    private Long serverTime = DateUtil.milliLongTime();

    /**
     * 数据初始化
     */
    public Result(T data) {
        this.data = data;
    }

    /**
     * 数据+业务Code+消息 初始化
     */
    public Result(T data, Integer code, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 全数据初始化
     */
    public Result(T data, Integer status, Integer code, String message) {
        this.data = data;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * 请求成功
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 业务成功
     */
    public static <T> Result<T> success(T data, Integer code, String message) {
        return new Result<>(data, code, message);
    }

    /**
     * 请求失败
     */
    public static Result<Object> fatal(String message) {
        return new Result<>(new Object(), 1, 0, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(null, 1, 0, message);
    }

    /**
     * 自定义返回
     */
    public static <T> Result<T> normal(T data, Integer status, Integer code, String message) {
        return new Result<>(data, status, code, message);
    }
}
