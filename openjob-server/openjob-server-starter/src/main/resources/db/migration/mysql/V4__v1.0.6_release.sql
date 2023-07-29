#`alert_rule`
# ------------------------------------------------------------
CREATE TABLE `alert_rule`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `name`              varchar(32)  NOT NULL DEFAULT '' COMMENT 'Rule name',
    `namespace_app_ids` text COMMENT 'Namespace and applicatioin ids',
    `events`            text COMMENT 'Events',
    `metrics`           text COMMENT 'Metrics',
    `locale`            varchar(16) DEFAULT '' COMMENT 'Alert Locale(zh_CN/en_US)',
    `method`            varchar(32)  NOT NULL DEFAULT '' COMMENT 'Alert method feishu/dingding/wecom/webhook',
    `url`               varchar(256) NOT NULL DEFAULT '' COMMENT 'Alert url',
    `secret`            varchar(256) NOT NULL DEFAULT '' COMMENT 'Alert secret',
    `status`            tinyint(1) NOT NULL COMMENT 'Rule status 1=on 2=off',
    `deleted`           tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
    `delete_time`       bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
    `create_time`       bigint(12) unsigned NOT NULL COMMENT 'Create time',
    `update_time`       bigint(12) unsigned NOT NULL COMMENT 'Update time',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#`admin_permission`
# ------------------------------------------------------------
INSERT INTO `admin_permission` (`id`, `pid`, `type`, `name`, `path`, `meta`, `hidden`, `sort`, `deleted`, `delete_time`, `update_time`, `create_time`)
VALUES  (106, 0, 1, 'userProfile', '/personal', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.userProfile", "isLink": "", "isAffix": false, "isIframe": false, "component": "/personal/index", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
        (170, 0, 1, 'Alert', '/e', '{"icon": "ele-Setting", "roles": ["admin"], "title": "message.router.alert", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (171, 170, 1, 'AlertRule', '/admin/alert/list', '{"icon": "ele-Connection", "roles": ["admin"], "title": "message.router.alertRule", "isLink": "", "isAffix": false, "isIframe": false, "component": "/alert/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);

#`admin_user`
# ------------------------------------------------------------
ALTER TABLE `admin_user`
    ADD `login_time` BIGINT(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Login time'  AFTER `role_ids`;
ALTER TABLE `admin_user`
    ADD `login_ip` VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'Login IP'  AFTER `role_ids`;

#`job`
# ------------------------------------------------------------
ALTER TABLE `job`
    ADD `execute_timeout` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Execute timeout'  AFTER `concurrency`;

#`job_instance`
# ------------------------------------------------------------
ALTER TABLE `job_instance`
    ADD `fail_status` TINYINT(2)  NOT NULL  DEFAULT '0'  COMMENT 'Fail status'  AFTER `status`;
ALTER TABLE `job_instance`
    ADD `execute_timeout` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Execute timeout'  AFTER `concurrency`;

#`delay_instance`
# ------------------------------------------------------------
ALTER TABLE `delay_instance`
    ADD `fail_status` TINYINT(2)  NOT NULL  DEFAULT '0'  COMMENT 'Delay fail status'  AFTER `status`;