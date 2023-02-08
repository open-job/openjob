package io.openjob.server.admin.vo.perm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminPermAddVO", description = "AdminPermAddVO")
public class AdminPermAddVO {

    @ApiModelProperty(value = "PK")
    private Long id;

}

