package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @date 2022-11-15 14:15:06
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminConfigAddRequest", description = "AdminConfig add Request")
public class AdminConfigAddRequest {

    @ApiModelProperty(value = "Config name")
    private String name;

    @ApiModelProperty(value = "Config value")
    private String value;

}

