#`job_instance`
# ------------------------------------------------------------
ALTER TABLE `job_instance`
    ADD `dispatch_version` int(11)  NOT NULL  DEFAULT '0'  COMMENT 'Dispatch version'  AFTER `last_report_time`;