-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
CREATE TABLE "admin_permission" (
                                    "id" NUMBER(20,0) NOT NULL ,
                                    "pid" NUMBER(20,0),
                                    "type" NUMBER(4,0),
                                    "name" NVARCHAR2(48),
                                    "path" NVARCHAR2(86),
                                    "meta" CLOB,
                                    "hidden" NUMBER(4,0),
                                    "sort" NUMBER(11,0),
                                    "deleted" NUMBER(4,0) ,
                                    "delete_time" NUMBER(20,0) ,
                                    "update_time" NUMBER(20,0) ,
                                    "create_time" NUMBER(20,0)
);
COMMENT ON COLUMN "admin_permission"."id" IS 'PK';
COMMENT ON COLUMN "admin_permission"."pid" IS 'Parent ID';
COMMENT ON COLUMN "admin_permission"."type" IS 'Type. 1=menu 2=perm';
COMMENT ON COLUMN "admin_permission"."name" IS 'Menu name';
COMMENT ON COLUMN "admin_permission"."path" IS 'Route path or API path';
COMMENT ON COLUMN "admin_permission"."meta" IS 'Extra meta data. JSON object: {icon:xx,title:some.name}';
COMMENT ON COLUMN "admin_permission"."hidden" IS 'Hidden status. 1=yes 2=no';
COMMENT ON COLUMN "admin_permission"."sort" IS 'Sort value';
COMMENT ON COLUMN "admin_permission"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "admin_permission"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "admin_permission"."update_time" IS 'Update time';
COMMENT ON COLUMN "admin_permission"."create_time" IS 'Create time';
COMMENT ON TABLE "admin_permission" IS 'Admin user permissions';

