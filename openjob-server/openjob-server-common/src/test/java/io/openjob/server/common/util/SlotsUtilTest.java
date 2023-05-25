package io.openjob.server.common.util;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class SlotsUtilTest {
    @Test
    public void testGetWorkerSupervisorSlotsId() {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setWorkerSupervisorSlot(6);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long slotId = SlotsUtil.getWorkerSupervisorSlotsId(UUID.randomUUID().toString());
            Assertions.assertTrue(slotId >= 1);
            Assertions.assertTrue(slotId <= 6);
        }
    }
}
