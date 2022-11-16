package io.openjob.common.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class OneDTO {
    private String name;
    private Integer id;
    private ChildDTO childDTO;
    private List<ChildDTO> childDTOList;
}
