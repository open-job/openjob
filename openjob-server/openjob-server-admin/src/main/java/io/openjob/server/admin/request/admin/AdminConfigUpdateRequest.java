package io.openjob.server.admin.request.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigUpdateRequest", description = "AdminConfig update request")
public class AdminConfigUpdateRequest {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "PK")
    private Long id;

    @ApiModelProperty(value = "Config name")
    private String name;

    @ApiModelProperty(value = "Config value")
    private String value;

}

