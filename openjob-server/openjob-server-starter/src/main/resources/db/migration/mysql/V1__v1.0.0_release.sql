# ************************************************************
# Openjob SQL dump
# Version 1.0.0
#
# http://www.openjob.io/
# https://github.com/open-job/openjob
#
# Host: 127.0.0.1 (MySQL)
# Database: openjob
# Generation Time: 2023-05-05 12:51:40 +0000
# ************************************************************

# Dump of table admin_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_permission`;

CREATE TABLE `admin_permission` (
                                    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                    `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Parent ID',
                                    `type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Type. 1=menu 2=perm',
                                    `name` varchar(48) NOT NULL DEFAULT '' COMMENT 'Menu name',
                                    `path` varchar(86) NOT NULL DEFAULT '' COMMENT 'Route path or API path',
                                    `meta` json DEFAULT NULL COMMENT 'Extra meta data. JSON object: {icon:xx,title:some.name}',
                                    `hidden` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Hidden status. 1=yes 2=no',
                                    `sort` int(10) NOT NULL DEFAULT '0' COMMENT 'Sort value',
                                    `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                    `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                    `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                                    `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_pid` (`pid`),
                                    KEY `idx_path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Admin user permissions';

insert into admin_permission (id, pid, type, name, path, meta, hidden, sort, deleted, delete_time, update_time, create_time)
values  (1, 0, 1, 'dashboard', '/dashboard', '{"icon": "iconfont icon-shouye", "roles": ["admin"], "title": "message.router.dashboard", "isLink": "", "isAffix": false, "isIframe": false, "component": "/home/index.vue", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (20, 0, 1, 'namespace', '/admin/namespace/list', '{"icon": "ele-Edit", "roles": ["admin"], "title": "message.router.namespace", "isLink": "", "isAffix": false, "isIframe": false, "component": "/namespace/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (40, 0, 1, 'application', '/admin/app/list', '{"icon": "ele-Operation", "roles": ["admin"], "title": "message.router.application", "isLink": "", "isAffix": false, "isIframe": false, "component": "/app/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (60, 0, 1, 'cronJob', '/a', '{"icon": "ele-AlarmClock", "roles": ["admin"], "title": "message.router.cronJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (61, 60, 1, 'cronJobJob', '/admin/job/list', '{"icon": "ele-Suitcase", "roles": ["admin"], "title": "message.router.cronJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (62, 60, 1, 'cronJobInstance', '/admin/job-instance/list', '{"icon": "ele-Document", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (63, 60, 1, 'cronJobPage', '/admin/job/page', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/page", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
        (80, 0, 1, 'delayJob', '/b', '{"icon": "ele-Clock", "roles": ["admin"], "title": "message.router.delayJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (81, 80, 1, 'delayJobDelay', '/admin/delay/list', '{"icon": "ele-SetUp", "roles": ["admin"], "title": "message.router.delayJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (82, 80, 1, 'delayJobInstance', '/admin/delay-instance/list', '{"icon": "ele-DocumentChecked", "roles": ["admin"], "title": "message.router.delayJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (200, 0, 1, 'cluster', '/c', '{"icon": "ele-SuitcaseLine", "roles": ["admin"], "title": "message.router.clusterManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (201, 200, 1, 'ClusterNode', '/admin/cluster-node/list', '{"icon": "ele-Notification", "roles": ["admin"], "title": "message.router.clusterNode", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/node/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (202, 200, 1, 'ClusterWorker', '/admin/cluster-worker/list', '{"icon": "ele-Monitor", "roles": ["admin"], "title": "message.router.clusterWorker", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/worker/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (300, 0, 1, 'system', '/d', '{"icon": "ele-Setting", "roles": ["admin"], "title": "message.router.systemManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (301, 300, 1, 'SystemConfiguration', '/admin/system-config/list', '{"icon": "ele-CreditCard", "roles": ["admin"], "title": "message.router.systemConfiguration", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/config/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (302, 300, 1, 'SystemSlots', '/admin/system-slots/list', '{"icon": "ele-Connection", "roles": ["admin"], "title": "message.router.systemSlots", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/slots/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1000, 0, 2, 'namespace.add', '/admin/namespace/add', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1001, 0, 2, 'namespace.delete', '/admin/namespace/delete', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1002, 0, 2, 'namespace.update', '/admin/namespace/update', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1003, 0, 2, 'namespace.update.status', '/admin/namespace/update-status', '{}', 2, 0, 2, 0, 1669972320, 1669972320);

# Dump of table admin_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_role`;

CREATE TABLE `admin_role` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `name` varchar(48) NOT NULL DEFAULT '' COMMENT 'role name',
                              `desc` varchar(128) NOT NULL DEFAULT '' COMMENT 'Description',
                              `admin` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Is supper admin. 1=yes 2=no',
                              `menu_ids` json DEFAULT NULL COMMENT 'Menu ids for role. JSON array',
                              `perm_ids` json DEFAULT NULL COMMENT 'Permission ids for role. JSON array',
                              `namespace_ids` json DEFAULT NULL COMMENT 'namespaces Ids. JSON array',
                              `app_ids` json DEFAULT NULL COMMENT 'app ids. JSON array',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              PRIMARY KEY (`id`),
                              KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Admin roles with perms';

INSERT INTO `admin_role` (`id`, `name`, `desc`, `admin`, `menu_ids`, `perm_ids`, `namespace_ids`, `app_ids`, `deleted`, `delete_time`, `update_time`, `create_time`)
insert into admin_role (id, name, desc, admin, menu_ids, perm_ids, namespace_ids, app_ids, deleted, delete_time, update_time, create_time)
values  (1, 'Admin', 'Administrator role', 1, '[1, 5]', '[11, 12, 13, 14, 15]', '[]', '[]', 2, 0, 1670255999, 1670255999),
    (2, 'Developer', 'Developer role', 2, '[1, 5]', '[11, 12, 13, 14, 15]', '[]', '[]', 2, 0, 1670255999, 1670255999);

/*!40000 ALTER TABLE `admin_role` ENABLE KEYS */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `username` varchar(48) NOT NULL DEFAULT '' COMMENT 'User name',
                              `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT 'Nickname',
                              `passwd` varchar(128) NOT NULL DEFAULT '' COMMENT 'Password',
                              `session_key` varchar(128) NOT NULL DEFAULT '' COMMENT 'Session key',
                              `session_expire_at` bigint(12) NOT NULL COMMENT 'Session expire at',
                              `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'Api auth token',
                              `role_ids` json DEFAULT NULL COMMENT 'role IDs. JSON: [1,2]',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uni_token` (`token`),
                              KEY `idx_name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Job admin users';

insert into admin_user (id, username, nickname, passwd, session_key, session_expire_at, token, role_ids, deleted, delete_time, update_time, create_time)
values  (1, 'admin', 'Administrator', 'c881f5068a2d066023dfd404d9a75e4f1708a9df6dc9c451900fc72d986f7ba9', '79f74383e2c92ae01e172ced4c9267d5', 0, '79f74383e2c92ae01e172ced4c9267d5', '[1]', 2, 0, 1670255999, 1670255999),
        (2, 'openjob', 'OpenJob User', 'c0d4247cd38f62f975ba32c9f1e58926f6a99c223f642524c53917810c95d39b', '2cebdf15d414b6713672475a21f995a0', 0, '2cebdf15d414b6713672475a21f995a0', '[2]', 2, 0, 1670255999, 1670255999);

# Dump of table app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                       `namespace_id` bigint(20) NOT NULL COMMENT 'Namespace id',
                       `name` varchar(64) DEFAULT NULL COMMENT 'Name',
                       `desc` varchar(256) NOT NULL DEFAULT '' COMMENT 'Description',
                       `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                       `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                       `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                       `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `udx_name` (`name`),
                       KEY `idx_namespace_id_name` (`namespace_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `app` (`id`, `namespace_id`, `name`, `desc`, `deleted`, `delete_time`, `create_time`, `update_time`)
VALUES
    (1,1,'openjob','openjob',2,0,1658473199,1678796017);


# Dump of table delay
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay`;

CREATE TABLE `delay` (
                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                         `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Parent id',
                         `cid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Child id',
                         `namespace_id` bigint(20) NOT NULL COMMENT 'Namespace',
                         `app_id` bigint(20) NOT NULL COMMENT 'Application',
                         `name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Name',
                         `description` varchar(256) NOT NULL DEFAULT '' COMMENT 'Description',
                         `processor_info` varchar(256) NOT NULL DEFAULT '' COMMENT 'Processor info',
                         `fail_retry_times` int(11) unsigned NOT NULL DEFAULT '3' COMMENT 'Fail retry times',
                         `fail_retry_interval` int(11) unsigned NOT NULL DEFAULT '3' COMMENT 'Fail retry interval(s)',
                         `execute_timeout` int(11) unsigned NOT NULL DEFAULT '60' COMMENT 'Execute timeout(s)',
                         `concurrency` int(11) unsigned NOT NULL DEFAULT '1' COMMENT 'Execute concurrency',
                         `blocking_size` int(11) unsigned NOT NULL DEFAULT '8' COMMENT 'Pull blocking size',
                         `topic` varchar(128) NOT NULL DEFAULT '' COMMENT 'Topic',
                         `fail_topic_enable` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'Fail topic eanble status',
                         `fail_topic_concurrency` int(11) NOT NULL DEFAULT '1' COMMENT 'Fail topic execute concurrency',
                         `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                         `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                         `create_time` bigint(12) NOT NULL COMMENT 'Create time',
                         `update_time` bigint(12) NOT NULL COMMENT 'Update time',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `udx_topic` (`topic`),
                         KEY `idx_namespace_id_name` (`namespace_id`,`name`),
                         KEY `idx_namespace_id_topic` (`namespace_id`,`topic`),
                         KEY `idx_namespace_id_app_id` (`namespace_id`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table delay_instance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay_instance`;

CREATE TABLE `delay_instance` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                  `app_id` bigint(20) unsigned NOT NULL COMMENT 'Application',
                                  `namespace_id` bigint(20) unsigned NOT NULL COMMENT 'Namespace',
                                  `task_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'Task id',
                                  `topic` varchar(128) NOT NULL DEFAULT '' COMMENT 'Topic',
                                  `delay_id` bigint(20) unsigned NOT NULL COMMENT 'Delay id',
                                  `delay_params` longtext NOT NULL COMMENT 'Delay params',
                                  `delay_extra` text NOT NULL COMMENT 'Delay extra',
                                  `status` tinyint(2) NOT NULL COMMENT 'Delay task status',
                                  `execute_time` bigint(12) NOT NULL COMMENT 'Execute time',
                                  `complete_time` bigint(12) NOT NULL DEFAULT '0' COMMENT 'Complete time',
                                  `worker_address` varchar(32) NOT NULL DEFAULT '' COMMENT 'Worker address',
                                  `deleted` tinyint(12) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                  `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                  `create_time` bigint(12) NOT NULL COMMENT 'Create time',
                                  `update_time` bigint(12) NOT NULL COMMENT 'Update time',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `udx_task_id` (`task_id`),
                                  KEY `idx_namespace_id_create_time` (`namespace_id`,`create_time`),
                                  KEY `idx_namespace_id_app_id_delay_id_create_time` (`namespace_id`,`app_id`,`delay_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table delay_worker
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay_worker`;

CREATE TABLE `delay_worker` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                `topic` varchar(128) NOT NULL DEFAULT '' COMMENT 'Topic',
                                `pull_size` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Pull size',
                                `pull_time` bigint(16) unsigned NOT NULL DEFAULT '0' COMMENT 'Pull time',
                                `create_time` bigint(12) NOT NULL COMMENT 'Create time',
                                `update_time` bigint(12) NOT NULL COMMENT 'Update time',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                       `namespace_id` bigint(20) unsigned NOT NULL COMMENT 'Namespace',
                       `app_id` bigint(20) unsigned NOT NULL COMMENT 'Application',
                       `workflow_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Workflow id',
                       `name` varchar(32) NOT NULL DEFAULT '' COMMENT 'Name',
                       `description` varchar(128) NOT NULL DEFAULT '' COMMENT 'Description',
                       `processor_type` varchar(16) NOT NULL DEFAULT 'java' COMMENT 'Processor type java /shell/python',
                       `processor_info` text NOT NULL COMMENT 'Processor info',
                       `execute_type` varchar(16) NOT NULL DEFAULT 'standalone' COMMENT 'Execute type 1=standalone 2=broadcast 3=MR',
                       `params` varchar(3096) NOT NULL DEFAULT '' COMMENT 'Params',
                       `params_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Params type text/json/yaml',
                       `extend_params_type` varchar(16) DEFAULT NULL COMMENT 'Extend params type text/json/yaml',
                       `extend_params` varchar(3096) NOT NULL DEFAULT '' COMMENT 'Extend params',
                       `fail_retry_times` int(11) unsigned NOT NULL COMMENT 'Fail retry times',
                       `fail_retry_interval` int(11) unsigned NOT NULL COMMENT 'Fail retry interval(s)',
                       `concurrency` int(11) unsigned NOT NULL DEFAULT '1' COMMENT 'Execute concurrency',
                       `time_expression_type` varchar(16) NOT NULL DEFAULT 'cron' COMMENT 'Time express time cron/second/delay',
                       `time_expression` varchar(32) NOT NULL DEFAULT '' COMMENT 'Cron express type',
                       `execute_strategy` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
                       `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1=running 2=stop',
                       `next_execute_time` bigint(12) unsigned NOT NULL COMMENT 'Next execute time',
                       `slots_id` int(11) unsigned NOT NULL COMMENT 'Slots id',
                       `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                       `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                       `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                       `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                       PRIMARY KEY (`id`),
                       KEY `idx_namespace_id_name` (`namespace_id`,`name`),
                       KEY `idx_namespace_id_app_id_name` (`namespace_id`,`app_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job_instance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance`;

CREATE TABLE `job_instance` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                `job_id` bigint(20) unsigned NOT NULL COMMENT 'Job id',
                                `params` varchar(3096) NOT NULL DEFAULT '' COMMENT 'Params',
                                `params_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Params type text/json/yaml',
                                `extend_params_type` varchar(16) DEFAULT NULL COMMENT 'Extend params type text/json/yaml',
                                `extend_params` varchar(3096) NOT NULL DEFAULT '' COMMENT 'Extend params',
                                `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Instance status',
                                `slots_id` bigint(20) unsigned NOT NULL COMMENT 'Slots id',
                                `workflow_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Workflow id',
                                `namespace_id` bigint(20) unsigned NOT NULL COMMENT 'Namespace',
                                `app_id` bigint(20) unsigned NOT NULL COMMENT 'Application',
                                `execute_time` bigint(12) unsigned NOT NULL COMMENT 'Execute time',
                                `complete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Complete time',
                                `last_report_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Last report time',
                                `processor_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Processor type',
                                `processor_info` text NOT NULL COMMENT 'Processor info',
                                `execute_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Execute time',
                                `fail_retry_times` int(11) unsigned NOT NULL COMMENT 'Fail retry times',
                                `fail_retry_interval` int(11) unsigned NOT NULL COMMENT 'Fail retry interval(s)',
                                `time_expression_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Time expression type',
                                `time_expression` varchar(32) NOT NULL DEFAULT '' COMMENT 'TIme expression',
                                `concurrency` int(11) unsigned NOT NULL DEFAULT '1' COMMENT 'Concurrency',
                                `worker_address` varchar(32) NOT NULL DEFAULT '' COMMENT 'Worker address',
                                `execute_strategy` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
                                `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                `update_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                                `create_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                                PRIMARY KEY (`id`),
                                KEY `idx_execute_time_slots_id` (`execute_time`,`slots_id`),
                                KEY `idx_last_report_time_slots_id` (`last_report_time`,`slots_id`),
                                KEY `idx_job_id_status` (`job_id`,`status`),
                                KEY `idx_namespace_id_create_time` (`namespace_id`,`create_time`),
                                KEY `idx_namespace_id_app_id_job_id_create_time` (`namespace_id`,`app_id`,`job_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job_instance_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance_log`;

CREATE TABLE `job_instance_log` (
                                    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                    `job_id` bigint(20) unsigned NOT NULL COMMENT 'Job id',
                                    `job_instance_id` bigint(20) unsigned NOT NULL COMMENT 'Job instance id',
                                    `message` longtext COMMENT 'Message',
                                    `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                    `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                    `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                                    `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_job_instance_id_create_time` (`job_instance_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job_instance_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance_task`;

CREATE TABLE `job_instance_task` (
                                     `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                     `job_id` bigint(20) unsigned NOT NULL COMMENT 'Job id',
                                     `job_instance_id` bigint(20) unsigned NOT NULL COMMENT 'Instance id',
                                     `circle_id` bigint(20) unsigned NOT NULL COMMENT 'Circle id',
                                     `task_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'Task id',
                                     `parent_task_id` varchar(64) NOT NULL DEFAULT '0' COMMENT 'Parent task id',
                                     `task_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Task name',
                                     `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Instance task status',
                                     `result` longtext COMMENT 'Task result',
                                     `worker_address` varchar(128) NOT NULL DEFAULT '' COMMENT 'Worker address',
                                     `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                     `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                     `create_time` bigint(12) unsigned DEFAULT NULL COMMENT 'Create time',
                                     `update_time` bigint(12) unsigned DEFAULT NULL COMMENT 'Update time',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `udx_task_id` (`task_id`),
                                     KEY `idx_job_instance_id_create_time` (`job_instance_id`,`create_time`),
                                     KEY `idx_parent_task_id` (`parent_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job_slots
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_slots`;

CREATE TABLE `job_slots` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                             `server_id` bigint(20) unsigned NOT NULL COMMENT 'Server id',
                             `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                             `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                             `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                             `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`),
                             KEY `idx_server_id` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `job_slots` (`id`, `server_id`, `deleted`, `delete_time`, `create_time`, `update_time`)
VALUES
    (1,1,2,0,1655781998,1683287431),
    (2,1,2,0,1655781999,1683287431),
    (3,1,2,0,1655781999,1683287431),
    (4,1,2,0,1655781999,1683287431),
    (5,1,2,0,1655781999,1683287431),
    (6,1,2,0,1655781999,1683287431),
    (7,1,2,0,1655781999,1683287431),
    (8,1,2,0,1655781999,1683287431),
    (9,1,2,0,1655781999,1683287431),
    (10,1,2,0,1655781999,1683287431),
    (11,1,2,0,1655781999,1683287431),
    (12,1,2,0,1655781999,1683287431),
    (13,1,2,0,1655781999,1683287431),
    (14,1,2,0,1655781999,1683287431),
    (15,1,2,0,1655781999,1683287431),
    (16,1,2,0,1655781999,1683287431),
    (17,1,2,0,1655781999,1683287431),
    (18,1,2,0,1655781999,1683287431),
    (19,1,2,0,1655781999,1683287431),
    (20,1,2,0,1655781999,1683287431),
    (21,1,2,0,1655781999,1683287431),
    (22,1,2,0,1655781999,1683287431),
    (23,1,2,0,1655781999,1683287431),
    (24,1,2,0,1655781999,1683287431),
    (25,1,2,0,1655781999,1683287431),
    (26,1,2,0,1655781999,1683287431),
    (27,1,2,0,1655781999,1683287431),
    (28,1,2,0,1655781999,1683287431),
    (29,1,2,0,1655781999,1683287431),
    (30,1,2,0,1655781999,1683287431),
    (31,1,2,0,1655781999,1683287431),
    (32,1,2,0,1655781999,1683287431),
    (33,1,2,0,1655781999,1683287431),
    (34,1,2,0,1655781999,1683287431),
    (35,1,2,0,1655781999,1683287431),
    (36,1,2,0,1655781999,1683287431),
    (37,1,2,0,1655781999,1683287431),
    (38,1,2,0,1655781999,1683287431),
    (39,1,2,0,1655781999,1683287431),
    (40,1,2,0,1655781999,1683287431),
    (41,1,2,0,1655781999,1683287431),
    (42,1,2,0,1655781999,1683287431),
    (43,1,2,0,1655781999,1683287431),
    (44,1,2,0,1655781999,1683287431),
    (45,1,2,0,1655781999,1683287431),
    (46,1,2,0,1655781999,1683287431),
    (47,1,2,0,1655781999,1683287431),
    (48,1,2,0,1655781999,1683287431),
    (49,1,2,0,1655781999,1683287431),
    (50,1,2,0,1655781999,1683287431),
    (51,1,2,0,1655781999,1683287431),
    (52,1,2,0,1655781999,1683287431),
    (53,1,2,0,1655781999,1683287431),
    (54,1,2,0,1655781999,1683287431),
    (55,1,2,0,1655781999,1683287431),
    (56,1,2,0,1655781999,1683287431),
    (57,1,2,0,1655781999,1683287431),
    (58,1,2,0,1655781999,1683287431),
    (59,1,2,0,1655781999,1683287431),
    (60,1,2,0,1655781999,1683287431),
    (61,1,2,0,1655781999,1683287431),
    (62,1,2,0,1655781999,1683287431),
    (63,1,2,0,1655781999,1683287431),
    (64,1,2,0,1655781999,1683287431),
    (65,1,2,0,1655781999,1683287431),
    (66,1,2,0,1655781999,1683287431),
    (67,1,2,0,1655781999,1683287431),
    (68,1,2,0,1655781999,1683287431),
    (69,1,2,0,1655781999,1683287431),
    (70,1,2,0,1655781999,1683287431),
    (71,1,2,0,1655781999,1683287431),
    (72,1,2,0,1655781999,1683287431),
    (73,1,2,0,1655781999,1683287431),
    (74,1,2,0,1655781999,1683287431),
    (75,1,2,0,1655781999,1683287431),
    (76,1,2,0,1655781999,1683287431),
    (77,1,2,0,1655781999,1683287431),
    (78,1,2,0,1655781999,1683287431),
    (79,1,2,0,1655781999,1683287431),
    (80,1,2,0,1655781999,1683287431),
    (81,1,2,0,1655781999,1683287431),
    (82,1,2,0,1655781999,1683287431),
    (83,1,2,0,1655781999,1683287431),
    (84,1,2,0,1655781999,1683287431),
    (85,1,2,0,1655781999,1683287431),
    (86,1,2,0,1655781999,1683287431),
    (87,1,2,0,1655781999,1683287431),
    (88,1,2,0,1655781999,1683287431),
    (89,1,2,0,1655781999,1683287431),
    (90,1,2,0,1655781999,1683287431),
    (91,1,2,0,1655781999,1683287431),
    (92,1,2,0,1655781999,1683287431),
    (93,1,2,0,1655781999,1683287431),
    (94,1,2,0,1655781999,1683287431),
    (95,1,2,0,1655781999,1683287431),
    (96,1,2,0,1655781999,1683287431),
    (97,1,2,0,1655781999,1683287431),
    (98,1,2,0,1655781999,1683287431),
    (99,1,2,0,1655781999,1683287431),
    (100,1,2,0,1655781999,1683287431),
    (101,1,2,0,1655781999,1683287431),
    (102,1,2,0,1655781999,1683287431),
    (103,1,2,0,1655781999,1683287431),
    (104,1,2,0,1655781999,1683287431),
    (105,1,2,0,1655781999,1683287431),
    (106,1,2,0,1655781999,1683287431),
    (107,1,2,0,1655781999,1683287431),
    (108,1,2,0,1655781999,1683287431),
    (109,1,2,0,1655781999,1683287431),
    (110,1,2,0,1655781999,1683287431),
    (111,1,2,0,1655781999,1683287431),
    (112,1,2,0,1655781999,1683287431),
    (113,1,2,0,1655781999,1683287431),
    (114,1,2,0,1655781999,1683287431),
    (115,1,2,0,1655781999,1683287431),
    (116,1,2,0,1655781999,1683287431),
    (117,1,2,0,1655781999,1683287431),
    (118,1,2,0,1655781999,1683287431),
    (119,1,2,0,1655781999,1683287431),
    (120,1,2,0,1655781999,1683287431),
    (121,1,2,0,1655781999,1683287431),
    (122,1,2,0,1655781999,1683287431),
    (123,1,2,0,1655781999,1683287431),
    (124,1,2,0,1655781999,1683287431),
    (125,1,2,0,1655781999,1683287431),
    (126,1,2,0,1655781999,1683287431),
    (127,1,2,0,1655781999,1683287431),
    (128,1,2,0,1655781999,1683287431),
    (129,1,2,0,1655781999,1683287431),
    (130,1,2,0,1655781999,1683287431),
    (131,1,2,0,1655781999,1683287431),
    (132,1,2,0,1655781999,1683287431),
    (133,1,2,0,1655781999,1683287431),
    (134,1,2,0,1655781999,1683287431),
    (135,1,2,0,1655781999,1683287431),
    (136,1,2,0,1655782000,1683287431),
    (137,1,2,0,1655782000,1683287431),
    (138,1,2,0,1655782000,1683287431),
    (139,1,2,0,1655782000,1683287431),
    (140,1,2,0,1655782000,1683287431),
    (141,1,2,0,1655782000,1683287431),
    (142,1,2,0,1655782000,1683287431),
    (143,1,2,0,1655782000,1683287431),
    (144,1,2,0,1655782000,1683287431),
    (145,1,2,0,1655782000,1683287431),
    (146,1,2,0,1655782000,1683287431),
    (147,1,2,0,1655782000,1683287431),
    (148,1,2,0,1655782000,1683287431),
    (149,1,2,0,1655782000,1683287431),
    (150,1,2,0,1655782000,1683287431),
    (151,1,2,0,1655782000,1683287431),
    (152,1,2,0,1655782000,1683287431),
    (153,1,2,0,1655782000,1683287431),
    (154,1,2,0,1655782000,1683287431),
    (155,1,2,0,1655782000,1683287431),
    (156,1,2,0,1655782000,1683287431),
    (157,1,2,0,1655782000,1683287431),
    (158,1,2,0,1655782000,1683287431),
    (159,1,2,0,1655782000,1683287431),
    (160,1,2,0,1655782000,1683287431),
    (161,1,2,0,1655782000,1683287431),
    (162,1,2,0,1655782000,1683287431),
    (163,1,2,0,1655782000,1683287431),
    (164,1,2,0,1655782000,1683287431),
    (165,1,2,0,1655782000,1683287431),
    (166,1,2,0,1655782000,1683287431),
    (167,1,2,0,1655782000,1683287431),
    (168,1,2,0,1655782000,1683287431),
    (169,1,2,0,1655782000,1683287431),
    (170,1,2,0,1655782000,1683287431),
    (171,1,2,0,1655782000,1683287431),
    (172,1,2,0,1655782000,1683287431),
    (173,1,2,0,1655782000,1683287431),
    (174,1,2,0,1655782000,1683287431),
    (175,1,2,0,1655782000,1683287431),
    (176,1,2,0,1655782000,1683287431),
    (177,1,2,0,1655782000,1683287431),
    (178,1,2,0,1655782000,1683287431),
    (179,1,2,0,1655782000,1683287431),
    (180,1,2,0,1655782000,1683287431),
    (181,1,2,0,1655782000,1683287431),
    (182,1,2,0,1655782000,1683287431),
    (183,1,2,0,1655782000,1683287431),
    (184,1,2,0,1655782000,1683287431),
    (185,1,2,0,1655782000,1683287431),
    (186,1,2,0,1655782000,1683287431),
    (187,1,2,0,1655782000,1683287431),
    (188,1,2,0,1655782000,1683287431),
    (189,1,2,0,1655782000,1683287431),
    (190,1,2,0,1655782000,1683287431),
    (191,1,2,0,1655782000,1683287431),
    (192,1,2,0,1655782000,1683287431),
    (193,1,2,0,1655782000,1683287431),
    (194,1,2,0,1655782000,1683287431),
    (195,1,2,0,1655782000,1683287431),
    (196,1,2,0,1655782000,1683287431),
    (197,1,2,0,1655782000,1683287431),
    (198,1,2,0,1655782000,1683287431),
    (199,1,2,0,1655782000,1683287431),
    (200,1,2,0,1655782000,1683287431),
    (201,1,2,0,1655782000,1683287431),
    (202,1,2,0,1655782000,1683287431),
    (203,1,2,0,1655782000,1683287431),
    (204,1,2,0,1655782000,1683287431),
    (205,1,2,0,1655782000,1683287431),
    (206,1,2,0,1655782000,1683287431),
    (207,1,2,0,1655782000,1683287431),
    (208,1,2,0,1655782000,1683287431),
    (209,1,2,0,1655782000,1683287431),
    (210,1,2,0,1655782000,1683287431),
    (211,1,2,0,1655782000,1683287431),
    (212,1,2,0,1655782000,1683287431),
    (213,1,2,0,1655782000,1683287431),
    (214,1,2,0,1655782000,1683287431),
    (215,1,2,0,1655782000,1683287431),
    (216,1,2,0,1655782000,1683287431),
    (217,1,2,0,1655782000,1683287431),
    (218,1,2,0,1655782000,1683287431),
    (219,1,2,0,1655782000,1683287431),
    (220,1,2,0,1655782000,1683287431),
    (221,1,2,0,1655782000,1683287431),
    (222,1,2,0,1655782000,1683287431),
    (223,1,2,0,1655782000,1683287431),
    (224,1,2,0,1655782000,1683287431),
    (225,1,2,0,1655782000,1683287431),
    (226,1,2,0,1655782000,1683287431),
    (227,1,2,0,1655782000,1683287431),
    (228,1,2,0,1655782000,1683287431),
    (229,1,2,0,1655782000,1683287431),
    (230,1,2,0,1655782000,1683287431),
    (231,1,2,0,1655782000,1683287431),
    (232,1,2,0,1655782000,1683287431),
    (233,1,2,0,1655782000,1683287431),
    (234,1,2,0,1655782000,1683287431),
    (235,1,2,0,1655782000,1683287431),
    (236,1,2,0,1655782000,1683287431),
    (237,1,2,0,1655782000,1683287431),
    (238,1,2,0,1655782000,1683287431),
    (239,1,2,0,1655782000,1683287431),
    (240,1,2,0,1655782000,1683287431),
    (241,1,2,0,1655782000,1683287431),
    (242,1,2,0,1655782000,1683287431),
    (243,1,2,0,1655782000,1683287431),
    (244,1,2,0,1655782000,1683287431),
    (245,1,2,0,1655782000,1683287431),
    (246,1,2,0,1655782000,1683287431),
    (247,1,2,0,1655782000,1683287431),
    (248,1,2,0,1655782000,1683287431),
    (249,1,2,0,1655782000,1683287431),
    (250,1,2,0,1655782000,1683287431),
    (251,1,2,0,1655782000,1683287431),
    (252,1,2,0,1655782000,1683287431),
    (253,1,2,0,1655782000,1683287431),
    (254,1,2,0,1655782000,1683287431),
    (255,1,2,0,1655782000,1683287431),
    (256,1,2,0,1655782000,1683287431);


# Dump of table namespace
# ------------------------------------------------------------

DROP TABLE IF EXISTS `namespace`;

CREATE TABLE `namespace` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                             `name` varchar(64) NOT NULL DEFAULT '' COMMENT 'Name',
                             `uuid` varchar(64) NOT NULL DEFAULT '' COMMENT 'Unique id',
                             `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                             `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                             `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                             `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`),
                             KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `namespace` (`id`, `name`, `uuid`, `deleted`, `delete_time`, `create_time`, `update_time`)
VALUES
    (1,'default','a65d3fe5-258d-43e3-a5e6-dc12c4879ed6',2,0,1657528102,1683203496);


# Dump of table processor_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `processor_log`;

CREATE TABLE `processor_log` (
                                 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                 `task_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Task id',
                                 `worker_address` varchar(128) NOT NULL DEFAULT '' COMMENT 'Worker address',
                                 `content` longtext NOT NULL COMMENT 'Content',
                                 `time` bigint(16) unsigned NOT NULL COMMENT 'TIme',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_task_unique_id_time` (`time`),
                                 KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table server
# ------------------------------------------------------------

DROP TABLE IF EXISTS `server`;

CREATE TABLE `server` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
                          `ip` varchar(32) NOT NULL DEFAULT '' COMMENT 'Server ip',
                          `akka_address` varchar(32) NOT NULL DEFAULT '' COMMENT 'Akka address like `127.0.0.1:25520`',
                          `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Server status 1=ok 2=fail',
                          `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                          `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                          `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                          `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                          PRIMARY KEY (`id`),
                          KEY `udx_akka_address` (`akka_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table server_reports
# ------------------------------------------------------------

DROP TABLE IF EXISTS `server_reports`;

CREATE TABLE `server_reports` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                  `server_id` bigint(20) unsigned NOT NULL COMMENT 'Server id',
                                  `report_server_id` bigint(20) unsigned NOT NULL COMMENT 'Report server id',
                                  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Report status',
                                  `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                  `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                  `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                                  `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_create_time_server_id` (`create_time`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table system
# ------------------------------------------------------------

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
                          `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                          `version` varchar(16) NOT NULL DEFAULT '0' COMMENT 'Version',
                          `cluster_version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT 'Cluster version',
                          `cluster_delay_version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT 'Cluster delay version',
                          `worker_supervisor_slot` int(11) unsigned NOT NULL DEFAULT '16' COMMENT 'Worker supervisor slot',
                          `delay_zset_slot` int(11) unsigned NOT NULL DEFAULT '4' COMMENT 'Delay zset slot',
                          `delay_fail_zset_slot` int(11) NOT NULL DEFAULT '2' COMMENT 'Delay fail zset slot',
                          `delay_add_list_slot` int(11) unsigned NOT NULL DEFAULT '2' COMMENT 'Delay add list slot',
                          `delay_status_list_slot` int(10) unsigned NOT NULL DEFAULT '2' COMMENT 'Delay status list slot',
                          `delay_delete_list_slot` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'Delay delete list slot',
                          `max_slot` int(11) unsigned NOT NULL DEFAULT '256' COMMENT 'System max slot',
                          `job_keep_days` int(11) NOT NULL DEFAULT '30' COMMENT 'Job keep days',
                          `delay_keep_days` int(11) NOT NULL DEFAULT '30' COMMENT 'Delay keep days',
                          `server_keep_days` int(11) NOT NULL DEFAULT '30' COMMENT 'Server keep days',
                          `worker_keep_days` int(11) NOT NULL DEFAULT '30' COMMENT 'Worker keep days',
                          `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                          `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                          `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                          `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `system` (`id`, `version`, `cluster_version`, `cluster_delay_version`, `worker_supervisor_slot`, `delay_zset_slot`, `delay_fail_zset_slot`, `delay_add_list_slot`, `delay_status_list_slot`, `delay_delete_list_slot`, `max_slot`, `job_keep_days`, `delay_keep_days`, `server_keep_days`, `worker_keep_days`, `deleted`, `delete_time`, `create_time`, `update_time`)
VALUES
    (1,'1.0.0',437,8,32,2,2,2,2,1,256,1,7,90,180,2,0,1663590330,1663590330);


# Dump of table task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                        `job_id` bigint(20) unsigned NOT NULL COMMENT 'Job id',
                        `instance_id` bigint(20) unsigned NOT NULL COMMENT 'Instance id',
                        `circle_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Circle id',
                        `task_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'Task id',
                        `task_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Task name',
                        `task_parent_id` varchar(64) NOT NULL DEFAULT '0' COMMENT 'Task parent id',
                        `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Status',
                        `worker_address` varchar(64) NOT NULL DEFAULT '' COMMENT 'Worker address',
                        `result` longtext COMMENT 'Task result',
                        `task_body` blob COMMENT 'Task body',
                        `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                        `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `udx_task_id` (`task_id`),
                        KEY `idx_instance_id_circle_id` (`instance_id`,`circle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table worker
# ------------------------------------------------------------

DROP TABLE IF EXISTS `worker`;

CREATE TABLE `worker` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                          `app_id` bigint(20) unsigned NOT NULL COMMENT 'Application id',
                          `namespace_id` bigint(20) unsigned NOT NULL COMMENT 'Namepspace',
                          `app_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Application name',
                          `worker_key` varchar(64) NOT NULL DEFAULT '' COMMENT 'Worker unique key',
                          `slots_id` bigint(20) unsigned NOT NULL COMMENT 'Slots id',
                          `address` varchar(32) NOT NULL DEFAULT '' COMMENT 'Address',
                          `protocol_type` varchar(8) NOT NULL DEFAULT '' COMMENT 'Protocol type',
                          `version` varchar(32) NOT NULL DEFAULT '' COMMENT 'Version',
                          `last_heartbeat_time` bigint(12) unsigned NOT NULL COMMENT 'Last heartbeat time',
                          `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Worker status',
                          `metric` varchar(1024) NOT NULL DEFAULT '' COMMENT 'Metric',
                          `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                          `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                          `create_time` bigint(12) unsigned NOT NULL COMMENT 'Create time',
                          `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                          PRIMARY KEY (`id`),
                          KEY `udx_address` (`address`),
                          KEY `idx_namespace_id_app_id` (`namespace_id`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
