# ************************************************************
# Openjob SQL
# Version 1.0.2
#
# http://www.openjob.io/
# https://github.com/open-job/openjob
#
# Host: 127.0.0.1 (MySQL)
# Database: openjob
# Generation Time: 2023-05-07 09:51:36 +0000
# ************************************************************
ALTER TABLE `delay_instance` ADD INDEX `idx_delay_id` (`delay_id`);

ALTER TABLE `job_instance` ADD `create_time_hour` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Time hour`2023052701`'  AFTER `update_time`;
ALTER TABLE `job_instance` ADD `create_time_date` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Time date`20230527`'  AFTER `update_time`;

ALTER TABLE `job_instance` ADD INDEX `idx_create_time_hour` (`create_time`, `create_time_hour`);
ALTER TABLE `job_instance` ADD INDEX `idx_create_time_day` (`create_time`, `create_time_date`);

ALTER TABLE `delay_instance` ADD `create_time_hour` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Time hour`2023052701`'  AFTER `update_time`;
ALTER TABLE `delay_instance` ADD `create_time_date` INT(11)  NOT NULL  DEFAULT '0'  COMMENT 'Time date`20230527`'  AFTER `update_time`;

ALTER TABLE `delay_instance` ADD INDEX `idx_create_time_hour` (`create_time`, `create_time_hour`);
ALTER TABLE `delay_instance` ADD INDEX `idx_create_time_day` (`create_time`, `create_time_date`);