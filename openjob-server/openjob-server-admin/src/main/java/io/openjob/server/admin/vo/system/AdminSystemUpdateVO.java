package io.openjob.server.admin.vo.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author riki
 * @date 2022-12-26
 */
@Data
@ApiModel(value = "AdminSystemVO", description = "Admin System update VO")
public class AdminSystemUpdateVO {
    @ApiModelProperty(value = "PK")
    private Long id;

}
