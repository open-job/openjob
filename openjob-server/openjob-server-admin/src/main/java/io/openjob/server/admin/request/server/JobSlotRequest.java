package io.openjob.server.admin.request.server;

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
@ApiModel(value = "JobSlotRequest", description = "Job slot list request")
public class JobSlotRequest extends PageRequest {
}

