CREATE TABLE `server_fail_reports`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `server_id`        bigint(20)          NOT NULL,
    `report_server_id` bigint(20)          NOT NULL,
    `create_time`      int(11)             NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_create_time_server_id` (`create_time`, `server_id`)
);