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
 * @date 2022-11-13 23:27:31
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminMenuDelete", description = "Delete AdminMenu")
public class AdminMenuDeleteRequest {

    @Min(1)
    @NotNull()
    @ApiModelProperty(value = "PK")
    private Long id;

}

