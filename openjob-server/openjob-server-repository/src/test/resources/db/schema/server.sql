CREATE TABLE IF NOT EXISTS `server`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `ip`          varchar(60)         NOT NULL,
    `address`     varchar(60)         NOT NULL DEFAULT '',
    `status`      tinyint(2)          NOT NULL COMMENT 'server status 1=ok 2=fail',
    `create_time` int(11)             NOT NULL,
    `update_time` int(11)             NOT NULL,
    PRIMARY KEY (`id`)
);