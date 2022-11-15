
package io.openjob.server.admin.request.user;

import io.openjob.server.admin.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inhere
 * @date 2022-11-07 20:29:02
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AdminUserListRequest", description = "AdminUserListRequest")
public class AdminUserListRequest extends BasePageRequest {

}

