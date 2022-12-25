package io.openjob.server.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:41
 */
@Data
public class ListJobVO {

    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("Job list.")
    private List<JobVO> jobList;

    @Data
    @ApiModel("Job item")
    public static class JobVO {

        @ApiModelProperty(value = "Namespace primary id", required = true)
        private Long id;

        @ApiModelProperty(value = "Namespace name", required = true)
        private String name;

        @ApiModelProperty(value = "Namespace desc", required = true)
        private String desc;

        @ApiModelProperty(value = "Namespace status", required = true)
        private Integer status;
    }
}
