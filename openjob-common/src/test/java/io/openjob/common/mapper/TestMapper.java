package io.openjob.common.mapper;

import io.openjob.common.dto.OneDTO;
import io.openjob.common.dto.TwoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Mapper
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    TwoDTO oneDTO2TwoDTO(OneDTO oneDTO);

    List<TwoDTO> one2twos(List<OneDTO> ones);
}
