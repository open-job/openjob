package io.openjob.server.admin.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRuleUpdateVO", description = "AdminRule update VO")
public class AdminRuleUpdateVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

