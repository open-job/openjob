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
@ApiModel("List namespace")
public class ListNamespaceVO {

    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("Page total.")
    private Integer total;

    @ApiModelProperty("Namespace list.")
    private List<NamespaceVO> list;

    @Data
    @ApiModel("Namespace item")
    public static class NamespaceVO {

        @ApiModelProperty(value = "Namespace primary id", required = true)
        private Long id;

        @ApiModelProperty(value = "Namespace name", required = true)
        private String name;

        @ApiModelProperty(value = "Namespace uuid", required = true)
        private String uuid;

        @ApiModelProperty(value = "Namespace status", required = true)
        private Integer status;

        @ApiModelProperty(value = "Namespace createTime", required = true)
        private Long createTime;
    }
}
