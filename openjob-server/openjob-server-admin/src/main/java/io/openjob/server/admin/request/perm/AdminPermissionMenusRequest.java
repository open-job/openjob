package io.openjob.server.admin.request.perm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class AdminPermissionMenusRequest {

    @ApiModelProperty(value = "Admin uid", hidden = true)
    private Long uid;
}
