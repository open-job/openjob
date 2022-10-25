CREATE TABLE IF NOT EXISTS `job_instance`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `job_id`           bigint(20)          NOT NULL,
    `job_params`       varchar(3096)       NOT NULL,
    `status`           tinyint(11)         NOT NULL DEFAULT '1',
    `slots_id`         bigint(20)          NOT NULL,
    `namespace_id`     bigint(20)          NOT NULL,
    `app_id`           bigint(20)          NOT NULL,
    `execute_time`     int(11)             NOT NULL,
    `complete_time`    int(11)             NOT NULL DEFAULT '0',
    `last_report_time` int(11)             NOT NULL DEFAULT '0',
    `update_time`      int(11)             NOT NULL,
    `create_time`      int(11)             NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 81
  DEFAULT CHARSET = utf8mb4;