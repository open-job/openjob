package io.openjob.server.cluster.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ResponseDTO implements Serializable {
    private Integer status;
    private String message;
    private String data;
}
