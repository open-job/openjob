package io.openjob.server.repository.dao;

import com.google.common.collect.Lists;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.entity.DelayInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/delay_instance.sql")
public class DelayInstanceDAOTest extends RepositoryTest {
    private final DelayInstanceDAO delayInstanceDAO;

    @Autowired
    public DelayInstanceDAOTest(DelayInstanceDAO delayInstanceDAO) {
        this.delayInstanceDAO = delayInstanceDAO;
    }

    @Test
    public void testBatchSave() {
        List<DelayInstance> list = Lists.newArrayList();

        int saveSize = 3;
        for (int i = 0; i < saveSize; i++) {
            DelayInstance delayInstance = new DelayInstance();
            delayInstance.setNamespaceId(1L);
            delayInstance.setAppId(1L);
            delayInstance.setTaskId(UUID.randomUUID().toString());
            delayInstance.setTopic("topic");
            delayInstance.setDelayId(2L);
            delayInstance.setDelayParams("p");
            delayInstance.setDelayExtra("e");
            delayInstance.setStatus(1);
            delayInstance.setExecuteTime(DateUtil.timestamp());
            delayInstance.setDeleted(2);
            delayInstance.setDeleteTime(0L);
            delayInstance.setCreateTime(DateUtil.timestamp());
            delayInstance.setUpdateTime(DateUtil.timestamp());
            list.add(delayInstance);
        }
        int count = this.delayInstanceDAO.batchSave(list);
        Assertions.assertEquals(count, saveSize);
    }
}
