CREATE TABLE IF NOT EXISTS `job`
(
    `id`                   bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `namespace_id`         bigint(20)          NOT NULL,
    `app_id`               bigint(20)          NOT NULL,
    `workflow_id`          bigint(20)          NOT NULL DEFAULT '0',
    `name`                 varchar(32)         NOT NULL DEFAULT '',
    `description`          varchar(128)        NOT NULL DEFAULT '',
    `processor_type`       varchar(16)         NOT NULL DEFAULT 'java' COMMENT 'java /shell/python',
    `processor_info`       varchar(128)        NOT NULL DEFAULT '',
    `execute_type`         varchar(16)         NOT NULL DEFAULT 'standalone' COMMENT 'execute type 1=standalone 2=broadcast 3=MR',
    `params`               varchar(3096)       NOT NULL DEFAULT '',
    `fail_retry_times`     int(11)             NOT NULL,
    `fail_retry_interval`  int(11)             NOT NULL,
    `concurrency`          int(11)             NOT NULL DEFAULT '1',
    `time_expression_type` varchar(16)         NOT NULL DEFAULT 'cron' COMMENT 'cron/second/delay',
    `time_expression`      varchar(32)         NOT NULL DEFAULT '' COMMENT 'Cron express type',
    `status`               tinyint(2)          NOT NULL DEFAULT '1' COMMENT '1=running 2=stop',
    `next_execute_time`    int(11)             NOT NULL,
    `slots_id`             int(11)             NOT NULL,
    `create_time`          int(11)             NOT NULL,
    `update_time`          int(11)             NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = utf8mb4;