# `alert_rule`
# ------------------------------------------------------------
CREATE TABLE `alert_rule`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `name`              varchar(32)  NOT NULL DEFAULT '' COMMENT 'Rule name',
    `namespace_app_ids` text COMMENT 'Namespace and applicatioin ids',
    `events`            text COMMENT 'Events',
    `metrics`           text COMMENT 'Metrics',
    `method`            varchar(32)  NOT NULL DEFAULT '' COMMENT 'Alert method feishu/dingding/wecom/webhook',
    `url`               varchar(128) NOT NULL DEFAULT '' COMMENT 'Alert url',
    `status`            tinyint(1) NOT NULL COMMENT 'Rule status 1=on 2=off',
    `deleted`           tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
    `delete_time`       bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
    `create_time`       bigint(12) unsigned NOT NULL COMMENT 'Create time',
    `update_time`       bigint(12) unsigned NOT NULL COMMENT 'Update time',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# `job_instance`
# ------------------------------------------------------------
ALTER TABLE `job_instance` ADD `fail_status` TINYINT(2)  NOT NULL  DEFAULT '1'  COMMENT 'Fail status'  AFTER `status`;


# `delay_instance`
# ------------------------------------------------------------
ALTER TABLE `delay_instance` ADD `fail_status` TINYINT(2)  NOT NULL  DEFAULT '1'  COMMENT 'Delay fail status'  AFTER `status`;