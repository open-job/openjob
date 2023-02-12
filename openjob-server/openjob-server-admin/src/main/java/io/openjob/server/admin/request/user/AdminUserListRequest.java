
package io.openjob.server.admin.request.user;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AdminUserListRequest", description = "AdminUserListRequest")
public class AdminUserListRequest extends PageRequest {

}

