package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum TaskStatusEnum {
    INIT(1, "初始化"),
    RUNNING(5, "运行"),
    SUCCESS(10, "成功"),
    FAILED(15, "失败");

    /**
     * Finish status.
     */
    public static final List<Integer> FINISH_STATUS = Arrays.asList(SUCCESS.getStatus(), FAILED.getStatus());

    private final Integer status;
    private final String message;
}
