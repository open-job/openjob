package io.openjob.worker.persistence;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class H2MemoryPersistence implements TaskPersistence {
    @Override
    public void initTable() throws Exception {
        String createSql = "CREATE TABLE `task` (\n" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `job_id` bigint(20) NOT NULL,\n" +
                "  `instance_id` bigint(20) NOT NULL,\n" +
                "  `circle_id` bigint(20) NOT NULL DEFAULT '0',\n" +
                "  `task_id` varchar(64) NOT NULL DEFAULT '',\n" +
                "  `task_name` varchar(128) NOT NULL,\n" +
                "  `task_parent_id` varchar(64) NOT NULL DEFAULT '0',\n" +
                "  `status` tinyint(2) NOT NULL DEFAULT '1',\n" +
                "  `worker_address` varchar(32) NOT NULL DEFAULT '',\n" +
                "  `task_body` blob,\n" +
                "  `create_time` int(11) NOT NULL,\n" +
                "  `update_time` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `udx_task_id` (`task_id`),\n" +
                "  KEY `idx_instance_id_circle_id` (`instance_id`,`circle_id`)\n" +
                ")";
    }
}
