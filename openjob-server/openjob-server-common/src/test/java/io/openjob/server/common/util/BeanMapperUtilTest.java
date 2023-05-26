package io.openjob.server.common.util;

import io.openjob.server.common.dto.DestinationDTO;
import io.openjob.server.common.dto.SourceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class BeanMapperUtilTest {
    @Test
    public void testMap() {
        SourceDTO sourceDTO = new SourceDTO();
        sourceDTO.setId(1);
        sourceDTO.setName("name");
        sourceDTO.setType(true);
        sourceDTO.setTime(6666L);

        DestinationDTO destinationDTO = BeanMapperUtil.map(sourceDTO, DestinationDTO.class);
        Assertions.assertEquals(destinationDTO.getId(), 1);
        Assertions.assertEquals(destinationDTO.getName(), "name");
        Assertions.assertEquals(destinationDTO.getType(), true);
        Assertions.assertEquals(destinationDTO.getTime(), 6666L);
    }

    @Test
    public void testMapList() {
        SourceDTO sourceDTO = new SourceDTO();
        sourceDTO.setId(1);
        sourceDTO.setName("name");
        sourceDTO.setType(true);
        sourceDTO.setTime(6666L);

        SourceDTO sourceDTO2 = new SourceDTO();
        sourceDTO2.setId(2);
        sourceDTO2.setName("name2");
        sourceDTO2.setType(true);
        sourceDTO2.setTime(66662222L);

        List<DestinationDTO> destinations = BeanMapperUtil.mapList(Arrays.asList(sourceDTO, sourceDTO2), SourceDTO.class, DestinationDTO.class);

        Assertions.assertEquals(destinations.size(), 2);
        destinations.forEach(d -> {
            if (d.getId().equals(1)) {
                Assertions.assertEquals(d.getId(), 1);
                Assertions.assertEquals(d.getName(), "name");
                Assertions.assertEquals(d.getType(), true);
                Assertions.assertEquals(d.getTime(), 6666L);
            } else {
                Assertions.assertEquals(d.getId(), 2);
                Assertions.assertEquals(d.getName(), "name2");
                Assertions.assertEquals(d.getType(), true);
                Assertions.assertEquals(d.getTime(), 66662222L);
            }
        });
    }
}
