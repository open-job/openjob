package io.openjob.server.repository.dao;

import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.entity.Job;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/job.sql")
public class JobDAOTest extends RepositoryTest {
//    private final JobDAO jobDAO;
//
//    @Autowired
//    public JobDAOTest(JobDAO jobDAO) {
//        this.jobDAO = jobDAO;
//    }
//
//    @Test
//    public void testSave() {
//        Long id = jobDAO.save(new Job());
//        Assertions.assertNotNull(id);
//    }
}
