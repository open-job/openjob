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
    public void testGetSlotsId() {
        this.genSlotsId(1);
        this.genSlotsId(256);
    }

    @Test
    public void testGetWorkerSupervisorSlotsId() {
        this.genWorkerSupervisorSlotsId(1);
        this.genWorkerSupervisorSlotsId(6);
    }

    private void genWorkerSupervisorSlotsId(Integer maxSlot) {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setWorkerSupervisorSlot(maxSlot);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long slotId = SlotsUtil.getWorkerSupervisorSlotsId(UUID.randomUUID().toString());
            Assertions.assertTrue(slotId >= 1);
            Assertions.assertTrue(slotId <= maxSlot);
        }
    }

    private void genSlotsId(Integer maxSlot) {
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setMaxSlot(maxSlot);
        ClusterContext.refreshSystem(systemDTO);

        for (int i = 0; i < 1000; i++) {
            long slotId = SlotsUtil.getSlotsId(UUID.randomUUID().toString());
            Assertions.assertTrue(slotId >= 1);
            Assertions.assertTrue(slotId <= maxSlot);
        }
    }
}
