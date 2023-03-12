package io.openjob.server.log.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ProcessorLogField {
    private String name;
    private String value;

    public ProcessorLogField(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
