package io.openjob.server.log.mapper;

import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.server.log.dto.ProcessorLogField;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Mapper
public interface LogMapper {
    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    List<ProcessorLogField> toProcessorLogFieldList(List<WorkerJobInstanceTaskLogFieldRequest> list);
}
