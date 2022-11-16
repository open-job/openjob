package io.openjob.common.util;

import com.google.common.collect.Lists;
import io.openjob.common.dto.ChildDTO;
import io.openjob.common.dto.OneDTO;
import io.openjob.common.dto.TwoDTO;
import io.openjob.common.mapper.TestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */

public class MapperTest {

    @Test
    public void testMapper() {

        List<ChildDTO> childDTOList = Lists.newArrayList();
        childDTOList.add(new ChildDTO("nameList"));

        OneDTO oneDTO = new OneDTO();
        oneDTO.setId(1);
        oneDTO.setName("name");
        oneDTO.setChildDTO(new ChildDTO("child"));
        oneDTO.setChildDTOList(childDTOList);

        TwoDTO twoDTO = TestMapper.INSTANCE.oneDTO2TwoDTO(oneDTO);
        Assertions.assertEquals(twoDTO.getId(), 1);
        Assertions.assertEquals(twoDTO.getName(), "name");
        Assertions.assertEquals(twoDTO.getChildDTO().getName(), "child");

        List<OneDTO> ones = Lists.newArrayList();
        ones.add(oneDTO);

        List<TwoDTO> twos = TestMapper.INSTANCE.one2twos(ones);
        Assertions.assertEquals(twos.size(), 1);
        Assertions.assertEquals(twos.get(0).getId(), 1);
        Assertions.assertEquals(twos.get(0).getName(), "name");
        Assertions.assertEquals(twos.get(0).getChildDTO().getName(), "child");
    }
}
