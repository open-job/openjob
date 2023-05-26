package io.openjob.server.common.dto;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class DestinationDTO {
    private Integer id;
    private String name;
    private Boolean type;
    private Long time;
}
