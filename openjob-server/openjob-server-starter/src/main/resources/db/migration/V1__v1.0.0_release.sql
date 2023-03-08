# Dump of table app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                       `namespace_id` bigint(20) NOT NULL,
                       `name` varchar(256) NOT NULL DEFAULT '',
                       `desc` varchar(256) NOT NULL DEFAULT '',
                       `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                       `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                       `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                       `create_time` bigint(12) unsigned NOT NULL,
                       `update_time` bigint(12) unsigned NOT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `udx_app_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40000 ALTER TABLE `app` DISABLE KEYS */;

INSERT INTO `app` (`id`, `namespace_id`, `name`, `desc`, `status`, `create_time`, `update_time`)
VALUES
    (1,1,'openjob','openjob', 1, 1658473199,1658473199);

/*!40000 ALTER TABLE `app` ENABLE KEYS */;



# Dump of table delay
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay`;

CREATE TABLE `delay` (
                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                         `namespace_id` bigint(20) NOT NULL,
                         `app_id` bigint(20) NOT NULL,
                         `name` varchar(128) NOT NULL DEFAULT '',
                         `description` varchar(256) NOT NULL DEFAULT '',
                         `processor_info` varchar(256) NOT NULL DEFAULT '',
                         `fail_retry_times` int(11) unsigned NOT NULL DEFAULT '0',
                         `fail_retry_interval` int(11) unsigned NOT NULL DEFAULT '1000',
                         `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                         `execute_timeout` int(11) unsigned NOT NULL DEFAULT '0',
                         `concurrency` int(11) unsigned NOT NULL DEFAULT '2',
                         `blocking_size` int(11) unsigned NOT NULL DEFAULT '20',
                         `topic` varchar(128) NOT NULL DEFAULT '',
                         `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                         `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                         `create_time` bigint(12) NOT NULL,
                         `update_time` bigint(12) NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `udx_topic` (`topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `delay` (`id`, `namespace_id`, `app_id`, `name`, `description`, `processor_info`, `fail_retry_times`, `fail_retry_interval`, `status`, `execute_timeout`, `concurrency`, `blocking_size`, `topic`, `deleted`, `delete_time`, `create_time`, `update_time`)
VALUES
    (1,1,1,'delay_test','','io.openjob.worker.samples.processor.DelayProcessorSample',0,1000,1,30,2,20,'openjob.delay.test',2,0,0,0);


# Dump of table delay_instance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay_instance`;

CREATE TABLE `delay_instance` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                  `app_id` bigint(20) unsigned NOT NULL,
                                  `namespace_id` bigint(20) unsigned NOT NULL,
                                  `task_id` varchar(64) NOT NULL DEFAULT '',
                                  `topic` varchar(128) NOT NULL DEFAULT '',
                                  `delay_id` bigint(20) unsigned NOT NULL,
                                  `delay_params` longtext NOT NULL,
                                  `delay_extra` text NOT NULL,
                                  `status` tinyint(2) NOT NULL,
                                  `execute_time` bigint(12) NOT NULL,
                                  `deleted` tinyint(12) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                  `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                  `create_time` bigint(12) NOT NULL,
                                  `update_time` bigint(12) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `udx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Dump of table delay_worker
# ------------------------------------------------------------

DROP TABLE IF EXISTS `delay_worker`;

CREATE TABLE `delay_worker` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                `topic` varchar(128) NOT NULL DEFAULT '',
                                `pull_size` int(11) unsigned NOT NULL DEFAULT '0',
                                `pull_time` bigint(16) unsigned NOT NULL DEFAULT '0',
                                `create_time` bigint(12) NOT NULL,
                                `update_time` bigint(12) NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                       `namespace_id` bigint(20) unsigned NOT NULL,
                       `app_id` bigint(20) unsigned NOT NULL,
                       `workflow_id` bigint(20) unsigned NOT NULL DEFAULT '0',
                       `name` varchar(32) NOT NULL DEFAULT '',
                       `description` varchar(128) NOT NULL DEFAULT '',
                       `processor_type` varchar(16) NOT NULL DEFAULT 'java' COMMENT 'java /shell/python',
                       `processor_info` varchar(128) NOT NULL DEFAULT '',
                       `execute_type` varchar(16) NOT NULL DEFAULT 'standalone' COMMENT 'execute type 1=standalone 2=broadcast 3=MR',
                       `params` varchar(3096) NOT NULL DEFAULT '',
                       `params_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Params type text/json/yaml',
                       `extend_params_type` varchar(16) DEFAULT NULL COMMENT 'Extend params type text/json/yaml',
                       `extend_params` varchar(3096) NOT NULL DEFAULT '',
                       `fail_retry_times` int(11) unsigned NOT NULL,
                       `fail_retry_interval` int(11) unsigned NOT NULL,
                       `concurrency` int(11) unsigned NOT NULL DEFAULT '1',
                       `time_expression_type` varchar(16) NOT NULL DEFAULT 'cron' COMMENT 'cron/second/delay',
                       `time_expression` varchar(32) NOT NULL DEFAULT '' COMMENT 'Cron express type',
                       `execute_strategy` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
                       `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1=running 2=stop',
                       `next_execute_time` bigint(12) unsigned NOT NULL,
                       `slots_id` int(11) unsigned NOT NULL,
                       `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                       `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                       `create_time` bigint(12) unsigned NOT NULL,
                       `update_time` bigint(12) unsigned NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40000 ALTER TABLE `job` DISABLE KEYS */;

INSERT INTO `job` (`id`, `namespace_id`, `app_id`, `workflow_id`, `name`, `description`, `processor_type`, `processor_info`, `execute_type`, `params`, `params_type`, `extend_params_type`, `extend_params`,`fail_retry_times`, `fail_retry_interval`, `concurrency`, `time_expression_type`, `time_expression`, `status`, `next_execute_time`, `slots_id`, `create_time`, `update_time`)
VALUES
    (10,1,1,0,'测试任务','测试任务','java','io.openjob.worker.samples.processor.JavaProcessorSample','standalone','','text','text','',0,0,1,'cron','30 * * * * ?',2,1663590330,233,1657528102,1663590220),
    (13,1,1,0,'测试任务2','测试任务2','java','io.openjob.worker.samples.processorJavaProcessorSample','standalone','','text','text','',0,0,1,'cron','10 * * * * ?',2,1660288630,1,1657528102,1660288512),
    (14,1,1,0,'测试任务','测试任务','java','io.openjob.worker.samples.processor.JavaProcessorSample','standalone','','text','text','',0,0,1,'cron','59 * * * * ?',2,1660288619,126,1657528102,1660288512),
    (15,1,1,0,'测试任务','测试任务','java','io.openjob.worker.samples.processor.JavaProcessorSample','standalone','','text','text','',0,0,1,'cron','0 */3 * * * ?',2,1660288680,12,1657528102,1660288452),
    (16,1,1,0,'MR任务测试','测试MR','java','io.openjob.worker.samples.processor.MapReduceProcessorSample','mapReduce','','text','text','',0,0,1,'cron','15 * * * * ?',2,1666100115,126,1657528102,1666099995);

/*!40000 ALTER TABLE `job` ENABLE KEYS */;


# Dump of table job_instance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance`;

CREATE TABLE `job_instance` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                `job_id` bigint(20) unsigned NOT NULL,
                                `params` varchar(3096) NOT NULL,
                                `params_type` varchar(16) NOT NULL DEFAULT '' COMMENT 'Params type text/json/yaml',
                                `extend_params_type` varchar(16) DEFAULT NULL COMMENT 'Extend params type text/json/yaml',
                                `extend_params` varchar(3096) NOT NULL DEFAULT '',
                                `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                                `slots_id` bigint(20) unsigned NOT NULL,
                                `workflow_id` bigint(20) unsigned NOT NULL DEFAULT '0',
                                `namespace_id` bigint(20) unsigned NOT NULL,
                                `app_id` bigint(20) unsigned NOT NULL,
                                `execute_time` bigint(12) unsigned NOT NULL,
                                `complete_time` bigint(12) unsigned NOT NULL DEFAULT '0',
                                `last_report_time` bigint(12) unsigned NOT NULL DEFAULT '0',
                                `processor_type` varchar(16) NOT NULL DEFAULT '',
                                `processor_info` varchar(128) NOT NULL DEFAULT '',
                                `execute_type` varchar(16) NOT NULL DEFAULT '',
                                `fail_retry_times` int(11) unsigned NOT NULL,
                                `fail_retry_interval` int(11) unsigned NOT NULL,
                                `time_expression_type` varchar(16) NOT NULL DEFAULT '',
                                `time_expression` varchar(32) NOT NULL DEFAULT '',
                                `concurrency` int(11) unsigned NOT NULL DEFAULT '1',
                                `worker_address` varchar(32) NOT NULL DEFAULT '',
                                `execute_strategy` tinyint(2) NOT NULL DEFAULT '1' COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
                                `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                `update_time` bigint(12) unsigned NOT NULL,
                                `create_time` bigint(12) unsigned NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `idx_execute_time_slots_id` (`execute_time`,`slots_id`),
                                KEY `idx_last_report_time_slots_id` (`last_report_time`,`slots_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table job_instance_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance_log`;

CREATE TABLE `job_instance_log` (
                                    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                    `job_id` bigint(20) unsigned NOT NULL,
                                    `job_instance_id` bigint(20) unsigned NOT NULL,
                                    `message` longtext,
                                    `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                    `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                    `create_time` bigint(12) unsigned NOT NULL,
                                    `update_time` bigint(12) unsigned NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# Dump of table job_instance_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_instance_task`;

CREATE TABLE `job_instance_task` (
                                     `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                     `job_id` bigint(20) unsigned NOT NULL,
                                     `job_instance_id` bigint(20) unsigned NOT NULL,
                                     `circle_id` bigint(20) unsigned NOT NULL,
                                     `task_id` varchar(64) NOT NULL DEFAULT '',
                                     `parent_task_id` varchar(64) NOT NULL DEFAULT '0',
                                     `task_name` varchar(128) NOT NULL DEFAULT '',
                                     `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                                     `result` longtext,
                                     `worker_address` varchar(128) NOT NULL DEFAULT '',
                                     `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                     `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                     `create_time` bigint(12) unsigned DEFAULT NULL,
                                     `update_time` bigint(12) unsigned DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `udx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table processor_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `processor_log`;

CREATE TABLE `processor_log` (
                                 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                 `task_id` varchar(128) NOT NULL DEFAULT '',
                                 `worker_address` varchar(128) NOT NULL DEFAULT '',
                                 `content` longtext NOT NULL,
                                 `time` bigint(16) unsigned NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `idx_task_unique_id_time` (`time`),
                                 KEY `idx_task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# Dump of table job_slots
# ------------------------------------------------------------

DROP TABLE IF EXISTS `job_slots`;

CREATE TABLE `job_slots` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                             `server_id` bigint(20) unsigned NOT NULL,
                             `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                             `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                             `create_time` bigint(12) unsigned NOT NULL,
                             `update_time` bigint(12) unsigned NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `idx_server_id` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40000 ALTER TABLE `job_slots` DISABLE KEYS */;

INSERT INTO `job_slots` (`id`, `server_id`, `create_time`, `update_time`)
VALUES
    (1,0,1655781998,1666100014),
    (2,0,1655781999,1666100014),
    (3,0,1655781999,1666100014),
    (4,0,1655781999,1666100014),
    (5,0,1655781999,1666100014),
    (6,0,1655781999,1666100014),
    (7,0,1655781999,1666100014),
    (8,0,1655781999,1666100014),
    (9,0,1655781999,1666100014),
    (10,0,1655781999,1666100014),
    (11,0,1655781999,1666100014),
    (12,0,1655781999,1666100014),
    (13,0,1655781999,1666100014),
    (14,0,1655781999,1666100014),
    (15,0,1655781999,1666100014),
    (16,0,1655781999,1666100014),
    (17,0,1655781999,1666100014),
    (18,0,1655781999,1666100014),
    (19,0,1655781999,1666100014),
    (20,0,1655781999,1666100014),
    (21,0,1655781999,1666100014),
    (22,0,1655781999,1666100014),
    (23,0,1655781999,1666100014),
    (24,0,1655781999,1666100014),
    (25,0,1655781999,1666100014),
    (26,0,1655781999,1666100014),
    (27,0,1655781999,1666100014),
    (28,0,1655781999,1666100014),
    (29,0,1655781999,1666100014),
    (30,0,1655781999,1666100014),
    (31,0,1655781999,1666100014),
    (32,0,1655781999,1666100014),
    (33,0,1655781999,1666100014),
    (34,0,1655781999,1666100014),
    (35,0,1655781999,1666100014),
    (36,0,1655781999,1666100014),
    (37,0,1655781999,1666100014),
    (38,0,1655781999,1666100014),
    (39,0,1655781999,1666100014),
    (40,0,1655781999,1666100014),
    (41,0,1655781999,1666100014),
    (42,0,1655781999,1666100014),
    (43,0,1655781999,1666100014),
    (44,0,1655781999,1666100014),
    (45,0,1655781999,1666100014),
    (46,0,1655781999,1666100014),
    (47,0,1655781999,1666100014),
    (48,0,1655781999,1666100014),
    (49,0,1655781999,1666100014),
    (50,0,1655781999,1666100014),
    (51,0,1655781999,1666100014),
    (52,0,1655781999,1666100014),
    (53,0,1655781999,1666100014),
    (54,0,1655781999,1666100014),
    (55,0,1655781999,1666100014),
    (56,0,1655781999,1666100014),
    (57,0,1655781999,1666100014),
    (58,0,1655781999,1666100014),
    (59,0,1655781999,1666100014),
    (60,0,1655781999,1666100014),
    (61,0,1655781999,1666100014),
    (62,0,1655781999,1666100014),
    (63,0,1655781999,1666100014),
    (64,0,1655781999,1666100014),
    (65,0,1655781999,1666100014),
    (66,0,1655781999,1666100014),
    (67,0,1655781999,1666100014),
    (68,0,1655781999,1666100014),
    (69,0,1655781999,1666100014),
    (70,0,1655781999,1666100014),
    (71,0,1655781999,1666100014),
    (72,0,1655781999,1666100014),
    (73,0,1655781999,1666100014),
    (74,0,1655781999,1666100014),
    (75,0,1655781999,1666100014),
    (76,0,1655781999,1666100014),
    (77,0,1655781999,1666100014),
    (78,0,1655781999,1666100014),
    (79,0,1655781999,1666100014),
    (80,0,1655781999,1666100014),
    (81,0,1655781999,1666100014),
    (82,0,1655781999,1666100014),
    (83,0,1655781999,1666100014),
    (84,0,1655781999,1666100014),
    (85,0,1655781999,1666100014),
    (86,0,1655781999,1666100014),
    (87,0,1655781999,1666100014),
    (88,0,1655781999,1666100014),
    (89,0,1655781999,1666100014),
    (90,0,1655781999,1666100014),
    (91,0,1655781999,1666100014),
    (92,0,1655781999,1666100014),
    (93,0,1655781999,1666100014),
    (94,0,1655781999,1666100014),
    (95,0,1655781999,1666100014),
    (96,0,1655781999,1666100014),
    (97,0,1655781999,1666100014),
    (98,0,1655781999,1666100014),
    (99,0,1655781999,1666100014),
    (100,0,1655781999,1666100014),
    (101,0,1655781999,1666100014),
    (102,0,1655781999,1666100014),
    (103,0,1655781999,1666100014),
    (104,0,1655781999,1666100014),
    (105,0,1655781999,1666100014),
    (106,0,1655781999,1666100014),
    (107,0,1655781999,1666100014),
    (108,0,1655781999,1666100014),
    (109,0,1655781999,1666100014),
    (110,0,1655781999,1666100014),
    (111,0,1655781999,1666100014),
    (112,0,1655781999,1666100014),
    (113,0,1655781999,1666100014),
    (114,0,1655781999,1666100014),
    (115,0,1655781999,1666100014),
    (116,0,1655781999,1666100014),
    (117,0,1655781999,1666100014),
    (118,0,1655781999,1666100014),
    (119,0,1655781999,1666100014),
    (120,0,1655781999,1666100014),
    (121,0,1655781999,1666100014),
    (122,0,1655781999,1666100014),
    (123,0,1655781999,1666100014),
    (124,0,1655781999,1666100014),
    (125,0,1655781999,1666100014),
    (126,0,1655781999,1666100014),
    (127,0,1655781999,1666100014),
    (128,0,1655781999,1666100014),
    (129,0,1655781999,1666100014),
    (130,0,1655781999,1666100014),
    (131,0,1655781999,1666100014),
    (132,0,1655781999,1666100014),
    (133,0,1655781999,1666100014),
    (134,0,1655781999,1666100014),
    (135,0,1655781999,1666100014),
    (136,0,1655782000,1666100014),
    (137,0,1655782000,1666100014),
    (138,0,1655782000,1666100014),
    (139,0,1655782000,1666100014),
    (140,0,1655782000,1666100014),
    (141,0,1655782000,1666100014),
    (142,0,1655782000,1666100014),
    (143,0,1655782000,1666100014),
    (144,0,1655782000,1666100014),
    (145,0,1655782000,1666100014),
    (146,0,1655782000,1666100014),
    (147,0,1655782000,1666100014),
    (148,0,1655782000,1666100014),
    (149,0,1655782000,1666100014),
    (150,0,1655782000,1666100014),
    (151,0,1655782000,1666100014),
    (152,0,1655782000,1666100014),
    (153,0,1655782000,1666100014),
    (154,0,1655782000,1666100014),
    (155,0,1655782000,1666100014),
    (156,0,1655782000,1666100014),
    (157,0,1655782000,1666100014),
    (158,0,1655782000,1666100014),
    (159,0,1655782000,1666100014),
    (160,0,1655782000,1666100014),
    (161,0,1655782000,1666100014),
    (162,0,1655782000,1666100014),
    (163,0,1655782000,1666100014),
    (164,0,1655782000,1666100014),
    (165,0,1655782000,1666100014),
    (166,0,1655782000,1666100014),
    (167,0,1655782000,1666100014),
    (168,0,1655782000,1666100014),
    (169,0,1655782000,1666100014),
    (170,0,1655782000,1666100014),
    (171,0,1655782000,1666100014),
    (172,0,1655782000,1666100014),
    (173,0,1655782000,1666100014),
    (174,0,1655782000,1666100014),
    (175,0,1655782000,1666100014),
    (176,0,1655782000,1666100014),
    (177,0,1655782000,1666100014),
    (178,0,1655782000,1666100014),
    (179,0,1655782000,1666100014),
    (180,0,1655782000,1666100014),
    (181,0,1655782000,1666100014),
    (182,0,1655782000,1666100014),
    (183,0,1655782000,1666100014),
    (184,0,1655782000,1666100014),
    (185,0,1655782000,1666100014),
    (186,0,1655782000,1666100014),
    (187,0,1655782000,1666100014),
    (188,0,1655782000,1666100014),
    (189,0,1655782000,1666100014),
    (190,0,1655782000,1666100014),
    (191,0,1655782000,1666100014),
    (192,0,1655782000,1666100014),
    (193,0,1655782000,1666100014),
    (194,0,1655782000,1666100014),
    (195,0,1655782000,1666100014),
    (196,0,1655782000,1666100014),
    (197,0,1655782000,1666100014),
    (198,0,1655782000,1666100014),
    (199,0,1655782000,1666100014),
    (200,0,1655782000,1666100014),
    (201,0,1655782000,1666100014),
    (202,0,1655782000,1666100014),
    (203,0,1655782000,1666100014),
    (204,0,1655782000,1666100014),
    (205,0,1655782000,1666100014),
    (206,0,1655782000,1666100014),
    (207,0,1655782000,1666100014),
    (208,0,1655782000,1666100014),
    (209,0,1655782000,1666100014),
    (210,0,1655782000,1666100014),
    (211,0,1655782000,1666100014),
    (212,0,1655782000,1666100014),
    (213,0,1655782000,1666100014),
    (214,0,1655782000,1666100014),
    (215,0,1655782000,1666100014),
    (216,0,1655782000,1666100014),
    (217,0,1655782000,1666100014),
    (218,0,1655782000,1666100014),
    (219,0,1655782000,1666100014),
    (220,0,1655782000,1666100014),
    (221,0,1655782000,1666100014),
    (222,0,1655782000,1666100014),
    (223,0,1655782000,1666100014),
    (224,0,1655782000,1666100014),
    (225,0,1655782000,1666100014),
    (226,0,1655782000,1666100014),
    (227,0,1655782000,1666100014),
    (228,0,1655782000,1666100014),
    (229,0,1655782000,1666100014),
    (230,0,1655782000,1666100014),
    (231,0,1655782000,1666100014),
    (232,0,1655782000,1666100014),
    (233,0,1655782000,1666100014),
    (234,0,1655782000,1666100014),
    (235,0,1655782000,1666100014),
    (236,0,1655782000,1666100014),
    (237,0,1655782000,1666100014),
    (238,0,1655782000,1666100014),
    (239,0,1655782000,1666100014),
    (240,0,1655782000,1666100014),
    (241,0,1655782000,1666100014),
    (242,0,1655782000,1666100014),
    (243,0,1655782000,1666100014),
    (244,0,1655782000,1666100014),
    (245,0,1655782000,1666100014),
    (246,0,1655782000,1666100014),
    (247,0,1655782000,1666100014),
    (248,0,1655782000,1666100014),
    (249,0,1655782000,1666100014),
    (250,0,1655782000,1666100014),
    (251,0,1655782000,1666100014),
    (252,0,1655782000,1666100014),
    (253,0,1655782000,1666100014),
    (254,0,1655782000,1666100014),
    (255,0,1655782000,1666100014),
    (256,0,1655782000,1666100014);

/*!40000 ALTER TABLE `job_slots` ENABLE KEYS */;

# Dump of table namespace
# ------------------------------------------------------------

DROP TABLE IF EXISTS `namespace`;

CREATE TABLE `namespace` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(64) NOT NULL DEFAULT '',
                             `uuid` varchar(64) NOT NULL DEFAULT '',
                             `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                             `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                             `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                             `create_time` bigint(12) unsigned NOT NULL,
                             `update_time` bigint(12) unsigned NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into namespace (id, `name`, uuid, status, deleted, delete_time, create_time, update_time)
values  (1, 'default', 'a65d3fe5-258d-43e3-a5e6-dc12c4879ed6', 1, 2, 0, 1657528102, 1657528102);



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
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                  `server_id` bigint(20) unsigned NOT NULL,
                                  `report_server_id` bigint(20) unsigned NOT NULL,
                                  `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                                  `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                  `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                  `create_time` bigint(12) unsigned NOT NULL,
                                  `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                        `job_id` bigint(20) unsigned NOT NULL,
                        `instance_id` bigint(20) unsigned NOT NULL,
                        `circle_id` bigint(20) unsigned NOT NULL DEFAULT '0',
                        `task_id` varchar(64) NOT NULL DEFAULT '',
                        `task_name` varchar(128) NOT NULL,
                        `task_parent_id` varchar(64) NOT NULL DEFAULT '0',
                        `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                        `worker_address` varchar(64) NOT NULL DEFAULT '',
                        `result` longtext,
                        `task_body` blob,
                        `create_time` bigint(12) unsigned NOT NULL,
                        `update_time` bigint(12) unsigned NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `udx_task_id` (`task_id`),
                        KEY `idx_instance_id_circle_id` (`instance_id`,`circle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Dump of table worker
# ------------------------------------------------------------

DROP TABLE IF EXISTS `worker`;

CREATE TABLE `worker` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `app_id` bigint(20) unsigned NOT NULL,
                          `namespace_id` bigint(20) unsigned NOT NULL,
                          `app_name` varchar(128) NOT NULL DEFAULT '',
                          `worker_key` varchar(64) NOT NULL DEFAULT '',
                          `slots_id` bigint(20) unsigned NOT NULL,
                          `address` varchar(32) NOT NULL DEFAULT '',
                          `protocol_type` varchar(8) NOT NULL DEFAULT '',
                          `version` varchar(32) NOT NULL DEFAULT '',
                          `last_heartbeat_time` bigint(12) unsigned NOT NULL,
                          `status` tinyint(2) unsigned NOT NULL DEFAULT '1',
                          `metric` varchar(1024) NOT NULL DEFAULT '',
                          `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                          `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                          `create_time` bigint(12) unsigned NOT NULL,
                          `update_time` bigint(12) unsigned NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `udx_address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# Dump of table system
# ------------------------------------------------------------
DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
                          `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                          `version` varchar(16) NOT NULL DEFAULT '0',
                          `cluster_version` bigint(20) unsigned NOT NULL DEFAULT '1',
                          `cluster_delay_version` bigint(20) unsigned NOT NULL DEFAULT '1',
                          `cluster_supervisor_slot` int(11) unsigned NOT NULL DEFAULT '1',
                          `worker_supervisor_slot` int(11) unsigned NOT NULL DEFAULT '16',
                          `delay_zset_slot` int(11) unsigned NOT NULL DEFAULT '4',
                          `delay_add_list_slot` int(11) unsigned NOT NULL DEFAULT '2',
                          `delay_status_list_slot` int(10) unsigned NOT NULL DEFAULT '2',
                          `delay_delete_list_slot` int(10) unsigned NOT NULL DEFAULT '1',
                          `max_slot` int(11) unsigned NOT NULL DEFAULT '256',
                          `deleted` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                          `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                          `create_time` bigint(12) unsigned NOT NULL,
                          `update_time` bigint(12) unsigned NOT NULL COMMENT 'Update time',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40000 ALTER TABLE `system` DISABLE KEYS */;

INSERT INTO `system` (`id`, `version`, `cluster_version`, `cluster_delay_version`, `cluster_supervisor_slot`, `worker_supervisor_slot`, `delay_zset_slot`, `delay_add_list_slot`, `delay_status_list_slot`, `delay_delete_list_slot`, `max_slot`, `create_time`, `update_time`)
VALUES
    (1,'1.0.0',1,1,1,256,2,2,2,1,256,1663590330,1663590330);


DROP TABLE IF EXISTS `admin_session`;
CREATE TABLE `admin_session` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'User Id',
                              `username` varchar(48) NOT NULL DEFAULT '' COMMENT 'User name',
                              `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'Session token',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              INDEX `idx_user_id` (`user_id`),
                              UNIQUE `uni_token` (`token`),
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Admin user session caches';

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `username` varchar(48) NOT NULL DEFAULT '' COMMENT 'User name',
                              `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT 'Nickname',
                              `passwd` varchar(128) NOT NULL DEFAULT '' COMMENT 'Password',
                              `session_key` varchar(128) NOT NULL DEFAULT '' COMMENT 'Session key',
                              `session_expire_at` bigint(12) NOT NULL COMMENT 'Session expire at',
                              `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'Api auth token',
                              `role_ids` JSON COMMENT 'role IDs. JSON: [1,2]',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              INDEX `idx_name` (`username`),
                              UNIQUE `uni_token` (`token`),
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Job admin users';

INSERT INTO `admin_user` (`username`, `nickname`, `passwd`, `session_key`, `session_expire_at`, `token`, `role_ids`, `deleted`, `delete_time`, `update_time`, `create_time`)
VALUES
    ('admin', 'Administrator', 'c881f5068a2d066023dfd404d9a75e4f1708a9df6dc9c451900fc72d986f7ba9', '79f74383e2c92ae01e172ced4c9267d5', 0, '79f74383e2c92ae01e172ced4c9267d5', '[1]', 2, 0, 1670255999, 1670255999),
    ('openjob', 'OpenJob User', 'c0d4247cd38f62f975ba32c9f1e58926f6a99c223f642524c53917810c95d39b', '2cebdf15d414b6713672475a21f995a0', 0, '2cebdf15d414b6713672475a21f995a0', '[2]', 2, 0, 1670255999, 1670255999);

DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `name` varchar(48) NOT NULL DEFAULT '' COMMENT 'role name',
                              `desc` varchar(128) NOT NULL DEFAULT '' COMMENT 'Description',
                              `admin` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Is supper admin. 1=yes 2=no',
                              `menu_ids` JSON COMMENT 'Menu ids for role. JSON array',
                              `perm_ids` JSON COMMENT 'Permission ids for role. JSON array',
                              `namespace_ids` JSON COMMENT 'namespaces Ids. JSON array',
                              `app_ids` JSON COMMENT 'app ids. JSON array',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              INDEX `idx_name` (`name`),
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Admin roles with perms';

INSERT INTO `admin_role` (`name`, `desc`, `menu_ids`, `perm_ids`, `namespace_ids`, `app_ids`, `admin`, `deleted`, `delete_time`, `update_time`, `create_time`)
VALUES
    ('Admin', 'Administrator role', '[1, 5]', '[11, 12, 13, 14, 15]', '[]', '[]', 1, 2, 0, 1670255999, 1670255999),
    ('Developer', 'Developer role', '[1, 5]', '[11, 12, 13, 14, 15]', '[]', '[]', 2, 2, 0, 1670255999, 1670255999);

DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Parent ID',
                              `type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Type. 1=menu 2=perm',
                              `name` varchar(48) NOT NULL DEFAULT '' COMMENT 'Menu name',
                              `path` varchar(86) NOT NULL DEFAULT '' COMMENT 'Route path or API path',
                              `meta` JSON COMMENT 'Extra meta data. JSON object: {icon:xx,title:some.name}',
                              `hidden` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Hidden status. 1=yes 2=no',
                              `sort` int(10) NOT NULL DEFAULT '0' COMMENT 'Sort value',
                              `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                              `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                              `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                              `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                              PRIMARY KEY (`id`),
                              INDEX  `idx_pid` (`pid`),
                              INDEX  `idx_path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Admin user permissions';

insert into admin_permission (id, pid, `type`, `name`, `path`, meta, hidden, sort, deleted, delete_time, update_time, create_time)
values  (1, 0, 1, 'dashboard', '/dashboard', '{"icon": "iconfont icon-shouye", "roles": ["admin"], "title": "message.router.dashboard", "isLink": "", "isAffix": false, "isIframe": false, "component": "/home/index.vue", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (20, 0, 1, 'namespace', '/admin/namespace/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.namespace", "isLink": "", "isAffix": false, "isIframe": false, "component": "/namespace/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (40, 0, 1, 'application', '/admin/app/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.application", "isLink": "", "isAffix": false, "isIframe": false, "component": "/app/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (60, 0, 1, 'cronJob', '/a', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (61, 60, 1, 'cronJobJob', '/admin/job/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (62, 60, 1, 'cronJobInstance', '/admin/job-instance/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (63, 60, 1, 'cronJobPage', '/admin/job/page', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/page", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
        (80, 0, 1, 'delayJob', '/b', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.delayJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (81, 80, 1, 'delayJobDelay', '/admin/delay/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.delayJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (82, 80, 1, 'delayJobInstance', '/admin/delay-instance/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.delayJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (200, 0, 1, 'cluster', '/c', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.clusterManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (201, 200, 1, 'ClusterNode', '/admin/cluster-node/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.clusterNode", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/node/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (202, 200, 1, 'ClusterWorker', '/admin/cluster-worker/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.clusterWorker", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/worker/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (300, 0, 1, 'system', '/d', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.systemManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (301, 300, 1, 'SystemConfiguration', '/admin/system-config/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.systemConfiguration", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/config/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (302, 300, 1, 'SystemSlots', '/admin/system-slots/list', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.systemSlots", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/slots/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1000, 0, 2, 'namespace.add', '/admin/namespace/add', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1001, 0, 2, 'namespace.delete', '/admin/namespace/delete', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1002, 0, 2, 'namespace.update', '/admin/namespace/update', '{}', 2, 0, 2, 0, 1669972320, 1669972320),
        (1003, 0, 2, 'namespace.update.status', '/admin/namespace/update-status', '{}', 2, 0, 2, 0, 1669972320, 1669972320);

DROP TABLE IF EXISTS `notify_template`;
CREATE TABLE `notify_template` (
                                   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                   `name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Template name. eg: Wechat, DingTalk, Wecom, Feishu',
                                   `type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'notify type. 1 webhook 2 email 3 sms',
                                   `level` varchar(16) NOT NULL DEFAULT 'error' COMMENT 'Level. 1 notice 2 warning 3 error',
                                   `events` JSON COMMENT 'notify events list. JSON: [task_fail, task_suc, task_cancel, task_skip]',
                                   `contact_ids` JSON COMMENT 'related contact ids. JSON [12, 34]',
                                   `group_ids` JSON COMMENT 'related group ids. JSON [12, 34]',
                                   `webhook` varchar(128) NOT NULL DEFAULT '' COMMENT 'Webhook URL',
                                   `content` varchar(2048) NOT NULL DEFAULT '' COMMENT 'Template contents',
                                   `extra` JSON COMMENT 'Extra info. eg: third platform token',
                                   `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'Creator user ID',
                                   `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                   `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                   `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                                   `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                                   INDEX `idx_name` (`name`),
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Job notify template table';

DROP TABLE IF EXISTS `notify_contact`;
CREATE TABLE `notify_contact` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                  `name` varchar(128) NOT NULL DEFAULT '' COMMENT 'User name',
                                  `phone` varchar(24) NOT NULL DEFAULT '' COMMENT 'Phone',
                                  `email` varchar(64) NOT NULL DEFAULT '' COMMENT 'Email address',
                                  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Status. 1=OK 2=disabled',
                                  `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                  `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                  `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                                  `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                                  INDEX `idx_name` (`name`),
                                  INDEX `idx_phone` (`phone`),
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Job notify contact';

DROP TABLE IF EXISTS `notify_group`;
CREATE TABLE `notify_group` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                `name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Group name',
                                `contact_ids` varchar(2048) NOT NULL DEFAULT '' COMMENT '[12, 34]',
                                `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT 'Status. 1=OK 2=disabled',
                                `deleted` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT 'Delete status. 1=yes 2=no',
                                `delete_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete time',
                                `update_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Update time',
                                `create_time` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT 'Create time',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Notify contact group';
