CREATE TABLE IF NOT EXISTS `worker`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `app_id`              bigint(20)          NOT NULL,
    `namespace_id`        bigint(20)          NOT NULL,
    `app_name`            varchar(128)        NOT NULL DEFAULT '',
    `worker_key`          varchar(64)         NOT NULL DEFAULT '',
    `address`             varchar(32)         NOT NULL DEFAULT '',
    `protocol_type`       varchar(8)          NOT NULL DEFAULT '',
    `version`             varchar(32)         NOT NULL DEFAULT '',
    `last_heartbeat_time` int(11)             NOT NULL,
    `status`              tinyint(2)          NOT NULL DEFAULT '1',
    `metric`              varchar(1024)       NOT NULL DEFAULT '',
    `create_time`         int(11)             NOT NULL,
    `update_time`         int(11)             NOT NULL,
    PRIMARY KEY (`id`)
);