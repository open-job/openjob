package io.openjob.server.admin.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author inhere
 * @date 2022-11-15 14:16:53
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminRuleQueryRequest", description = "AdminRule query request")
public class AdminRuleQueryRequest {

    @Min(1)
    @NotNull()
    @ApiModelProperty(value = "PK")
    private Long id;

}

