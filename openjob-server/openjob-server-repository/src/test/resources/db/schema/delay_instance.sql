CREATE TABLE IF NOT EXISTS `delay_instance`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `app_id`       bigint(20) unsigned NOT NULL,
    `namespace_id` bigint(20) unsigned NOT NULL,
    `task_id`      varchar(64)  NOT NULL DEFAULT '',
    `topic`        varchar(128) NOT NULL DEFAULT '',
    `delay_id`     bigint(20) unsigned NOT NULL,
    `delay_params` longtext     NOT NULL,
    `delay_extra`  text         NOT NULL,
    `status`       tinyint(2) NOT NULL,
    `slots_id`     bigint(20) NOT NULL,
    `execute_time` bigint(12) NOT NULL,
    `deleted`      tinyint(12) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
    `create_time`  bigint(12) NOT NULL,
    `create_time_date` int(11) NOT NULL DEFAULT '0' COMMENT 'Time date`20230527`',
    `create_time_hour` int(11) NOT NULL DEFAULT '0' COMMENT 'Time hour`2023052701`',
    `update_time`  bigint(12) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `udx_task_id` (`task_id`)
);