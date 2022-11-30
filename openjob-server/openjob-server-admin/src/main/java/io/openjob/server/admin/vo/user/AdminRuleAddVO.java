package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRuleVO", description = "AdminRule add VO")
public class AdminRuleAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;
}

