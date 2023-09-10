-- admin_permission
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    pid         BIGINT DEFAULT 0 NOT NULL COMMENT 'Parent ID',
    type        TINYINT DEFAULT 1 NOT NULL COMMENT 'Type. 1=menu 2=perm',
    name        VARCHAR(48) DEFAULT '' NOT NULL COMMENT 'Menu name',
    path        VARCHAR(86) DEFAULT '' NOT NULL COMMENT 'Route path or API path',
    meta        TEXT,
    hidden      TINYINT DEFAULT 2 NOT NULL COMMENT 'Hidden status. 1=yes 2=no',
    sort        INT DEFAULT 0 NOT NULL COMMENT 'Sort value',
    deleted     TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    update_time BIGINT DEFAULT 0 NOT NULL COMMENT 'Update time',
    create_time BIGINT DEFAULT 0 NOT NULL COMMENT 'Create time'
);

CREATE INDEX admin_permission_idx_path ON `admin_permission`(`path`);
CREATE INDEX admin_permission_idx_pid ON `admin_permission`(`pid`);

INSERT INTO `admin_permission` (id, pid, type, name, path, meta, hidden, sort, deleted, delete_time, update_time, create_time) VALUES
(1, 0, 1, 'dashboard', '/dashboard', '{"icon": "iconfont icon-shouye", "roles": ["admin"], "title": "message.router.dashboard", "isLink": "", "isAffix": false, "isIframe": false, "component": "/home/index.vue", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(20, 0, 1, 'namespace', '/admin/namespace/list', '{"icon": "ele-Edit", "roles": ["admin"], "title": "message.router.namespace", "isLink": "", "isAffix": false, "isIframe": false, "component": "/namespace/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(40, 0, 1, 'application', '/admin/app/list', '{"icon": "ele-Operation", "roles": ["admin"], "title": "message.router.application", "isLink": "", "isAffix": false, "isIframe": false, "component": "/app/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(60, 0, 1, 'cronJob', '/a', '{"icon": "ele-AlarmClock", "roles": ["admin"], "title": "message.router.cronJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(61, 60, 1, 'cronJobJob', '/admin/job/list', '{"icon": "ele-Suitcase", "roles": ["admin"], "title": "message.router.cronJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(62, 60, 1, 'cronJobInstance', '/admin/job-instance/list', '{"icon": "ele-Document", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(63, 60, 1, 'cronJobPage', '/admin/job/page', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/page", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
(80, 0, 1, 'delayJob', '/b', '{"icon": "ele-Clock", "roles": ["admin"], "title": "message.router.delayJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(81, 80, 1, 'delayJobDelay', '/admin/delay/list', '{"icon": "ele-SetUp", "roles": ["admin"], "title": "message.router.delayJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(82, 80, 1, 'delayJobInstance', '/admin/delay-instance/list', '{"icon": "ele-DocumentChecked", "roles": ["admin"], "title": "message.router.delayJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(106, 0, 1, 'userProfile', '/personal', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.userProfile", "isLink": "", "isAffix": false, "isIframe": false, "component": "/personal/index", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
(170, 0, 1, 'Alert', '/e', '{"icon": "ele-Aim", "roles": ["admin"], "title": "message.router.alert", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
(171, 170, 1, 'AlertRule', '/admin/alert/list', '{"icon": "ele-DocumentCopy", "roles": ["admin"], "title": "message.router.alertRule", "isLink": "", "isAffix": false, "isIframe": false, "component": "/alert/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
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

-- admin_role
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    name          VARCHAR(48)      DEFAULT ''     NOT NULL COMMENT 'role name',
    `desc`        VARCHAR(128)     DEFAULT ''     NOT NULL COMMENT 'Description',
    admin         TINYINT          DEFAULT 2      NOT NULL COMMENT 'Is supper admin. 1=yes 2=no',
    menu_ids      TEXT             NOT NULL,
    perm_ids      TEXT             NOT NULL,
    namespace_ids TEXT             NOT NULL,
    app_ids       TEXT             NOT NULL,
    deleted       TINYINT          DEFAULT 2      NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time   BIGINT           DEFAULT 0      NOT NULL COMMENT 'Delete time',
    update_time   BIGINT           DEFAULT 0      NOT NULL COMMENT 'Update time',
    create_time   BIGINT           DEFAULT 0      NOT NULL COMMENT 'Create time'
);

CREATE INDEX admin_role_idx_name ON `admin_role` (`name`);

insert into `admin_role` (id, name, `desc`, admin, menu_ids, perm_ids, namespace_ids, app_ids, deleted, delete_time, update_time, create_time)
values  (1, 'Admin', 'Administrator role', 1, '[]', '[]', '[]', '[]', 2, 0, 1670255999, 1670255999);

-- admin_user
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    username          VARCHAR(48)      DEFAULT ''     NOT NULL COMMENT 'User name',
    nickname          VARCHAR(64)      DEFAULT ''     NOT NULL COMMENT 'Nickname',
    passwd            VARCHAR(128)     DEFAULT ''     NOT NULL COMMENT 'Password',
    session_key       VARCHAR(128)     DEFAULT ''     NOT NULL COMMENT 'Session key',
    session_expire_at BIGINT                          NOT NULL COMMENT 'Session expire at',
    token             VARCHAR(64)      DEFAULT ''     NOT NULL COMMENT 'Api auth token',
    role_ids          TEXT             NOT NULL,
    login_ip          VARCHAR(32)      DEFAULT ''     NOT NULL COMMENT 'Login IP',
    login_time        BIGINT           DEFAULT 0      NOT NULL COMMENT 'Login time',
    deleted           TINYINT          DEFAULT 2      NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time       BIGINT           DEFAULT 0      NOT NULL COMMENT 'Delete time',
    update_time       BIGINT           DEFAULT 0      NOT NULL COMMENT 'Update time',
    create_time       BIGINT           DEFAULT 0      NOT NULL COMMENT 'Create time',
    CONSTRAINT uni_token UNIQUE (token)
);

CREATE INDEX admin_user_idx_name ON `admin_user` (`username`);

insert into `admin_user` (id, username, nickname, passwd, session_key, session_expire_at, token, role_ids, login_ip, login_time, deleted, delete_time, update_time, create_time)
values  (1, 'openjob', 'Administrator', '144f039395e24903a08926b978340a1554660c4da724d0700e3dceac9573675f', '79f74383e2c92ae01e172ced4c9267d5', 0, '79f74383e2c92ae01e172ced4c9267d5', '[1]', '127.0.0.1', 1692841681, 2, 0, 1670255999, 1670255999);

-- alert_rule
DROP TABLE IF EXISTS `alert_rule`;
CREATE TABLE `alert_rule`
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(32)      DEFAULT ''  NOT NULL COMMENT 'Rule name',
    namespace_app_ids TEXT                        COMMENT 'Namespace and application ids',
    events            TEXT                        COMMENT 'Events',
    metrics           TEXT                        COMMENT 'Metrics',
    locale            VARCHAR(16)      DEFAULT ''  COMMENT 'Alert Locale(zh_CN/en_US)',
    method            VARCHAR(32)      DEFAULT ''  NOT NULL COMMENT 'Alert method feishu/dingding/wecom/webhook',
    url               VARCHAR(256)     DEFAULT ''  NOT NULL COMMENT 'Alert url',
    secret            VARCHAR(256)     DEFAULT ''  NOT NULL COMMENT 'Alert secret',
    status            TINYINT          NOT NULL COMMENT 'Rule status 1=on 2=off',
    deleted           TINYINT          DEFAULT 2  NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time       BIGINT           DEFAULT 0  NOT NULL COMMENT 'Delete time',
    create_time       BIGINT           NOT NULL COMMENT 'Create time',
    update_time       BIGINT           NOT NULL COMMENT 'Update time'
);

-- app
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    namespace_id BIGINT                NOT NULL COMMENT 'Namespace id',
    name         VARCHAR(64)           COMMENT 'Name',
    `desc`       VARCHAR(256) DEFAULT '' NOT NULL COMMENT 'Description',
    deleted      TINYINT DEFAULT 2     NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time  BIGINT DEFAULT 0      NOT NULL COMMENT 'Delete time',
    create_time  BIGINT                NOT NULL COMMENT 'Create time',
    update_time  BIGINT                NOT NULL COMMENT 'Update time',
    CONSTRAINT udx_name UNIQUE (name)
);

CREATE INDEX app_idx_namespace_id_name ON `app` (`namespace_id`, `name`);

insert into `app` (id, namespace_id, name, `desc`, deleted, delete_time, create_time, update_time)
values  (1, 1, 'openjob', 'openjob', 2, 0, 1658473199, 1678796017);

-- delay
DROP TABLE IF EXISTS `delay`;
CREATE TABLE `delay`
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    pid                    BIGINT DEFAULT 0     NOT NULL COMMENT 'Parent id',
    cid                    BIGINT DEFAULT 0     NOT NULL COMMENT 'Child id',
    namespace_id           BIGINT                NOT NULL COMMENT 'Namespace',
    app_id                 BIGINT                NOT NULL COMMENT 'Application',
    name                   VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Name',
    description            VARCHAR(256) DEFAULT '' NOT NULL COMMENT 'Description',
    processor_info         VARCHAR(256) DEFAULT '' NOT NULL COMMENT 'Processor info',
    fail_retry_times       INT DEFAULT 3         NOT NULL COMMENT 'Fail retry times',
    fail_retry_interval    INT DEFAULT 3         NOT NULL COMMENT 'Fail retry interval(s)',
    execute_timeout        INT DEFAULT 60        NOT NULL COMMENT 'Execute timeout(s)',
    concurrency            INT DEFAULT 1         NOT NULL COMMENT 'Execute concurrency',
    blocking_size          INT DEFAULT 8         NOT NULL COMMENT 'Pull blocking size',
    topic                  VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Topic',
    fail_topic_enable      TINYINT DEFAULT 1     NOT NULL COMMENT 'Fail topic enable status',
    fail_topic_concurrency INT DEFAULT 1         NOT NULL COMMENT 'Fail topic execute concurrency',
    deleted                TINYINT DEFAULT 2     NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time            BIGINT DEFAULT 0      NOT NULL COMMENT 'Delete time',
    create_time            BIGINT                NOT NULL COMMENT 'Create time',
    update_time            BIGINT                NOT NULL COMMENT 'Update time',
    CONSTRAINT udx_topic UNIQUE (topic)
);

CREATE INDEX delay_idx_namespace_id_app_id ON `delay` (`namespace_id`, `app_id`);

CREATE INDEX delay_idx_namespace_id_name ON `delay` (`namespace_id`, `name`);

CREATE INDEX delay_idx_namespace_id_topic ON `delay` (`namespace_id`, `topic`);

-- delay_instance
DROP TABLE IF EXISTS `delay_instance`;
CREATE TABLE `delay_instance`
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    app_id           BIGINT                NOT NULL COMMENT 'Application',
    namespace_id     BIGINT                NOT NULL COMMENT 'Namespace',
    task_id          VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Task id',
    topic            VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Topic',
    delay_id         BIGINT                NOT NULL COMMENT 'Delay id',
    delay_params     CLOB                  NOT NULL COMMENT 'Delay params',
    delay_extra      TEXT                  NOT NULL COMMENT 'Delay extra',
    status           TINYINT               NOT NULL COMMENT 'Delay task status',
    fail_status      TINYINT DEFAULT 0     NOT NULL COMMENT 'Delay fail status',
    execute_time     BIGINT                NOT NULL COMMENT 'Execute time',
    complete_time    BIGINT DEFAULT 0      NOT NULL COMMENT 'Complete time',
    worker_address   VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Worker address',
    deleted          TINYINT DEFAULT 2     NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time      BIGINT DEFAULT 0      NOT NULL COMMENT 'Delete time',
    create_time      BIGINT                NOT NULL COMMENT 'Create time',
    update_time      BIGINT                NOT NULL COMMENT 'Update time',
    create_time_date INT DEFAULT 0         NOT NULL COMMENT 'Time date`20230527`',
    create_time_hour INT DEFAULT 0         NOT NULL COMMENT 'Time hour`2023052701`'
);

CREATE INDEX delay_instance_idx_create_time_day ON `delay_instance` (create_time, create_time_date);

CREATE INDEX delay_instance_idx_create_time_hour ON `delay_instance` (create_time, create_time_hour);

CREATE INDEX delay_instance_idx_delay_id ON `delay_instance` (delay_id);

CREATE INDEX delay_instance_idx_namespace_id_app_id_delay_id_create_time ON `delay_instance` (`namespace_id`, `app_id`, `delay_id`, `create_time`);

CREATE INDEX delay_instance_idx_namespace_id_create_time ON `delay_instance` (`namespace_id`, `create_time`);

-- delay_worker
DROP TABLE IF EXISTS `delay_worker`;
CREATE TABLE `delay_worker`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    topic       VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Topic',
    pull_size   INT DEFAULT 0 NOT NULL COMMENT 'Pull size',
    pull_time   BIGINT DEFAULT 0 NOT NULL COMMENT 'Pull time',
    create_time BIGINT NOT NULL COMMENT 'Create time',
    update_time BIGINT NOT NULL COMMENT 'Update time'
);

-- job
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    namespace_id         BIGINT NOT NULL COMMENT 'Namespace',
    app_id               BIGINT NOT NULL COMMENT 'Application',
    workflow_id          BIGINT DEFAULT 0 NOT NULL COMMENT 'Workflow id',
    name                 VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Name',
    description          VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Description',
    processor_type       VARCHAR(16) DEFAULT 'java' NOT NULL COMMENT 'Processor type java /shell/python',
    processor_info       TEXT NOT NULL COMMENT 'Processor info',
    execute_type         VARCHAR(16) DEFAULT 'standalone' NOT NULL COMMENT 'Execute type 1=standalone 2=broadcast 3=MR',
    params               VARCHAR(3096) DEFAULT '' NOT NULL COMMENT 'Params',
    params_type          VARCHAR(16) DEFAULT '' NOT NULL COMMENT 'Params type text/json/yaml',
    extend_params_type   VARCHAR(16) COMMENT 'Extend params type text/json/yaml',
    extend_params        VARCHAR(3096) DEFAULT '' NOT NULL COMMENT 'Extend params',
    fail_retry_times     INT NOT NULL COMMENT 'Fail retry times',
    fail_retry_interval  INT NOT NULL COMMENT 'Fail retry interval(s)',
    concurrency          INT DEFAULT 1 NOT NULL COMMENT 'Execute concurrency',
    execute_timeout      INT DEFAULT 0 NOT NULL COMMENT 'Execute timeout',
    time_expression_type VARCHAR(16) DEFAULT 'cron' NOT NULL COMMENT 'Time express time cron/second/delay',
    time_expression      VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Cron express type',
    execute_strategy     TINYINT DEFAULT 1 NOT NULL COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
    status               TINYINT DEFAULT 1 NOT NULL COMMENT '1=running 2=stop',
    next_execute_time    BIGINT NOT NULL COMMENT 'Next execute time',
    slots_id             INT NOT NULL COMMENT 'Slots id',
    deleted              TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time          BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time          BIGINT NOT NULL COMMENT 'Create time',
    update_time          BIGINT NOT NULL COMMENT 'Update time'
);

CREATE INDEX job_idx_namespace_id_app_id_name ON `job` (`namespace_id`, `app_id`, `name`);
CREATE INDEX job_idx_namespace_id_name ON `job` (`namespace_id`, `name`);

-- job_instance
DROP TABLE IF EXISTS `job_instance`;
CREATE TABLE `job_instance`
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    job_id               BIGINT NOT NULL COMMENT 'Job id',
    params               VARCHAR(3096) DEFAULT '' NOT NULL COMMENT 'Params',
    params_type          VARCHAR(16) DEFAULT '' NOT NULL COMMENT 'Params type text/json/yaml',
    extend_params_type   VARCHAR(16) COMMENT 'Extend params type text/json/yaml',
    extend_params        VARCHAR(3096) DEFAULT '' NOT NULL COMMENT 'Extend params',
    status               TINYINT DEFAULT 1 NOT NULL COMMENT 'Instance status',
    fail_status          TINYINT DEFAULT 0 NOT NULL COMMENT 'Fail status',
    slots_id             BIGINT NOT NULL COMMENT 'Slots id',
    workflow_id          BIGINT DEFAULT 0 NOT NULL COMMENT 'Workflow id',
    namespace_id         BIGINT NOT NULL COMMENT 'Namespace',
    app_id               BIGINT NOT NULL COMMENT 'Application',
    execute_time         BIGINT NOT NULL COMMENT 'Execute time',
    complete_time        BIGINT DEFAULT 0 NOT NULL COMMENT 'Complete time',
    last_report_time     BIGINT DEFAULT 0 NOT NULL COMMENT 'Last report time',
    dispatch_version     BIGINT DEFAULT 0 NOT NULL COMMENT 'Dispatch version',
    processor_type       VARCHAR(16) DEFAULT '' NOT NULL COMMENT 'Processor type',
    processor_info       TEXT NOT NULL COMMENT 'Processor info',
    execute_type         VARCHAR(16) DEFAULT '' NOT NULL COMMENT 'Execute time',
    fail_retry_times     INT NOT NULL COMMENT 'Fail retry times',
    fail_retry_interval  INT NOT NULL COMMENT 'Fail retry interval(s)',
    time_expression_type VARCHAR(16) DEFAULT '' NOT NULL COMMENT 'Time expression type',
    time_expression      VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Time expression',
    concurrency          INT DEFAULT 1 NOT NULL COMMENT 'Concurrency',
    execute_timeout      INT DEFAULT 0 NOT NULL COMMENT 'Execute timeout',
    worker_address       VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Worker address',
    execute_strategy     TINYINT DEFAULT 1 NOT NULL COMMENT 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency',
    execute_once         TINYINT DEFAULT 2 NOT NULL COMMENT 'Execute once, 1=yes 2=no',
    deleted              TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time          BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    update_time          BIGINT NOT NULL COMMENT 'Create time',
    create_time_date     INT DEFAULT 0 NOT NULL COMMENT 'Time date`20230527`',
    create_time_hour     INT DEFAULT 0 NOT NULL COMMENT 'Time hour`2023052701`',
    create_time          BIGINT NOT NULL COMMENT 'Update time'
);

