package io.openjob.server.repository.dao;

import io.openjob.common.constant.CommonConstant;
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
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/job_instance.sql")
public class JobInstanceDAOTest extends RepositoryTest {
    private final JobInstanceDAO jobInstanceDAO;
    private final JobInstanceRepository jobInstanceRepository;

    @Autowired
    public JobInstanceDAOTest(JobInstanceDAO jobInstanceDAO, JobInstanceRepository jobInstanceRepository) {
        this.jobInstanceDAO = jobInstanceDAO;
        this.jobInstanceRepository = jobInstanceRepository;
    }

    @Test
    public void testSave() {
        JobInstance jobInstance = new JobInstance();
        jobInstance.setJobId(1L);
        jobInstance.setParams("");
        jobInstance.setSlotsId(1L);
        jobInstance.setNamespaceId(1L);
        jobInstance.setAppId(1L);
        jobInstance.setExecuteTime(DateUtil.timestamp());
        jobInstance.setDeleted(CommonConstant.NO);
        jobInstance.setDeleteTime(0L);
        jobInstance.setFailStatus(0);
        jobInstance.setExecuteTimeout(0);
        jobInstance.setUpdateTime(DateUtil.timestamp());
        jobInstance.setCreateTime(DateUtil.timestamp());

        Long id = jobInstanceDAO.save(jobInstance);

        Optional<JobInstance> optionalJobInstance = jobInstanceRepository.findById(id);
        Assertions.assertTrue(optionalJobInstance.isPresent());

        optionalJobInstance.ifPresent(j -> Assertions.assertEquals(j.getJobId(), 1L));
    }
}
