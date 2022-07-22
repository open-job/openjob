CREATE TABLE IF NOT EXISTS `app`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(256)     NOT NULL DEFAULT '',
    `desc`        varchar(256)     NOT NULL DEFAULT '',
    `create_time` int(11)          NOT NULL,
    `update_time` int(11)          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `udx_app_name` (`name`)
);