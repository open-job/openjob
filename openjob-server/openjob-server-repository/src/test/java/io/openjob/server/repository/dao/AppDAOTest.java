package io.openjob.server.repository.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.entity.App;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/app.sql")
public class AppDAOTest extends RepositoryTest {
    private final AppDAO appDAO;

    @Autowired
    public AppDAOTest(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    @BeforeEach
    public void beforeMethod() {
        App app = new App();
        app.setNamespaceId(1L);
        app.setName("xxx-service");
        app.setDesc("xxx-desc");
        app.setUpdateTime(DateUtil.timestamp());
        app.setCreateTime(DateUtil.timestamp());
        app.setDeleted(2);
        app.setDeleteTime(0L);
        appDAO.save(app);
    }

    @Test
    public void testGetAppByName() {
        App app = appDAO.getAppByName("xxx-service");
        Assertions.assertNotNull(app);

        Assertions.assertEquals(app.getName(), "xxx-service");
    }
}
