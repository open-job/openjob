package io.openjob.worker.master;

import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.worker.dto.JobInstanceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskMasterFactoryTest {
    @Test
    public void testCreate() {
        JobInstanceDTO jobInstanceDTO = new JobInstanceDTO();
        jobInstanceDTO.setId(1L);
        jobInstanceDTO.setExecuteType(ExecuteTypeEnum.STANDALONE.getType());

        TaskMaster taskMaster = TaskMasterFactory.create(jobInstanceDTO, null);
        Assertions.assertNotNull(taskMaster);
    }
}
