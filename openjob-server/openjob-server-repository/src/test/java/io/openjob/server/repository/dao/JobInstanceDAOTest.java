package io.openjob.server.repository.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/job_instance.sql")
public class JobInstanceDAOTest extends RepositoryTest {
//    private final JobInstanceDAO jobInstanceDAO;
//    private final JobInstanceRepository jobInstanceRepository;
//
//    @Autowired
//    public JobInstanceDAOTest(JobInstanceDAO jobInstanceDAO, JobInstanceRepository jobInstanceRepository) {
//        this.jobInstanceDAO = jobInstanceDAO;
//        this.jobInstanceRepository = jobInstanceRepository;
//    }
//
//    @Test
//    public void testSaveAndUpdate() throws InterruptedException {
//        JobInstance jobInstance = new JobInstance();
//        jobInstance.setJobId(1L);
//        jobInstance.setJobParams("");
//        jobInstance.setSlotsId(1L);
//        jobInstance.setNamespaceId(1L);
//        jobInstance.setAppId(1L);
//        jobInstance.setExecuteTime(DateUtil.now());
//        jobInstance.setUpdateTime(DateUtil.now());
//        jobInstance.setCreateTime(DateUtil.now());
//
//        Long id = jobInstanceDAO.save(jobInstance);
//
//        Optional<JobInstance> optionalJobInstance = jobInstanceRepository.findById(id);
//        Assertions.assertTrue(optionalJobInstance.isPresent());
//
//        optionalJobInstance.ifPresent(j -> Assertions.assertEquals(j.getJobId(), 1L));
//
//        Thread.sleep(3000L);
//        Integer now = DateUtil.now();
//        JobInstance updateJobInstance = new JobInstance();
//        updateJobInstance.setId(id);
//        updateJobInstance.setJobId(2L);
//        updateJobInstance.setUpdateTime(now);
//        updateJobInstance.setCreateTime(now);
//        updateJobInstance.setStatus(3);
//
//        this.jobInstanceDAO.save(updateJobInstance);
//
//        Optional<JobInstance> updateOptional = jobInstanceRepository.findById(id);
//        Assertions.assertTrue(updateOptional.isPresent());
//
//        updateOptional.ifPresent(j -> {
//            Assertions.assertEquals(j.getUpdateTime(), now);
//            Assertions.assertEquals(j.getJobId(), 2L);
//        });
//    }
}
