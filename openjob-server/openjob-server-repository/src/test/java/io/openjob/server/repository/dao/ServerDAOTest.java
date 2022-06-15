package io.openjob.server.repository.dao;

import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.entity.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/server.sql")
public class ServerDAOTest extends RepositoryTest {
    private final ServerDAO serverDAO;

    @Autowired
    public ServerDAOTest(ServerDAO serverDAO) {
        this.serverDAO = serverDAO;
    }

    @Test
    public void test() {
        Server server = new Server();
        server.setStatus(1);
        server.setCreateTime(1);
        server.setUpdateTime(1);
        long id = serverDAO.save(server);

    }
}
