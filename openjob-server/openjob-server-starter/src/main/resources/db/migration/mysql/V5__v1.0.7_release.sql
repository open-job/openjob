#`job_instance`
# ------------------------------------------------------------
ALTER TABLE `job_instance`
    ADD `dispatch_version` bigint(20)  NOT NULL  DEFAULT '0'  COMMENT 'Dispatch version'  AFTER `last_report_time`;
ALTER TABLE `job_instance`
    ADD `execute_once` tinyint(2) NOT NULL DEFAULT '2' COMMENT 'Execute once, 1=yes 2=no' AFTER `execute_type`;

#`job_instance_task`
# ------------------------------------------------------------
ALTER TABLE `job_instance_task`
    ADD `dispatch_version` bigint(20)  NOT NULL  DEFAULT '0'  COMMENT 'Dispatch version'  AFTER `job_instance_id`;