CREATE INDEX job_instance_idx_create_time_day ON `job_instance` (`create_time`, `create_time_date`);
CREATE INDEX job_instance_idx_create_time_hour ON `job_instance` (`create_time`, `create_time_hour`);
CREATE INDEX job_instance_idx_execute_time_slots_id ON `job_instance` (`execute_time`, `slots_id`);
CREATE INDEX job_instance_idx_job_id_status ON `job_instance` (`job_id`, `status`);
CREATE INDEX job_instance_idx_last_report_time_slots_id ON `job_instance` (`last_report_time`, `slots_id`);
CREATE INDEX job_instance_idx_namespace_id_app_id_job_id_create_time ON `job_instance` (`namespace_id`, `app_id`, `job_id`, `create_time`);
CREATE INDEX job_instance_idx_namespace_id_create_time ON `job_instance` (`namespace_id`, `create_time`);

-- job_instance_log
DROP TABLE IF EXISTS `job_instance_log`;
CREATE TABLE `job_instance_log`
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    job_id          BIGINT NOT NULL COMMENT 'Job id',
    job_instance_id BIGINT NOT NULL COMMENT 'Job instance id',
    message         CLOB COMMENT 'Message',
    deleted         TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time     BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time     BIGINT NOT NULL COMMENT 'Create time',
    update_time     BIGINT DEFAULT 0 NOT NULL COMMENT 'Update time'
);

