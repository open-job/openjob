package io.openjob.server.log.mapper;

import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Mapper
public interface LogMapper {
    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    /**
     * To ProcessorLogFieldList
     *
     * @param list list
     * @return List
     */
    List<ProcessorLogFieldDTO> toProcessorLogFieldList(List<WorkerJobInstanceTaskLogFieldRequest> list);
}
