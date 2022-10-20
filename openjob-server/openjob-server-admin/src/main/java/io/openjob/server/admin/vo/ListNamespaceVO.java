package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel
public class ListNamespaceVO {

    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("Namespace list.")
    private List<NamespaceVO> namespaceList;

    public static class NamespaceVO {

    }
}
