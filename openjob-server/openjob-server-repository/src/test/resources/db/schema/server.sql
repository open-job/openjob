CREATE TABLE IF NOT EXISTS `server`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `ip`           varchar(32)         NOT NULL DEFAULT '' COMMENT 'Server ip',
    `akka_address` varchar(32)         NOT NULL DEFAULT '' COMMENT 'Akka adress like `127.0.0.1:25520`',
    `status`       tinyint(2)          NOT NULL DEFAULT '1' COMMENT 'Server status 1=ok 2=fail',
    `create_time`  int(11)             NOT NULL COMMENT 'Create time',
    `update_time`  int(11)             NOT NULL COMMENT 'Update time',
    PRIMARY KEY (`id`),
    KEY `udx_akka_address` (`akka_address`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;