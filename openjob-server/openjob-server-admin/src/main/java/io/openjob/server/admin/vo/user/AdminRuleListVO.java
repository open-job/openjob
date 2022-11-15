package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-15 14:16:57
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRuleListVO", description = "AdminRule list VO")
public class AdminRuleListVO {

    @ApiModelProperty(value = "total count")
    private Integer total;

    @ApiModelProperty(value = "user list")
    private List<AdminRuleQueryVO> list;


}