CREATE INDEX job_instance_log_idx_job_instance_id_create_time ON `job_instance_log` (`job_instance_id`, `create_time`);

-- job_instance_task
DROP TABLE IF EXISTS `job_instance_task`;
CREATE TABLE `job_instance_task`
(
    id               INT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    job_id           BIGINT NOT NULL COMMENT 'Job id',
    job_instance_id  BIGINT NOT NULL COMMENT 'Instance id',
    dispatch_version BIGINT DEFAULT 0 NOT NULL COMMENT 'Dispatch version',
    circle_id        BIGINT NOT NULL COMMENT 'Circle id',
    task_id          VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Task id',
    parent_task_id   VARCHAR(64) DEFAULT '0' NOT NULL COMMENT 'Parent task id',
    task_name        VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Task name',
    status           TINYINT DEFAULT 1 NOT NULL COMMENT 'Instance task status',
    result           CLOB COMMENT 'Task result',
    worker_address   VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Worker address',
    deleted          TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time      BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time      BIGINT COMMENT 'Create time',
    update_time      BIGINT COMMENT 'Update time'
);

CREATE INDEX job_instance_task_idx_job_instance_id_create_time ON `job_instance_task` (`job_instance_id`, `create_time`);
CREATE INDEX job_instance_task_idx_parent_task_id ON `job_instance_task` (`parent_task_id`);

