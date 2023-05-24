package io.openjob.server.log.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.client.Elasticsearch7Client;
import io.openjob.server.log.dao.impl.Elasticsearch7DAOImpl;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class Elasticsearch7DAOImplTest {
    private static Elasticsearch7DAOImpl elasticsearch7DAO;

    @Test
    public void testBatchAdd() throws InterruptedException {
        List<ProcessorLogDTO> jobInstanceTaskLogs = new ArrayList<>();
        ProcessorLogDTO processorLogDTO = new ProcessorLogDTO();
        processorLogDTO.setTime(DateUtil.milliLongTime());
        processorLogDTO.setWorkerAddress("worker2Address");
        processorLogDTO.setTaskId("task2Id");

        List<ProcessorLogFieldDTO> fieldList = new ArrayList<>();
        ProcessorLogFieldDTO processorLogFieldDTO = new ProcessorLogFieldDTO("name", "jobbId");
        fieldList.add(processorLogFieldDTO);
        processorLogDTO.setFields(fieldList);
        jobInstanceTaskLogs.add(processorLogDTO);

        elasticsearch7DAO.batchAdd(jobInstanceTaskLogs);

        Thread.sleep(5000L);
    }

    @Test
    public void testQueryByPage() throws Exception {
        elasticsearch7DAO.queryByPage("task2Id",0L,10L);
    }

    @BeforeAll
    public static void CreateElasticsearch7() throws Exception {
        LogProperties.Elasticsearch7Properties elasticsearch7Properties = new LogProperties.Elasticsearch7Properties();
        elasticsearch7Properties.setClusterNodes("localhost:9200");
        elasticsearch7Properties.setProtocol("http");
        elasticsearch7Properties.setSocketTimeout(3000);
        elasticsearch7Properties.setConnectTimeout(3000);
        elasticsearch7Properties.setResponseTimeout(3000);
        elasticsearch7Properties.setBufferLimit(104857600);

        Elasticsearch7Client elasticsearch7Client = new Elasticsearch7Client(elasticsearch7Properties);
        elasticsearch7Client.afterPropertiesSet();
        elasticsearch7DAO = new Elasticsearch7DAOImpl(elasticsearch7Client, elasticsearch7Properties);
    }
}
