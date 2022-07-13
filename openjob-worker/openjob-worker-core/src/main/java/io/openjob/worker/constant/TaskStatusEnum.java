package io.openjob.worker.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {
    UNKNOWN(0, "未知", false),
    INIT(1, "初始化", false),
    PULLED(2, "已拉取", false),
    RUNNING(3, "运行", false),
    SUCCESS(4, "成功", true),
    FAILED(5, "失败", true);

    private final Integer value;
    private final String description;
    private final Boolean isFinish;
}