-- job_slots
DROP TABLE IF EXISTS `job_slots`;
CREATE TABLE `job_slots`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    server_id   BIGINT NOT NULL COMMENT 'Server id',
    deleted     TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time BIGINT NOT NULL COMMENT 'Create time',
    update_time BIGINT
);
CREATE INDEX job_slots_idx_server_id ON `job_slots` (`server_id`);

insert into `job_slots` (id, server_id, deleted, delete_time, create_time, update_time)
values  (1, 1, 2, 0, 1655781998, 1692841647),
        (2, 1, 2, 0, 1655781999, 1692841647),
        (3, 1, 2, 0, 1655781999, 1692841647),
        (4, 1, 2, 0, 1655781999, 1692841647),
        (5, 1, 2, 0, 1655781999, 1692841647),
        (6, 1, 2, 0, 1655781999, 1692841647),
        (7, 1, 2, 0, 1655781999, 1692841647),
        (8, 1, 2, 0, 1655781999, 1692841647),
        (9, 1, 2, 0, 1655781999, 1692841647),
        (10, 1, 2, 0, 1655781999, 1692841647),
        (11, 1, 2, 0, 1655781999, 1692841647),
        (12, 1, 2, 0, 1655781999, 1692841647),
        (13, 1, 2, 0, 1655781999, 1692841647),
        (14, 1, 2, 0, 1655781999, 1692841647),
        (15, 1, 2, 0, 1655781999, 1692841647),
        (16, 1, 2, 0, 1655781999, 1692841647),
        (17, 1, 2, 0, 1655781999, 1692841647),
        (18, 1, 2, 0, 1655781999, 1692841647),
        (19, 1, 2, 0, 1655781999, 1692841647),
        (20, 1, 2, 0, 1655781999, 1692841647),
        (21, 1, 2, 0, 1655781999, 1692841647),
        (22, 1, 2, 0, 1655781999, 1692841647),
        (23, 1, 2, 0, 1655781999, 1692841647),
        (24, 1, 2, 0, 1655781999, 1692841647),
        (25, 1, 2, 0, 1655781999, 1692841647),
        (26, 1, 2, 0, 1655781999, 1692841647),
        (27, 1, 2, 0, 1655781999, 1692841647),
        (28, 1, 2, 0, 1655781999, 1692841647),
        (29, 1, 2, 0, 1655781999, 1692841647),
        (30, 1, 2, 0, 1655781999, 1692841647),
        (31, 1, 2, 0, 1655781999, 1692841647),
        (32, 1, 2, 0, 1655781999, 1692841647),
        (33, 1, 2, 0, 1655781999, 1692841647),
        (34, 1, 2, 0, 1655781999, 1692841647),
        (35, 1, 2, 0, 1655781999, 1692841647),
        (36, 1, 2, 0, 1655781999, 1692841647),
        (37, 1, 2, 0, 1655781999, 1692841647),
        (38, 1, 2, 0, 1655781999, 1692841647),
        (39, 1, 2, 0, 1655781999, 1692841647),
        (40, 1, 2, 0, 1655781999, 1692841647),
        (41, 1, 2, 0, 1655781999, 1692841647),
        (42, 1, 2, 0, 1655781999, 1692841647),
        (43, 1, 2, 0, 1655781999, 1692841647),
        (44, 1, 2, 0, 1655781999, 1692841647),
        (45, 1, 2, 0, 1655781999, 1692841647),
        (46, 1, 2, 0, 1655781999, 1692841647),
        (47, 1, 2, 0, 1655781999, 1692841647),
        (48, 1, 2, 0, 1655781999, 1692841647),
        (49, 1, 2, 0, 1655781999, 1692841647),
        (50, 1, 2, 0, 1655781999, 1692841647),
        (51, 1, 2, 0, 1655781999, 1692841647),
        (52, 1, 2, 0, 1655781999, 1692841647),
        (53, 1, 2, 0, 1655781999, 1692841647),
        (54, 1, 2, 0, 1655781999, 1692841647),
        (55, 1, 2, 0, 1655781999, 1692841647),
        (56, 1, 2, 0, 1655781999, 1692841647),
        (57, 1, 2, 0, 1655781999, 1692841647),
        (58, 1, 2, 0, 1655781999, 1692841647),
        (59, 1, 2, 0, 1655781999, 1692841647),
        (60, 1, 2, 0, 1655781999, 1692841647),
        (61, 1, 2, 0, 1655781999, 1692841647),
        (62, 1, 2, 0, 1655781999, 1692841647),
        (63, 1, 2, 0, 1655781999, 1692841647),
        (64, 1, 2, 0, 1655781999, 1692841647),
        (65, 1, 2, 0, 1655781999, 1692841647),
        (66, 1, 2, 0, 1655781999, 1692841647),
        (67, 1, 2, 0, 1655781999, 1692841647),
        (68, 1, 2, 0, 1655781999, 1692841647),
        (69, 1, 2, 0, 1655781999, 1692841647),
        (70, 1, 2, 0, 1655781999, 1692841647),
        (71, 1, 2, 0, 1655781999, 1692841647),
        (72, 1, 2, 0, 1655781999, 1692841647),
        (73, 1, 2, 0, 1655781999, 1692841647),
        (74, 1, 2, 0, 1655781999, 1692841647),
        (75, 1, 2, 0, 1655781999, 1692841647),
        (76, 1, 2, 0, 1655781999, 1692841647),
        (77, 1, 2, 0, 1655781999, 1692841647),
        (78, 1, 2, 0, 1655781999, 1692841647),
        (79, 1, 2, 0, 1655781999, 1692841647),
        (80, 1, 2, 0, 1655781999, 1692841647),
        (81, 1, 2, 0, 1655781999, 1692841647),
        (82, 1, 2, 0, 1655781999, 1692841647),
        (83, 1, 2, 0, 1655781999, 1692841647),
        (84, 1, 2, 0, 1655781999, 1692841647),
        (85, 1, 2, 0, 1655781999, 1692841647),
        (86, 1, 2, 0, 1655781999, 1692841647),
        (87, 1, 2, 0, 1655781999, 1692841647),
        (88, 1, 2, 0, 1655781999, 1692841647),
        (89, 1, 2, 0, 1655781999, 1692841647),
        (90, 1, 2, 0, 1655781999, 1692841647),
        (91, 1, 2, 0, 1655781999, 1692841647),
        (92, 1, 2, 0, 1655781999, 1692841647),
        (93, 1, 2, 0, 1655781999, 1692841647),
        (94, 1, 2, 0, 1655781999, 1692841647),
        (95, 1, 2, 0, 1655781999, 1692841647),
        (96, 1, 2, 0, 1655781999, 1692841647),
        (97, 1, 2, 0, 1655781999, 1692841647),
        (98, 1, 2, 0, 1655781999, 1692841647),
        (99, 1, 2, 0, 1655781999, 1692841647),
        (100, 1, 2, 0, 1655781999, 1692841647),
        (101, 1, 2, 0, 1655781999, 1692841647),
        (102, 1, 2, 0, 1655781999, 1692841647),
        (103, 1, 2, 0, 1655781999, 1692841647),
        (104, 1, 2, 0, 1655781999, 1692841647),
        (105, 1, 2, 0, 1655781999, 1692841647),
        (106, 1, 2, 0, 1655781999, 1692841647),
        (107, 1, 2, 0, 1655781999, 1692841647),
        (108, 1, 2, 0, 1655781999, 1692841647),
        (109, 1, 2, 0, 1655781999, 1692841647),
        (110, 1, 2, 0, 1655781999, 1692841647),
        (111, 1, 2, 0, 1655781999, 1692841647),
        (112, 1, 2, 0, 1655781999, 1692841647),
        (113, 1, 2, 0, 1655781999, 1692841647),
        (114, 1, 2, 0, 1655781999, 1692841647),
        (115, 1, 2, 0, 1655781999, 1692841647),
        (116, 1, 2, 0, 1655781999, 1692841647),
        (117, 1, 2, 0, 1655781999, 1692841647),
        (118, 1, 2, 0, 1655781999, 1692841647),
        (119, 1, 2, 0, 1655781999, 1692841647),
        (120, 1, 2, 0, 1655781999, 1692841647),
        (121, 1, 2, 0, 1655781999, 1692841647),
        (122, 1, 2, 0, 1655781999, 1692841647),
        (123, 1, 2, 0, 1655781999, 1692841647),
        (124, 1, 2, 0, 1655781999, 1692841647),
        (125, 1, 2, 0, 1655781999, 1692841647),
        (126, 1, 2, 0, 1655781999, 1692841647),
        (127, 1, 2, 0, 1655781999, 1692841647),
        (128, 1, 2, 0, 1655781999, 1692841647),
        (129, 1, 2, 0, 1655781999, 1692841647),
        (130, 1, 2, 0, 1655781999, 1692841647),
        (131, 1, 2, 0, 1655781999, 1692841647),
        (132, 1, 2, 0, 1655781999, 1692841647),
        (133, 1, 2, 0, 1655781999, 1692841647),
        (134, 1, 2, 0, 1655781999, 1692841647),
        (135, 1, 2, 0, 1655781999, 1692841647),
        (136, 1, 2, 0, 1655782000, 1692841647),
        (137, 1, 2, 0, 1655782000, 1692841647),
        (138, 1, 2, 0, 1655782000, 1692841647),
        (139, 1, 2, 0, 1655782000, 1692841647),
        (140, 1, 2, 0, 1655782000, 1692841647),
        (141, 1, 2, 0, 1655782000, 1692841647),
        (142, 1, 2, 0, 1655782000, 1692841647),
        (143, 1, 2, 0, 1655782000, 1692841647),
        (144, 1, 2, 0, 1655782000, 1692841647),
        (145, 1, 2, 0, 1655782000, 1692841647),
        (146, 1, 2, 0, 1655782000, 1692841647),
        (147, 1, 2, 0, 1655782000, 1692841647),
        (148, 1, 2, 0, 1655782000, 1692841647),
        (149, 1, 2, 0, 1655782000, 1692841647),
        (150, 1, 2, 0, 1655782000, 1692841647),
        (151, 1, 2, 0, 1655782000, 1692841647),
        (152, 1, 2, 0, 1655782000, 1692841647),
        (153, 1, 2, 0, 1655782000, 1692841647),
        (154, 1, 2, 0, 1655782000, 1692841647),
        (155, 1, 2, 0, 1655782000, 1692841647),
        (156, 1, 2, 0, 1655782000, 1692841647),
        (157, 1, 2, 0, 1655782000, 1692841647),
        (158, 1, 2, 0, 1655782000, 1692841647),
        (159, 1, 2, 0, 1655782000, 1692841647),
        (160, 1, 2, 0, 1655782000, 1692841647),
        (161, 1, 2, 0, 1655782000, 1692841647),
        (162, 1, 2, 0, 1655782000, 1692841647),
        (163, 1, 2, 0, 1655782000, 1692841647),
        (164, 1, 2, 0, 1655782000, 1692841647),
        (165, 1, 2, 0, 1655782000, 1692841647),
        (166, 1, 2, 0, 1655782000, 1692841647),
        (167, 1, 2, 0, 1655782000, 1692841647),
        (168, 1, 2, 0, 1655782000, 1692841647),
        (169, 1, 2, 0, 1655782000, 1692841647),
        (170, 1, 2, 0, 1655782000, 1692841647),
        (171, 1, 2, 0, 1655782000, 1692841647),
        (172, 1, 2, 0, 1655782000, 1692841647),
        (173, 1, 2, 0, 1655782000, 1692841647),
        (174, 1, 2, 0, 1655782000, 1692841647),
        (175, 1, 2, 0, 1655782000, 1692841647),
        (176, 1, 2, 0, 1655782000, 1692841647),
        (177, 1, 2, 0, 1655782000, 1692841647),
        (178, 1, 2, 0, 1655782000, 1692841647),
        (179, 1, 2, 0, 1655782000, 1692841647),
        (180, 1, 2, 0, 1655782000, 1692841647),
        (181, 1, 2, 0, 1655782000, 1692841647),
        (182, 1, 2, 0, 1655782000, 1692841647),
        (183, 1, 2, 0, 1655782000, 1692841647),
        (184, 1, 2, 0, 1655782000, 1692841647),
        (185, 1, 2, 0, 1655782000, 1692841647),
        (186, 1, 2, 0, 1655782000, 1692841647),
        (187, 1, 2, 0, 1655782000, 1692841647),
        (188, 1, 2, 0, 1655782000, 1692841647),
        (189, 1, 2, 0, 1655782000, 1692841647),
        (190, 1, 2, 0, 1655782000, 1692841647),
        (191, 1, 2, 0, 1655782000, 1692841647),
        (192, 1, 2, 0, 1655782000, 1692841647),
        (193, 1, 2, 0, 1655782000, 1692841647),
        (194, 1, 2, 0, 1655782000, 1692841647),
        (195, 1, 2, 0, 1655782000, 1692841647),
        (196, 1, 2, 0, 1655782000, 1692841647),
        (197, 1, 2, 0, 1655782000, 1692841647),
        (198, 1, 2, 0, 1655782000, 1692841647),
        (199, 1, 2, 0, 1655782000, 1692841647),
        (200, 1, 2, 0, 1655782000, 1692841647),
        (201, 1, 2, 0, 1655782000, 1692841647),
        (202, 1, 2, 0, 1655782000, 1692841647),
        (203, 1, 2, 0, 1655782000, 1692841647),
        (204, 1, 2, 0, 1655782000, 1692841647),
        (205, 1, 2, 0, 1655782000, 1692841647),
        (206, 1, 2, 0, 1655782000, 1692841647),
        (207, 1, 2, 0, 1655782000, 1692841647),
        (208, 1, 2, 0, 1655782000, 1692841647),
        (209, 1, 2, 0, 1655782000, 1692841647),
        (210, 1, 2, 0, 1655782000, 1692841647),
        (211, 1, 2, 0, 1655782000, 1692841647),
        (212, 1, 2, 0, 1655782000, 1692841647),
        (213, 1, 2, 0, 1655782000, 1692841647),
        (214, 1, 2, 0, 1655782000, 1692841647),
        (215, 1, 2, 0, 1655782000, 1692841647),
        (216, 1, 2, 0, 1655782000, 1692841647),
        (217, 1, 2, 0, 1655782000, 1692841647),
        (218, 1, 2, 0, 1655782000, 1692841647),
        (219, 1, 2, 0, 1655782000, 1692841647),
        (220, 1, 2, 0, 1655782000, 1692841647),
        (221, 1, 2, 0, 1655782000, 1692841647),
        (222, 1, 2, 0, 1655782000, 1692841647),
        (223, 1, 2, 0, 1655782000, 1692841647),
        (224, 1, 2, 0, 1655782000, 1692841647),
        (225, 1, 2, 0, 1655782000, 1692841647),
        (226, 1, 2, 0, 1655782000, 1692841647),
        (227, 1, 2, 0, 1655782000, 1692841647),
        (228, 1, 2, 0, 1655782000, 1692841647),
        (229, 1, 2, 0, 1655782000, 1692841647),
        (230, 1, 2, 0, 1655782000, 1692841647),
        (231, 1, 2, 0, 1655782000, 1692841647),
        (232, 1, 2, 0, 1655782000, 1692841647),
        (233, 1, 2, 0, 1655782000, 1692841647),
        (234, 1, 2, 0, 1655782000, 1692841647),
        (235, 1, 2, 0, 1655782000, 1692841647),
        (236, 1, 2, 0, 1655782000, 1692841647),
        (237, 1, 2, 0, 1655782000, 1692841647),
        (238, 1, 2, 0, 1655782000, 1692841647),
        (239, 1, 2, 0, 1655782000, 1692841647),
        (240, 1, 2, 0, 1655782000, 1692841647),
        (241, 1, 2, 0, 1655782000, 1692841647),
        (242, 1, 2, 0, 1655782000, 1692841647),
        (243, 1, 2, 0, 1655782000, 1692841647),
        (244, 1, 2, 0, 1655782000, 1692841647),
        (245, 1, 2, 0, 1655782000, 1692841647),
        (246, 1, 2, 0, 1655782000, 1692841647),
        (247, 1, 2, 0, 1655782000, 1692841647),
        (248, 1, 2, 0, 1655782000, 1692841647),
        (249, 1, 2, 0, 1655782000, 1692841647),
        (250, 1, 2, 0, 1655782000, 1692841647),
        (251, 1, 2, 0, 1655782000, 1692841647),
        (252, 1, 2, 0, 1655782000, 1692841647),
        (253, 1, 2, 0, 1655782000, 1692841647),
        (254, 1, 2, 0, 1655782000, 1692841647),
        (255, 1, 2, 0, 1655782000, 1692841647),
        (256, 1, 2, 0, 1655782000, 1692841647);

