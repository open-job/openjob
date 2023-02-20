package io.openjob.server.admin.request;

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
@ApiModel(value = "BasePageRequest", description = "Base Page Request")
public class PageRequest {
    @ApiModelProperty(value = "List page.")
    @NotNull
    @Min(1)
    private Integer page = 1;

    @ApiModelProperty(value = "List size. default 10")
    @NotNull
    @Min(1)
    private Integer size = 10;
}

