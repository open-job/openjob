package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.constant.LogJdbcDriverConstant;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class OracleClient extends AbstractJdbcHikariClient {
    private final LogProperties.OracleProperties oracleProperties;

    public OracleClient(LogProperties.OracleProperties oracleProperties) {
        this.oracleProperties = oracleProperties;
    }

    @Override
    public void afterPropertiesSet() {
        // Driver
        LogProperties.JdbcProperties properties = oracleProperties.getProperties();
        if (Objects.isNull(properties.getDriver())) {
            properties.setDriver(LogJdbcDriverConstant.ORACLE);
        }

        this.init(properties);
    }
}