-- namespace
DROP TABLE IF EXISTS `namespace`;
CREATE TABLE `namespace`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    name        VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Name',
    uuid        VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Unique id',
    deleted     TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time BIGINT NOT NULL COMMENT 'Create time',
    update_time BIGINT
);
CREATE INDEX namespace_idx_name ON `namespace` (`name`);

insert into `namespace` (id, name, uuid, deleted, delete_time, create_time, update_time)
values  (1, 'default', 'a65d3fe5-258d-43e3-a5e6-dc12c4879ed6', 2, 0, 1657528102, 1683203496);


-- processor_log
DROP TABLE IF EXISTS `processor_log`;
CREATE TABLE `processor_log`
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    task_id        VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Task id',
    worker_address VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Worker address',
    content        CLOB NOT NULL COMMENT 'Content',
    time           BIGINT NOT NULL COMMENT 'Time'
);
CREATE INDEX processor_log_idx_task_id ON `processor_log` (`task_id`);
CREATE INDEX processor_log_idx_task_unique_id_time ON `processor_log` (`time`);

-- server
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server`
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    ip           VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Server ip',
    akka_address VARCHAR(32) DEFAULT '' NOT NULL COMMENT 'Akka address like `127.0.0.1:25520`',
    status       TINYINT DEFAULT 1 NOT NULL COMMENT 'Server status 1=ok 2=fail',
    deleted      TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time  BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time  BIGINT NOT NULL COMMENT 'Create time',
    update_time  BIGINT,
    CONSTRAINT udx_akka_address UNIQUE (akka_address)
);

-- server_reports
DROP TABLE IF EXISTS `server_reports`;
CREATE TABLE `server_reports`
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    server_id        BIGINT NOT NULL COMMENT 'Server id',
    report_server_id BIGINT NOT NULL COMMENT 'Report server id',
    status           TINYINT DEFAULT 1 NOT NULL COMMENT 'Report status',
    deleted          TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time      BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time      BIGINT NOT NULL COMMENT 'Create time',
    update_time      BIGINT
);
CREATE INDEX server_reports_idx_create_time_server_id ON `server_reports` (`create_time`, `server_id`);

-- system
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system`
(
    id                     INT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    version                VARCHAR(16) DEFAULT '0' NOT NULL COMMENT 'Version',
    cluster_version        BIGINT DEFAULT 1 NOT NULL COMMENT 'Cluster version',
    cluster_delay_version  BIGINT DEFAULT 1 NOT NULL COMMENT 'Cluster delay version',
    worker_supervisor_slot INT DEFAULT 16 NOT NULL COMMENT 'Worker supervisor slot',
    delay_zset_slot        INT DEFAULT 4 NOT NULL COMMENT 'Delay zset slot',
    delay_fail_zset_slot   INT DEFAULT 2 NOT NULL COMMENT 'Delay fail zset slot',
    delay_add_list_slot    INT DEFAULT 2 NOT NULL COMMENT 'Delay add list slot',
    delay_status_list_slot INT DEFAULT 2 NOT NULL COMMENT 'Delay status list slot',
    delay_delete_list_slot INT DEFAULT 1 NOT NULL COMMENT 'Delay delete list slot',
    max_slot               INT DEFAULT 256 NOT NULL COMMENT 'System max slot',
    job_keep_days          INT DEFAULT 30 NOT NULL COMMENT 'Job keep days',
    delay_keep_days        INT DEFAULT 30 NOT NULL COMMENT 'Delay keep days',
    server_keep_days       INT DEFAULT 30 NOT NULL COMMENT 'Server keep days',
    worker_keep_days       INT DEFAULT 30 NOT NULL COMMENT 'Worker keep days',
    deleted                TINYINT DEFAULT 2 NOT NULL COMMENT 'Delete status. 1=yes 2=no',
    delete_time            BIGINT DEFAULT 0 NOT NULL COMMENT 'Delete time',
    create_time            BIGINT NOT NULL COMMENT 'Create time',
    update_time            BIGINT
);

