#`job_instance`
# ------------------------------------------------------------
ALTER TABLE `job_instance`
    ADD `dispatch_version` bigint(20)  NOT NULL  DEFAULT '0'  COMMENT 'Dispatch version'  AFTER `last_report_time`;


#`job_instance_task`
# ------------------------------------------------------------
ALTER TABLE `job_instance_task`
    ADD `dispatch_version` bigint(20)  NOT NULL  DEFAULT '0'  COMMENT 'Dispatch version'  AFTER `job_instance_id`;