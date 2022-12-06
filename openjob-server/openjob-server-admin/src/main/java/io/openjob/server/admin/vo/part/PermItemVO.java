package io.openjob.server.admin.vo.part;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "PermItemVO", description = "AdminUserLoginVO PermItem")
public class PermItemVO {

    @ApiModelProperty(value = "Perm name")
    private String name;

    @ApiModelProperty(value = "Route path or API path")
    private String path;

    @ApiModelProperty(value = "Extra meta data. JSON object: {title:some.name}")
    private MenuMetaVO meta;
}
