package io.openjob.server.repository;

import io.openjob.server.repository.config.H2Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@SpringBootTest(classes = {H2Config.class, RepositoryApplication.class})
@Transactional
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class RepositoryTest {
}
