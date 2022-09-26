package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class H2Client extends JdbcHikariClient {
    private final LogProperties.H2Properties h2Properties;

    public H2Client(LogProperties.H2Properties h2Properties) {
        this.h2Properties = h2Properties;

        this.init(h2Properties.getProperties());
    }
}