insert into `system` (id, version, cluster_version, cluster_delay_version, worker_supervisor_slot, delay_zset_slot, delay_fail_zset_slot, delay_add_list_slot, delay_status_list_slot, delay_delete_list_slot, max_slot, job_keep_days, delay_keep_days, server_keep_days, worker_keep_days, deleted, delete_time, create_time, update_time)
values  (1, '1.0.0', 440, 8, 32, 2, 2, 2, 2, 1, 256, 1, 7, 90, 180, 2, 0, 1663590330, 1663590330);

-- task
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    job_id         BIGINT NOT NULL COMMENT 'Job id',
    instance_id    BIGINT NOT NULL COMMENT 'Instance id',
    circle_id      BIGINT DEFAULT 0 NOT NULL COMMENT 'Circle id',
    task_id        VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Task id',
    task_name      VARCHAR(128) DEFAULT '' NOT NULL COMMENT 'Task name',
    task_parent_id VARCHAR(64) DEFAULT '0' NOT NULL COMMENT 'Task parent id',
    status         TINYINT DEFAULT 1 NOT NULL COMMENT 'Status',
    worker_address VARCHAR(64) DEFAULT '' NOT NULL COMMENT 'Worker address',
    result         CLOB COMMENT 'Task result',
    task_body      BLOB COMMENT 'Task body',
    create_time    BIGINT NOT NULL COMMENT 'Create time',
    update_time    BIGINT
);
CREATE INDEX task_idx_instance_id_circle_id ON `task` (`instance_id`, `circle_id`);

-- worker
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker`
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    app_id              BIGINT NOT NULL,
    namespace_id        BIGINT NOT NULL,
    app_name            VARCHAR(128) NOT NULL DEFAULT '',
    worker_key          VARCHAR(64) NOT NULL DEFAULT '',
    slots_id            BIGINT NOT NULL,
    address             VARCHAR(32) NOT NULL DEFAULT '',
    protocol_type       VARCHAR(8) NOT NULL DEFAULT '',
    version             VARCHAR(32) NOT NULL DEFAULT '',
    last_heartbeat_time BIGINT NOT NULL,
    status              TINYINT NOT NULL DEFAULT 1,
    metric              VARCHAR(1024) NOT NULL DEFAULT '',
    deleted             TINYINT NOT NULL DEFAULT 2,
    delete_time         BIGINT NOT NULL DEFAULT 0,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,
    UNIQUE(address)
);

CREATE INDEX worker_idx_namespace_id_app_id ON `worker`(namespace_id, app_id);

