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
public class ListAppVO {

    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("App list.")
    private List<AppVO> appList;

    @Data
    @ApiModel("App item")
    public static class AppVO {

        @ApiModelProperty(value = "App primary id", required = true)
        private Long id;

        @ApiModelProperty(value = "App name", required = true)
        private String name;

        @ApiModelProperty(value = "App desc", required = true)
        private String desc;
    }
}