-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('1', '0', '1', 'dashboard', '/dashboard', '{"icon": "iconfont icon-shouye", "roles": ["admin"], "title": "message.router.dashboard", "isLink": "", "isAffix": false, "isIframe": false, "component": "/home/index.vue", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('20', '0', '1', 'namespace', '/admin/namespace/list', '{"icon": "ele-Edit", "roles": ["admin"], "title": "message.router.namespace", "isLink": "", "isAffix": false, "isIframe": false, "component": "/namespace/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('40', '0', '1', 'application', '/admin/app/list', '{"icon": "ele-Operation", "roles": ["admin"], "title": "message.router.application", "isLink": "", "isAffix": false, "isIframe": false, "component": "/app/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('60', '0', '1', 'cronJob', '/a', '{"icon": "ele-AlarmClock", "roles": ["admin"], "title": "message.router.cronJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('61', '60', '1', 'cronJobJob', '/admin/job/list', '{"icon": "ele-Suitcase", "roles": ["admin"], "title": "message.router.cronJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('62', '60', '1', 'cronJobInstance', '/admin/job-instance/list', '{"icon": "ele-Document", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/instance/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('63', '60', '1', 'cronJobPage', '/admin/job/page', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/page", "isKeepAlive": true}', '1', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('80', '0', '1', 'delayJob', '/b', '{"icon": "ele-Clock", "roles": ["admin"], "title": "message.router.delayJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('81', '80', '1', 'delayJobDelay', '/admin/delay/list', '{"icon": "ele-SetUp", "roles": ["admin"], "title": "message.router.delayJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/job/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('82', '80', '1', 'delayJobInstance', '/admin/delay-instance/list', '{"icon": "ele-DocumentChecked", "roles": ["admin"], "title": "message.router.delayJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/instance/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('200', '0', '1', 'cluster', '/c', '{"icon": "ele-SuitcaseLine", "roles": ["admin"], "title": "message.router.clusterManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('201', '200', '1', 'ClusterNode', '/admin/cluster-node/list', '{"icon": "ele-Notification", "roles": ["admin"], "title": "message.router.clusterNode", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/node/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('202', '200', '1', 'ClusterWorker', '/admin/cluster-worker/list', '{"icon": "ele-Monitor", "roles": ["admin"], "title": "message.router.clusterWorker", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/worker/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('300', '0', '1', 'system', '/d', '{"icon": "ele-Setting", "roles": ["admin"], "title": "message.router.systemManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('301', '300', '1', 'SystemConfiguration', '/admin/system-config/list', '{"icon": "ele-CreditCard", "roles": ["admin"], "title": "message.router.systemConfiguration", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/config/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('302', '300', '1', 'SystemSlots', '/admin/system-slots/list', '{"icon": "ele-Connection", "roles": ["admin"], "title": "message.router.systemSlots", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/slots/index", "isKeepAlive": true}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('1000', '0', '2', 'namespace.add', '/admin/namespace/add', '{}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('1001', '0', '2', 'namespace.delete', '/admin/namespace/delete', '{}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('1002', '0', '2', 'namespace.update', '/admin/namespace/update', '{}', '2', '0', '2', '0', '1669972320', '1669972320');
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES ('1003', '0', '2', 'namespace.update.status', '/admin/namespace/update-status', '{}', '2', '0', '2', '0', '1669972320', '1669972320');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
CREATE TABLE "admin_role" (
                              "id" NUMBER(20,0) NOT NULL ,
                              "name" NVARCHAR2(48),
                              "desc" NVARCHAR2(128),
                              "admin" NUMBER(4,0),
                              "menu_ids" CLOB,
                              "perm_ids" CLOB,
                              "namespace_ids" CLOB,
                              "app_ids" CLOB,
                              "deleted" NUMBER(4,0) ,
                              "delete_time" NUMBER(20,0) ,
                              "update_time" NUMBER(20,0) ,
                              "create_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "admin_role"."id" IS 'PK';
COMMENT ON COLUMN "admin_role"."name" IS 'role name';
COMMENT ON COLUMN "admin_role"."desc" IS 'Description';
COMMENT ON COLUMN "admin_role"."admin" IS 'Is supper admin. 1=yes 2=no';
COMMENT ON COLUMN "admin_role"."menu_ids" IS 'Menu ids for role. JSON array';
COMMENT ON COLUMN "admin_role"."perm_ids" IS 'Permission ids for role. JSON array';
COMMENT ON COLUMN "admin_role"."namespace_ids" IS 'namespaces Ids. JSON array';
COMMENT ON COLUMN "admin_role"."app_ids" IS 'app ids. JSON array';
COMMENT ON COLUMN "admin_role"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "admin_role"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "admin_role"."update_time" IS 'Update time';
COMMENT ON COLUMN "admin_role"."create_time" IS 'Create time';
COMMENT ON TABLE "admin_role" IS 'Admin roles with perms';

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO "admin_role" ("id", "name", "desc", "admin", "menu_ids", "perm_ids", "namespace_ids", "app_ids", "deleted", "delete_time", "update_time", "create_time") VALUES ('1', 'Admin', 'Administrator role', '1', '[]', '[]', '[]', '[]', '2', '0', '1670255999', '1670255999');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
CREATE TABLE "admin_user" (
                              "id" NUMBER(20,0) NOT NULL ,
                              "username" NVARCHAR2(48),
                              "nickname" NVARCHAR2(64),
                              "passwd" NVARCHAR2(128),
                              "session_key" NVARCHAR2(128),
                              "session_expire_at" NUMBER(20,0),
                              "token" NVARCHAR2(64),
                              "role_ids" CLOB,
                              "deleted" NUMBER(4,0) ,
                              "delete_time" NUMBER(20,0) ,
                              "update_time" NUMBER(20,0) ,
                              "create_time" NUMBER(20,0)
);
COMMENT ON COLUMN "admin_user"."id" IS 'PK';
COMMENT ON COLUMN "admin_user"."username" IS 'User name';
COMMENT ON COLUMN "admin_user"."nickname" IS 'Nickname';
COMMENT ON COLUMN "admin_user"."passwd" IS 'Password';
COMMENT ON COLUMN "admin_user"."session_key" IS 'Session key';
COMMENT ON COLUMN "admin_user"."session_expire_at" IS 'Session expire at';
COMMENT ON COLUMN "admin_user"."token" IS 'Api auth token';
COMMENT ON COLUMN "admin_user"."role_ids" IS 'role IDs. JSON: [1,2]';
COMMENT ON COLUMN "admin_user"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "admin_user"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "admin_user"."update_time" IS 'Update time';
COMMENT ON COLUMN "admin_user"."create_time" IS 'Create time';
COMMENT ON TABLE "admin_user" IS 'Job admin users';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO "admin_user" ("id", "username", "nickname", "passwd", "session_key", "session_expire_at", "token", "role_ids", "deleted", "delete_time", "update_time", "create_time") VALUES ('1', 'openjob', 'Administrator', '144f039395e24903a08926b978340a1554660c4da724d0700e3dceac9573675f', '79f74383e2c92ae01e172ced4c9267d5', '0', '79f74383e2c92ae01e172ced4c9267d5', '[1]', '2', '0', '1670255999', '1670255999');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for app
-- ----------------------------
CREATE TABLE "app" (
                       "id" NUMBER(20,0) NOT NULL ,
                       "namespace_id" NUMBER(20,0),
                       "name" NVARCHAR2(64),
                       "desc" NVARCHAR2(256),
                       "deleted" NUMBER(4,0) ,
                       "delete_time" NUMBER(20,0) ,
                       "create_time" NUMBER(20,0) ,
                       "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "app"."id" IS 'PK';
COMMENT ON COLUMN "app"."namespace_id" IS 'Namespace id';
COMMENT ON COLUMN "app"."name" IS 'Name';
COMMENT ON COLUMN "app"."desc" IS 'Description';
COMMENT ON COLUMN "app"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "app"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "app"."create_time" IS 'Create time';
COMMENT ON COLUMN "app"."update_time" IS 'Update time';

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO "app" ("id", "namespace_id", "name", "desc", "deleted", "delete_time", "create_time", "update_time") VALUES ('1', '1', 'openjob', 'openjob', '2', '0', '1658473199', '1678796017');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for delay
-- ----------------------------
CREATE TABLE "delay" (
                         "id" NUMBER(20,0) NOT NULL ,
                         "pid" NUMBER(20,0),
                         "cid" NUMBER(20,0),
                         "namespace_id" NUMBER(20,0),
                         "app_id" NUMBER(20,0),
                         "name" NVARCHAR2(128),
                         "description" NVARCHAR2(256),
                         "processor_info" NVARCHAR2(256),
                         "fail_retry_times" NUMBER(11,0),
                         "fail_retry_interval" NUMBER(11,0),
                         "execute_timeout" NUMBER(11,0),
                         "concurrency" NUMBER(11,0),
                         "blocking_size" NUMBER(11,0),
                         "topic" NVARCHAR2(128),
                         "fail_topic_enable" NUMBER(4,0),
                         "fail_topic_concurrency" NUMBER(11,0),
                         "deleted" NUMBER(4,0) ,
                         "delete_time" NUMBER(20,0) ,
                         "create_time" NUMBER(20,0) ,
                         "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "delay"."id" IS 'PK';
COMMENT ON COLUMN "delay"."pid" IS 'Parent id';
COMMENT ON COLUMN "delay"."cid" IS 'Child id';
COMMENT ON COLUMN "delay"."namespace_id" IS 'Namespace';
COMMENT ON COLUMN "delay"."app_id" IS 'Application';
COMMENT ON COLUMN "delay"."name" IS 'Name';
COMMENT ON COLUMN "delay"."description" IS 'Description';
COMMENT ON COLUMN "delay"."processor_info" IS 'Processor info';
COMMENT ON COLUMN "delay"."fail_retry_times" IS 'Fail retry times';
COMMENT ON COLUMN "delay"."fail_retry_interval" IS 'Fail retry interval(s)';
COMMENT ON COLUMN "delay"."execute_timeout" IS 'Execute timeout(s)';
COMMENT ON COLUMN "delay"."concurrency" IS 'Execute concurrency';
COMMENT ON COLUMN "delay"."blocking_size" IS 'Pull blocking size';
COMMENT ON COLUMN "delay"."topic" IS 'Topic';
COMMENT ON COLUMN "delay"."fail_topic_enable" IS 'Fail topic eanble status';
COMMENT ON COLUMN "delay"."fail_topic_concurrency" IS 'Fail topic execute concurrency';
COMMENT ON COLUMN "delay"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "delay"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "delay"."create_time" IS 'Create time';
COMMENT ON COLUMN "delay"."update_time" IS 'Update time';

-- ----------------------------
-- Records of delay
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for delay_instance
-- ----------------------------
CREATE TABLE "delay_instance" (
                                  "id" NUMBER(20,0) NOT NULL ,
                                  "app_id" NUMBER(20,0),
                                  "namespace_id" NUMBER(20,0),
                                  "task_id" NVARCHAR2(64),
                                  "topic" NVARCHAR2(128),
                                  "delay_id" NUMBER(20,0),
                                  "delay_params" NCLOB,
                                  "delay_extra" NCLOB,
                                  "status" NUMBER(4,0),
                                  "execute_time" NUMBER(20,0),
                                  "complete_time" NUMBER(20,0),
                                  "worker_address" NVARCHAR2(32),
                                  "deleted" NUMBER(4,0) ,
                                  "delete_time" NUMBER(20,0) ,
                                  "create_time" NUMBER(20,0) ,
                                  "update_time" NUMBER(20,0) ,
                                  "create_time_date" NUMBER(11,0),
                                  "create_time_hour" NUMBER(11,0)
);
COMMENT ON COLUMN "delay_instance"."id" IS 'PK';
COMMENT ON COLUMN "delay_instance"."app_id" IS 'Application';
COMMENT ON COLUMN "delay_instance"."namespace_id" IS 'Namespace';
COMMENT ON COLUMN "delay_instance"."task_id" IS 'Task id';
COMMENT ON COLUMN "delay_instance"."topic" IS 'Topic';
COMMENT ON COLUMN "delay_instance"."delay_id" IS 'Delay id';
COMMENT ON COLUMN "delay_instance"."delay_params" IS 'Delay params';
COMMENT ON COLUMN "delay_instance"."delay_extra" IS 'Delay extra';
COMMENT ON COLUMN "delay_instance"."status" IS 'Delay task status';
COMMENT ON COLUMN "delay_instance"."execute_time" IS 'Execute time';
COMMENT ON COLUMN "delay_instance"."complete_time" IS 'Complete time';
COMMENT ON COLUMN "delay_instance"."worker_address" IS 'Worker address';
COMMENT ON COLUMN "delay_instance"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "delay_instance"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "delay_instance"."create_time" IS 'Create time';
COMMENT ON COLUMN "delay_instance"."update_time" IS 'Update time';
COMMENT ON COLUMN "delay_instance"."create_time_date" IS 'Time date`20230527`';
COMMENT ON COLUMN "delay_instance"."create_time_hour" IS 'Time hour`2023052701`';

-- ----------------------------
-- Records of delay_instance
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for job
-- ----------------------------
CREATE TABLE "job" (
                       "id" NUMBER(20,0) NOT NULL ,
                       "namespace_id" NUMBER(20,0),
                       "app_id" NUMBER(20,0),
                       "workflow_id" NUMBER(20,0),
                       "name" NVARCHAR2(32),
                       "description" NVARCHAR2(128),
                       "processor_type" NVARCHAR2(16),
                       "processor_info" NCLOB,
                       "execute_type" NVARCHAR2(16),
                       "params" NCLOB,
                       "params_type" NVARCHAR2(16),
                       "extend_params_type" NVARCHAR2(16),
                       "extend_params" NCLOB,
                       "fail_retry_times" NUMBER(11,0),
                       "fail_retry_interval" NUMBER(11,0),
                       "concurrency" NUMBER(11,0),
                       "time_expression_type" NVARCHAR2(16),
                       "time_expression" NVARCHAR2(32),
                       "execute_strategy" NUMBER(4,0),
                       "status" NUMBER(4,0),
                       "next_execute_time" NUMBER(20,0),
                       "slots_id" NUMBER(11,0),
                       "deleted" NUMBER(4,0) ,
                       "delete_time" NUMBER(20,0) ,
                       "create_time" NUMBER(20,0) ,
                       "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "job"."id" IS 'PK';
COMMENT ON COLUMN "job"."namespace_id" IS 'Namespace';
COMMENT ON COLUMN "job"."app_id" IS 'Application';
COMMENT ON COLUMN "job"."workflow_id" IS 'Workflow id';
COMMENT ON COLUMN "job"."name" IS 'Name';
COMMENT ON COLUMN "job"."description" IS 'Description';
COMMENT ON COLUMN "job"."processor_type" IS 'Processor type java /shell/python';
COMMENT ON COLUMN "job"."processor_info" IS 'Processor info';
COMMENT ON COLUMN "job"."execute_type" IS 'Execute type 1=standalone 2=broadcast 3=MR';
COMMENT ON COLUMN "job"."params" IS 'Params';
COMMENT ON COLUMN "job"."params_type" IS 'Params type text/json/yaml';
COMMENT ON COLUMN "job"."extend_params_type" IS 'Extend params type text/json/yaml';
COMMENT ON COLUMN "job"."extend_params" IS 'Extend params';
COMMENT ON COLUMN "job"."fail_retry_times" IS 'Fail retry times';
COMMENT ON COLUMN "job"."fail_retry_interval" IS 'Fail retry interval(s)';
COMMENT ON COLUMN "job"."concurrency" IS 'Execute concurrency';
COMMENT ON COLUMN "job"."time_expression_type" IS 'Time express time cron/second/delay';
COMMENT ON COLUMN "job"."time_expression" IS 'Cron express type';
COMMENT ON COLUMN "job"."execute_strategy" IS 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency';
COMMENT ON COLUMN "job"."status" IS '1=running 2=stop';
COMMENT ON COLUMN "job"."next_execute_time" IS 'Next execute time';
COMMENT ON COLUMN "job"."slots_id" IS 'Slots id';
COMMENT ON COLUMN "job"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job"."create_time" IS 'Create time';
COMMENT ON COLUMN "job"."update_time" IS 'Update time';

-- ----------------------------
-- Records of job
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for job_instance
-- ----------------------------
CREATE TABLE "job_instance" (
                                "id" NUMBER(20,0) NOT NULL ,
                                "job_id" NUMBER(20,0),
                                "params" NCLOB,
                                "params_type" NVARCHAR2(16),
                                "extend_params_type" NVARCHAR2(16),
                                "extend_params" NCLOB,
                                "status" NUMBER(4,0),
                                "slots_id" NUMBER(20,0),
                                "workflow_id" NUMBER(20,0),
                                "namespace_id" NUMBER(20,0),
                                "app_id" NUMBER(20,0),
                                "execute_time" NUMBER(20,0),
                                "complete_time" NUMBER(20,0),
                                "last_report_time" NUMBER(20,0),
                                "processor_type" NVARCHAR2(16),
                                "processor_info" NCLOB,
                                "execute_type" NVARCHAR2(16),
                                "fail_retry_times" NUMBER(11,0),
                                "fail_retry_interval" NUMBER(11,0),
                                "time_expression_type" NVARCHAR2(16),
                                "time_expression" NVARCHAR2(32),
                                "concurrency" NUMBER(11,0),
                                "worker_address" NVARCHAR2(32),
                                "execute_strategy" NUMBER(4,0),
                                "deleted" NUMBER(4,0) ,
                                "delete_time" NUMBER(20,0) ,
                                "update_time" NUMBER(20,0) ,
                                "create_time_date" NUMBER(11,0),
                                "create_time_hour" NUMBER(11,0),
                                "create_time" NUMBER(20,0)
);
COMMENT ON COLUMN "job_instance"."id" IS 'PK';
COMMENT ON COLUMN "job_instance"."job_id" IS 'Job id';
COMMENT ON COLUMN "job_instance"."params" IS 'Params';
COMMENT ON COLUMN "job_instance"."params_type" IS 'Params type text/json/yaml';
COMMENT ON COLUMN "job_instance"."extend_params_type" IS 'Extend params type text/json/yaml';
COMMENT ON COLUMN "job_instance"."extend_params" IS 'Extend params';
COMMENT ON COLUMN "job_instance"."status" IS 'Instance status';
COMMENT ON COLUMN "job_instance"."slots_id" IS 'Slots id';
COMMENT ON COLUMN "job_instance"."workflow_id" IS 'Workflow id';
COMMENT ON COLUMN "job_instance"."namespace_id" IS 'Namespace';
COMMENT ON COLUMN "job_instance"."app_id" IS 'Application';
COMMENT ON COLUMN "job_instance"."execute_time" IS 'Execute time';
COMMENT ON COLUMN "job_instance"."complete_time" IS 'Complete time';
COMMENT ON COLUMN "job_instance"."last_report_time" IS 'Last report time';
COMMENT ON COLUMN "job_instance"."processor_type" IS 'Processor type';
COMMENT ON COLUMN "job_instance"."processor_info" IS 'Processor info';
COMMENT ON COLUMN "job_instance"."execute_type" IS 'Execute time';
COMMENT ON COLUMN "job_instance"."fail_retry_times" IS 'Fail retry times';
COMMENT ON COLUMN "job_instance"."fail_retry_interval" IS 'Fail retry interval(s)';
COMMENT ON COLUMN "job_instance"."time_expression_type" IS 'Time expression type';
COMMENT ON COLUMN "job_instance"."time_expression" IS 'TIme expression';
COMMENT ON COLUMN "job_instance"."concurrency" IS 'Concurrency';
COMMENT ON COLUMN "job_instance"."worker_address" IS 'Worker address';
COMMENT ON COLUMN "job_instance"."execute_strategy" IS 'Execute strategy. 1=Discard after task 2=Overlay before task 3=Concurrency';
COMMENT ON COLUMN "job_instance"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job_instance"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job_instance"."update_time" IS 'Create time';
COMMENT ON COLUMN "job_instance"."create_time_date" IS 'Time date`20230527`';
COMMENT ON COLUMN "job_instance"."create_time_hour" IS 'Time hour`2023052701`';
COMMENT ON COLUMN "job_instance"."create_time" IS 'Update time';

-- ----------------------------
-- Records of job_instance
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for job_instance_log
-- ----------------------------
CREATE TABLE "job_instance_log" (
                                    "id" NUMBER(20,0) NOT NULL ,
                                    "job_id" NUMBER(20,0),
                                    "job_instance_id" NUMBER(20,0),
                                    "message" NCLOB,
                                    "deleted" NUMBER(4,0) ,
                                    "delete_time" NUMBER(20,0) ,
                                    "create_time" NUMBER(20,0) ,
                                    "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "job_instance_log"."id" IS 'PK';
COMMENT ON COLUMN "job_instance_log"."job_id" IS 'Job id';
COMMENT ON COLUMN "job_instance_log"."job_instance_id" IS 'Job instance id';
COMMENT ON COLUMN "job_instance_log"."message" IS 'Message';
COMMENT ON COLUMN "job_instance_log"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job_instance_log"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job_instance_log"."create_time" IS 'Create time';
COMMENT ON COLUMN "job_instance_log"."update_time" IS 'Update time';

-- ----------------------------
-- Records of job_instance_log
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for job_instance_task
-- ----------------------------
CREATE TABLE "job_instance_task" (
                                     "id" NUMBER(11,0) NOT NULL ,
                                     "job_id" NUMBER(20,0),
                                     "job_instance_id" NUMBER(20,0),
                                     "circle_id" NUMBER(20,0),
                                     "task_id" NVARCHAR2(64),
                                     "parent_task_id" NVARCHAR2(64),
                                     "task_name" NVARCHAR2(128),
                                     "status" NUMBER(4,0),
                                     "result" NCLOB,
                                     "worker_address" NVARCHAR2(128),
                                     "deleted" NUMBER(4,0) ,
                                     "delete_time" NUMBER(20,0) ,
                                     "create_time" NUMBER(20,0),
                                     "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "job_instance_task"."id" IS 'PK';
COMMENT ON COLUMN "job_instance_task"."job_id" IS 'Job id';
COMMENT ON COLUMN "job_instance_task"."job_instance_id" IS 'Instance id';
COMMENT ON COLUMN "job_instance_task"."circle_id" IS 'Circle id';
COMMENT ON COLUMN "job_instance_task"."task_id" IS 'Task id';
COMMENT ON COLUMN "job_instance_task"."parent_task_id" IS 'Parent task id';
COMMENT ON COLUMN "job_instance_task"."task_name" IS 'Task name';
COMMENT ON COLUMN "job_instance_task"."status" IS 'Instance task status';
COMMENT ON COLUMN "job_instance_task"."result" IS 'Task result';
COMMENT ON COLUMN "job_instance_task"."worker_address" IS 'Worker address';
COMMENT ON COLUMN "job_instance_task"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job_instance_task"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job_instance_task"."create_time" IS 'Create time';
COMMENT ON COLUMN "job_instance_task"."update_time" IS 'Update time';

-- ----------------------------
-- Records of job_instance_task
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for job_slots
-- ----------------------------
CREATE TABLE "job_slots" (
                             "id" NUMBER(20,0) NOT NULL ,
                             "server_id" NUMBER(20,0),
                             "deleted" NUMBER(4,0) ,
                             "delete_time" NUMBER(20,0) ,
                             "create_time" NUMBER(20,0) ,
                             "update_time" NUMBER(20,0)
);
COMMENT ON COLUMN "job_slots"."id" IS 'PK';
COMMENT ON COLUMN "job_slots"."server_id" IS 'Server id';
COMMENT ON COLUMN "job_slots"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job_slots"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job_slots"."create_time" IS 'Create time';
COMMENT ON COLUMN "job_slots"."update_time" IS 'Update time';

-- ----------------------------
-- Records of job_slots
-- ----------------------------
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('1', '1', '2', '0', '1655781998', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('2', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('3', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('4', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('5', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('6', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('7', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('8', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('9', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('10', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('11', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('12', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('13', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('14', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('15', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('16', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('17', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('18', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('19', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('20', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('21', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('22', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('23', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('24', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('25', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('26', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('27', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('28', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('29', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('30', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('31', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('32', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('33', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('34', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('35', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('36', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('37', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('38', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('39', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('40', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('41', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('42', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('43', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('44', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('45', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('46', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('47', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('48', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('49', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('50', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('51', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('52', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('53', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('54', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('55', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('56', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('57', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('58', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('59', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('60', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('61', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('62', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('63', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('64', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('65', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('66', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('67', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('68', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('69', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('70', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('71', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('72', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('73', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('74', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('75', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('76', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('77', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('78', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('79', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('80', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('81', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('82', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('83', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('84', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('85', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('86', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('87', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('88', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('89', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('90', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('91', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('92', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('93', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('94', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('95', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('96', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('97', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('98', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('99', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('100', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('101', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('102', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('103', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('104', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('105', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('106', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('107', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('108', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('109', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('110', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('111', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('112', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('113', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('114', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('115', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('116', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('117', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('118', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('119', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('120', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('121', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('122', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('123', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('124', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('125', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('126', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('127', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('128', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('129', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('130', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('131', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('132', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('133', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('134', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('135', '1', '2', '0', '1655781999', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('136', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('137', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('138', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('139', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('140', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('141', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('142', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('143', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('144', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('145', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('146', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('147', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('148', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('149', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('150', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('151', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('152', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('153', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('154', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('155', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('156', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('157', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('158', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('159', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('160', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('161', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('162', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('163', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('164', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('165', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('166', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('167', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('168', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('169', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('170', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('171', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('172', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('173', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('174', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('175', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('176', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('177', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('178', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('179', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('180', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('181', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('182', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('183', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('184', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('185', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('186', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('187', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('188', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('189', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('190', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('191', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('192', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('193', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('194', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('195', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('196', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('197', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('198', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('199', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('200', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('201', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('202', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('203', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('204', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('205', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('206', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('207', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('208', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('209', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('210', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('211', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('212', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('213', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('214', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('215', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('216', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('217', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('218', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('219', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('220', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('221', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('222', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('223', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('224', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('225', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('226', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('227', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('228', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('229', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('230', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('231', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('232', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('233', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('234', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('235', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('236', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('237', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('238', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('239', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('240', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('241', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('242', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('243', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('244', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('245', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('246', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('247', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('248', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('249', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('250', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('251', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('252', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('253', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('254', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('255', '1', '2', '0', '1655782000', '1687953090');
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES ('256', '1', '2', '0', '1655782000', '1687953090');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for namespace
-- ----------------------------
CREATE TABLE "namespace" (
                             "id" NUMBER(20,0) NOT NULL ,
                             "name" NVARCHAR2(64),
                             "uuid" NVARCHAR2(64),
                             "deleted" NUMBER(4,0) ,
                             "delete_time" NUMBER(20,0) ,
                             "create_time" NUMBER(20,0) ,
                             "update_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "namespace"."id" IS 'PK';
COMMENT ON COLUMN "namespace"."name" IS 'Name';
COMMENT ON COLUMN "namespace"."uuid" IS 'Unique id';
COMMENT ON COLUMN "namespace"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "namespace"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "namespace"."create_time" IS 'Create time';
COMMENT ON COLUMN "namespace"."update_time" IS 'Update time';

-- ----------------------------
-- Records of namespace
-- ----------------------------
INSERT INTO "namespace" ("id", "name", "uuid", "deleted", "delete_time", "create_time", "update_time") VALUES ('1', 'default', 'a65d3fe5-258d-43e3-a5e6-dc12c4879ed6', '2', '0', '1657528102', '1683203496');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for processor_log
-- ----------------------------
CREATE TABLE "processor_log" (
                                 "id" NUMBER(20,0) NOT NULL ,
                                 "task_id" NVARCHAR2(128),
                                 "worker_address" NVARCHAR2(128),
                                 "content" NCLOB,
                                 "time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "processor_log"."id" IS 'PK';
COMMENT ON COLUMN "processor_log"."task_id" IS 'Task id';
COMMENT ON COLUMN "processor_log"."worker_address" IS 'Worker address';
COMMENT ON COLUMN "processor_log"."content" IS 'Content';
COMMENT ON COLUMN "processor_log"."time" IS 'TIme';

-- ----------------------------
-- Records of processor_log
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for server
-- ----------------------------
CREATE TABLE "server" (
                          "id" NUMBER(20,0)NOT NULL ,
                          "ip" NVARCHAR2(32),
                          "akka_address" NVARCHAR2(32),
                          "status" NUMBER(4,0),
                          "deleted" NUMBER(4,0) ,
                          "delete_time" NUMBER(20,0) ,
                          "create_time" NUMBER(20,0) ,
                          "update_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "server"."id" IS 'ID';
COMMENT ON COLUMN "server"."ip" IS 'Server ip';
COMMENT ON COLUMN "server"."akka_address" IS 'Akka address like `127.0.0.1:25520`';
COMMENT ON COLUMN "server"."status" IS 'Server status 1=ok 2=fail';
COMMENT ON COLUMN "server"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "server"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "server"."create_time" IS 'Create time';
COMMENT ON COLUMN "server"."update_time" IS 'Update time';

-- ----------------------------
-- Records of server
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for server_reports
-- ----------------------------
CREATE TABLE "server_reports" (
                                  "id" NUMBER(20,0) NOT NULL ,
                                  "server_id" NUMBER(20,0),
                                  "report_server_id" NUMBER(20,0),
                                  "status" NUMBER(4,0),
                                  "deleted" NUMBER(4,0) ,
                                  "delete_time" NUMBER(20,0) ,
                                  "create_time" NUMBER(20,0) ,
                                  "update_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "server_reports"."id" IS 'PK';
COMMENT ON COLUMN "server_reports"."server_id" IS 'Server id';
COMMENT ON COLUMN "server_reports"."report_server_id" IS 'Report server id';
COMMENT ON COLUMN "server_reports"."status" IS 'Report status';
COMMENT ON COLUMN "server_reports"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "server_reports"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "server_reports"."create_time" IS 'Create time';
COMMENT ON COLUMN "server_reports"."update_time" IS 'Update time';

-- ----------------------------
-- Records of server_reports
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for system
-- ----------------------------
CREATE TABLE "system" (
                          "id" NUMBER(11,0) NOT NULL ,
                          "version" NVARCHAR2(16),
                          "cluster_version" NUMBER(20,0),
                          "cluster_delay_version" NUMBER(20,0),
                          "worker_supervisor_slot" NUMBER(11,0),
                          "delay_zset_slot" NUMBER(11,0),
                          "delay_fail_zset_slot" NUMBER(11,0),
                          "delay_add_list_slot" NUMBER(11,0),
                          "delay_status_list_slot" NUMBER(11,0),
                          "delay_delete_list_slot" NUMBER(11,0),
                          "max_slot" NUMBER(11,0),
                          "job_keep_days" NUMBER(11,0),
                          "delay_keep_days" NUMBER(11,0),
                          "server_keep_days" NUMBER(11,0),
                          "worker_keep_days" NUMBER(11,0) ,
                          "deleted" NUMBER(4,0) ,
                          "delete_time" NUMBER(20,0) ,
                          "create_time" NUMBER(20,0) ,
                          "update_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "system"."id" IS 'PK';
COMMENT ON COLUMN "system"."version" IS 'Version';
COMMENT ON COLUMN "system"."cluster_version" IS 'Cluster version';
COMMENT ON COLUMN "system"."cluster_delay_version" IS 'Cluster delay version';
COMMENT ON COLUMN "system"."worker_supervisor_slot" IS 'Worker supervisor slot';
COMMENT ON COLUMN "system"."delay_zset_slot" IS 'Delay zset slot';
COMMENT ON COLUMN "system"."delay_fail_zset_slot" IS 'Delay fail zset slot';
COMMENT ON COLUMN "system"."delay_add_list_slot" IS 'Delay add list slot';
COMMENT ON COLUMN "system"."delay_status_list_slot" IS 'Delay status list slot';
COMMENT ON COLUMN "system"."delay_delete_list_slot" IS 'Delay delete list slot';
COMMENT ON COLUMN "system"."max_slot" IS 'System max slot';
COMMENT ON COLUMN "system"."job_keep_days" IS 'Job keep days';
COMMENT ON COLUMN "system"."delay_keep_days" IS 'Delay keep days';
COMMENT ON COLUMN "system"."server_keep_days" IS 'Server keep days';
COMMENT ON COLUMN "system"."worker_keep_days" IS 'Worker keep days';
COMMENT ON COLUMN "system"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "system"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "system"."create_time" IS 'Create time';
COMMENT ON COLUMN "system"."update_time" IS 'Update time';

-- ----------------------------
-- Records of system
-- ----------------------------
INSERT INTO "system" ("id", "version", "cluster_version", "cluster_delay_version", "worker_supervisor_slot", "delay_zset_slot", "delay_fail_zset_slot", "delay_add_list_slot", "delay_status_list_slot", "delay_delete_list_slot", "max_slot", "job_keep_days", "delay_keep_days", "server_keep_days", "worker_keep_days", "deleted", "delete_time", "create_time", "update_time") VALUES ('1', '1.0.0', '438', '8', '32', '2', '2', '2', '2', '1', '256', '1', '7', '90', '180', '2', '0', '1663590330', '1663590330');
COMMIT;
COMMIT;

-- ----------------------------
-- Table structure for worker
-- ----------------------------
CREATE TABLE "worker" (
                          "id" NUMBER(20,0) NOT NULL ,
                          "app_id" NUMBER(20,0),
                          "namespace_id" NUMBER(20,0),
                          "app_name" NVARCHAR2(128),
                          "worker_key" NVARCHAR2(64),
                          "slots_id" NUMBER(20,0),
                          "address" NVARCHAR2(32),
                          "protocol_type" NVARCHAR2(8),
                          "version" NVARCHAR2(32),
                          "last_heartbeat_time" NUMBER(20,0),
                          "status" NUMBER(4,0),
                          "metric" NCLOB,
                          "deleted" NUMBER(4,0) ,
                          "delete_time" NUMBER(20,0) ,
                          "create_time" NUMBER(20,0) ,
                          "update_time" NUMBER(20,0)
)
;
COMMENT ON COLUMN "worker"."id" IS 'PK';
COMMENT ON COLUMN "worker"."app_id" IS 'Application id';
COMMENT ON COLUMN "worker"."namespace_id" IS 'Namepspace';
COMMENT ON COLUMN "worker"."app_name" IS 'Application name';
COMMENT ON COLUMN "worker"."worker_key" IS 'Worker unique key';
COMMENT ON COLUMN "worker"."slots_id" IS 'Slots id';
COMMENT ON COLUMN "worker"."address" IS 'Address';
COMMENT ON COLUMN "worker"."protocol_type" IS 'Protocol type';
COMMENT ON COLUMN "worker"."version" IS 'Version';
COMMENT ON COLUMN "worker"."last_heartbeat_time" IS 'Last heartbeat time';
COMMENT ON COLUMN "worker"."status" IS 'Worker status';
COMMENT ON COLUMN "worker"."metric" IS 'Metric';
COMMENT ON COLUMN "worker"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "worker"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "worker"."create_time" IS 'Create time';
COMMENT ON COLUMN "worker"."update_time" IS 'Update time';

-- ----------------------------
-- Records of worker
-- ----------------------------
COMMIT;
COMMIT;

-- ----------------------------
-- Primary Key structure for table admin_permission
-- ----------------------------
ALTER TABLE "admin_permission" ADD CONSTRAINT "SYS_C0011575" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table admin_permission
-- ----------------------------
CREATE INDEX "idx_path"
    ON "admin_permission" ("path" ASC) ;
CREATE INDEX "idx_pid"
    ON "admin_permission" ("pid" ASC) ;

-- ----------------------------
-- Primary Key structure for table admin_role
-- ----------------------------
ALTER TABLE "admin_role" ADD CONSTRAINT "SYS_C0011574" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table admin_role
-- ----------------------------
CREATE INDEX "idx_name_admin_role"
    ON "admin_role" ("name" ASC)
;

-- ----------------------------
-- Primary Key structure for table admin_user
-- ----------------------------
ALTER TABLE "admin_user" ADD CONSTRAINT "SYS_C0011572" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table admin_user
-- ----------------------------
CREATE INDEX "idx_name"
    ON "admin_user" ("username" ASC)
;
CREATE UNIQUE INDEX "udx_token"
    ON "admin_user" ("token" ASC)
;

-- ----------------------------
-- Primary Key structure for table app
-- ----------------------------
ALTER TABLE "app" ADD CONSTRAINT "SYS_C0011573" PRIMARY KEY ("id");


-- ----------------------------
-- Primary Key structure for table delay
-- ----------------------------
ALTER TABLE "delay" ADD CONSTRAINT "SYS_C0011576" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table delay
-- ----------------------------
CREATE INDEX "idx_namespace_id_app_id"
    ON "delay" ("namespace_id" ASC, "app_id" ASC)
;
CREATE INDEX "idx_namespace_id_name_delay"
    ON "delay" ("namespace_id" ASC, "name" ASC)
;
CREATE INDEX "idx_namespace_id_topic"
    ON "delay" ("namespace_id" ASC, "topic" ASC)
;
CREATE UNIQUE INDEX "udx_topic"
    ON "delay" ("topic" ASC) ;

-- ----------------------------
-- Primary Key structure for table delay_instance
-- ----------------------------
ALTER TABLE "delay_instance" ADD CONSTRAINT "SYS_C0011577" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table delay_instance
-- ----------------------------
CREATE INDEX "idx_create_time_day"
    ON "delay_instance" ("create_time" ASC, "create_time_date" ASC)
;
CREATE INDEX "idx_create_time_hour"
    ON "delay_instance" ("create_time" ASC, "create_time_hour" ASC) ;
CREATE INDEX "idx_delay_id"
    ON "delay_instance" ("delay_id" ASC)
;
CREATE INDEX "idx_nadc_delay_instance"
    ON "delay_instance" ("namespace_id" ASC, "app_id" ASC, "delay_id" ASC, "create_time" ASC)
;
CREATE INDEX "idx_namespace_id_create_time"
    ON "delay_instance" ("namespace_id" ASC, "create_time" ASC) ;
CREATE UNIQUE INDEX "udx_task_id"
    ON "delay_instance" ("task_id" ASC)
;

-- ----------------------------
-- Primary Key structure for table job
-- ----------------------------
ALTER TABLE "job" ADD CONSTRAINT "SYS_C0011579" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job
-- ----------------------------
CREATE INDEX "idx_namespace_id_app_id_name"
    ON "job" ("namespace_id" ASC, "app_id" ASC, "name" ASC)
;
CREATE INDEX "idx_namespace_id_name_job"
    ON "job" ("namespace_id" ASC, "name" ASC)
;

-- ----------------------------
-- Primary Key structure for table job_instance
-- ----------------------------
ALTER TABLE "job_instance" ADD CONSTRAINT "SYS_C0011580" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance
-- ----------------------------
CREATE INDEX "idx_ch_instance"
    ON "job_instance" ("create_time" ASC, "create_time_hour" ASC)
;
CREATE INDEX "idx_ct_day_job_instance"
    ON "job_instance" ("create_time" ASC, "create_time_date" ASC)
;
CREATE INDEX "idx_execute_time_slots_id"
    ON "job_instance" ("execute_time" ASC, "slots_id" ASC)
;
CREATE INDEX "idx_job_id_status"
    ON "job_instance" ("job_id" ASC, "status" ASC)
;
CREATE INDEX "idx_last_report_time_slots_id"
    ON "job_instance" ("last_report_time" ASC, "slots_id" ASC)
;
CREATE INDEX "idx_niajcc_job_instance"
    ON "job_instance" ("namespace_id" ASC, "app_id" ASC, "job_id" ASC, "create_time" ASC)
;
CREATE INDEX "idx_nicc_job_instance"
    ON "job_instance" ("namespace_id" ASC, "create_time" ASC)
;

-- ----------------------------
-- Primary Key structure for table job_instance_log
-- ----------------------------
ALTER TABLE "job_instance_log" ADD CONSTRAINT "SYS_C0011581" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance_log
-- ----------------------------
CREATE INDEX "idx_jict_job_instance_log"
    ON "job_instance_log" ("job_instance_id" ASC, "create_time" ASC)
;

-- ----------------------------
-- Primary Key structure for table job_instance_task
-- ----------------------------
ALTER TABLE "job_instance_task" ADD CONSTRAINT "SYS_C0011582" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance_task
-- ----------------------------
CREATE INDEX "idx_jiic_job_instance_task"
    ON "job_instance_task" ("job_instance_id" ASC, "create_time" ASC)
;
CREATE INDEX "idx_parent_task_id"
    ON "job_instance_task" ("parent_task_id" ASC)
;
CREATE UNIQUE INDEX "udx_task_id_job_instance_task"
    ON "job_instance_task" ("task_id" ASC)
;

-- ----------------------------
-- Primary Key structure for table job_slots
-- ----------------------------
ALTER TABLE "job_slots" ADD CONSTRAINT "SYS_C0011583" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table job_slots
-- ----------------------------
CREATE INDEX "idx_server_id"
    ON "job_slots" ("server_id" ASC)
;

-- ----------------------------
-- Primary Key structure for table namespace
-- ----------------------------
ALTER TABLE "namespace" ADD CONSTRAINT "SYS_C0011585" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table namespace
-- ----------------------------
CREATE INDEX "idx_name_namespace"
    ON "namespace" ("name" ASC)
;

-- ----------------------------
-- Primary Key structure for table processor_log
-- ----------------------------
ALTER TABLE "processor_log" ADD CONSTRAINT "SYS_C0011586" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table processor_log
-- ----------------------------
CREATE INDEX "idx_task_id"
    ON "processor_log" ("task_id" ASC)
;
CREATE INDEX "idx_task_unique_id_time"
    ON "processor_log" ("time" ASC)
;

-- ----------------------------
-- Primary Key structure for table server
-- ----------------------------
ALTER TABLE "server" ADD CONSTRAINT "SYS_C0011587" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table server
-- ----------------------------
CREATE UNIQUE INDEX "udx_akka_address"
    ON "server" ("akka_address" ASC)
;

-- ----------------------------
-- Primary Key structure for table server_reports
-- ----------------------------
ALTER TABLE "server_reports" ADD CONSTRAINT "SYS_C0011588" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table server_reports
-- ----------------------------
CREATE INDEX "idx_create_time_server_id"
    ON "server_reports" ("create_time" ASC, "server_id" ASC)
;

-- ----------------------------
-- Primary Key structure for table system
-- ----------------------------
ALTER TABLE "system" ADD CONSTRAINT "SYS_C0011589" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table worker
-- ----------------------------
ALTER TABLE "worker" ADD CONSTRAINT "SYS_C0011591" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table worker
-- ----------------------------
CREATE INDEX "idx_nia_worker"
    ON "worker" ("namespace_id" ASC, "app_id" ASC)
;
CREATE UNIQUE INDEX "udx_address"
    ON "worker" ("address" ASC);


-- Create sequence
create sequence admin_permission_id
    minvalue 1
    maxvalue 999999999999
    start with 10000
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence admin_role_id
    minvalue 1
    maxvalue 999999999999
    start with 3
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence admin_user_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 3
    cache 20
cycle
;

-- Create sequence
create sequence app_id
    minvalue 1
    maxvalue 999999999999
    start with 3
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence delay_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence delay_instance_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence job_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence job_instance_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence job_instance_log_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence job_instance_task_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence job_slots_id
    minvalue 1
    maxvalue 999999999999
    start with 257
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence namespace_id
    minvalue 1
    maxvalue 999999999999
    start with 3
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence processor_log_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence server_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence server_reports_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence system_id
    minvalue 1
    maxvalue 999999999999
    start with 2
    increment by 1
    cache 20
cycle
;

-- Create sequence
create sequence worker_id
    minvalue 1
    maxvalue 999999999999
    start with 1
    increment by 1
    cache 20
cycle
;