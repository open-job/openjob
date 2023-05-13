package io.openjob.server.repository.dao;

import io.openjob.server.repository.RepositoryTest;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.repository.ServerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Sql(scripts = "classpath:db/schema/server.sql")
public class ServerDAOTest extends RepositoryTest {
    private final ServerDAO serverDAO;
    private final ServerRepository serverRepository;

    @Autowired
    public ServerDAOTest(ServerDAO serverDAO, ServerRepository serverRepository) {
        this.serverDAO = serverDAO;
        this.serverRepository = serverRepository;
    }

    @BeforeEach
    public void beforeMethod() {
        String ip = "127.0.0.1";
        String akkaAddress = "127.0.0.1:25520";

        Server server = new Server();
        server.setIp(ip);
        server.setAkkaAddress(akkaAddress);
        serverDAO.save(server);
    }

    @Test
    public void testSave() {
        String ip = "127.0.0.2";
        String akkaAddress = "127.0.0.2:25520";
        Server server = new Server();
        server.setIp(ip);
        server.setAkkaAddress(akkaAddress);
        Long id = serverDAO.save(server);

        Assertions.assertNotNull(id);
        Optional<Server> optionalServer = serverRepository.findById(id);
        Assertions.assertTrue(optionalServer.isPresent());

        optionalServer.ifPresent((s) -> {
            Assertions.assertEquals(s.getIp(), server.getIp());
            Assertions.assertEquals(s.getAkkaAddress(), server.getAkkaAddress());
        });
    }

    @Test
    public void testListServers() {
        List<Server> servers = serverDAO.listServers(ServerStatusEnum.OK.getStatus());
        Assertions.assertNotNull(servers);
    }

    @Test
    public void testListServersByEmpty() {
        List<Server> servers = serverDAO.listServers(6);
        Assertions.assertEquals(servers.size(), 0);
    }
}
