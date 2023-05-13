package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.MysqlClient;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class MysqlLogDAOImpl extends JdbcDAOImpl {
    public MysqlLogDAOImpl(MysqlClient mysqlClient) {
        super(mysqlClient);
    }
}
