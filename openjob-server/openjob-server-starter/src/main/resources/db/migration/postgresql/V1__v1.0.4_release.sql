-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
DROP TABLE IF EXISTS "admin_permission";
create sequence admin_permission_id start with 10000 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "admin_permission" (
                                    "id" numeric(20,0) NOT NULL DEFAULT nextval('admin_permission_id'::regclass),
                                    "pid" numeric(20,0) NOT NULL,
                                    "type" int2 NOT NULL,
                                    "name" varchar(48) COLLATE "pg_catalog"."default" NOT NULL,
                                    "path" varchar(86) COLLATE "pg_catalog"."default" NOT NULL,
                                    "meta" text COLLATE "pg_catalog"."default",
                                    "hidden" int2 NOT NULL,
                                    "sort" int4 NOT NULL,
                                    "deleted" int2 NOT NULL,
                                    "delete_time" numeric(20,0) NOT NULL,
                                    "update_time" numeric(20,0) NOT NULL,
                                    "create_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "admin_permission" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (1, 0, 1, 'dashboard', '/dashboard', '{"icon": "iconfont icon-shouye", "roles": ["admin"], "title": "message.router.dashboard", "isLink": "", "isAffix": false, "isIframe": false, "component": "/home/index.vue", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (20, 0, 1, 'namespace', '/admin/namespace/list', '{"icon": "ele-Edit", "roles": ["admin"], "title": "message.router.namespace", "isLink": "", "isAffix": false, "isIframe": false, "component": "/namespace/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (40, 0, 1, 'application', '/admin/app/list', '{"icon": "ele-Operation", "roles": ["admin"], "title": "message.router.application", "isLink": "", "isAffix": false, "isIframe": false, "component": "/app/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (60, 0, 1, 'cronJob', '/a', '{"icon": "ele-AlarmClock", "roles": ["admin"], "title": "message.router.cronJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (61, 60, 1, 'cronJobJob', '/admin/job/list', '{"icon": "ele-Suitcase", "roles": ["admin"], "title": "message.router.cronJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (62, 60, 1, 'cronJobInstance', '/admin/job-instance/list', '{"icon": "ele-Document", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (63, 60, 1, 'cronJobPage', '/admin/job/page', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.cronJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/job/job/page", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (80, 0, 1, 'delayJob', '/b', '{"icon": "ele-Clock", "roles": ["admin"], "title": "message.router.delayJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (81, 80, 1, 'delayJobDelay', '/admin/delay/list', '{"icon": "ele-SetUp", "roles": ["admin"], "title": "message.router.delayJobJob", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/job/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (82, 80, 1, 'delayJobInstance', '/admin/delay-instance/list', '{"icon": "ele-DocumentChecked", "roles": ["admin"], "title": "message.router.delayJobInstance", "isLink": "", "isAffix": false, "isIframe": false, "component": "/delay/instance/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (200, 0, 1, 'cluster', '/c', '{"icon": "ele-SuitcaseLine", "roles": ["admin"], "title": "message.router.clusterManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (201, 200, 1, 'ClusterNode', '/admin/cluster-node/list', '{"icon": "ele-Notification", "roles": ["admin"], "title": "message.router.clusterNode", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/node/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (202, 200, 1, 'ClusterWorker', '/admin/cluster-worker/list', '{"icon": "ele-Monitor", "roles": ["admin"], "title": "message.router.clusterWorker", "isLink": "", "isAffix": false, "isIframe": false, "component": "/cluster/worker/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (300, 0, 1, 'system', '/d', '{"icon": "ele-Setting", "roles": ["admin"], "title": "message.router.systemManager", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (301, 300, 1, 'SystemConfiguration', '/admin/system-config/list', '{"icon": "ele-CreditCard", "roles": ["admin"], "title": "message.router.systemConfiguration", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/config/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (302, 300, 1, 'SystemSlots', '/admin/system-slots/list', '{"icon": "ele-Connection", "roles": ["admin"], "title": "message.router.systemSlots", "isLink": "", "isAffix": false, "isIframe": false, "component": "/system/slots/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (1000, 0, 2, 'namespace.add', '/admin/namespace/add', '{}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (1001, 0, 2, 'namespace.delete', '/admin/namespace/delete', '{}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (1002, 0, 2, 'namespace.update', '/admin/namespace/update', '{}', 2, 0, 2, 0, 1669972320, 1669972320);
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time") VALUES (1003, 0, 2, 'namespace.update.status', '/admin/namespace/update-status', '{}', 2, 0, 2, 0, 1669972320, 1669972320);
COMMIT;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS "admin_role";
create sequence admin_role_id start with 3 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "admin_role" (
                              "id" numeric(20,0) NOT NULL DEFAULT nextval('admin_role_id'::regclass),
                              "name" varchar(48) COLLATE "pg_catalog"."default" NOT NULL,
                              "desc" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                              "admin" int2 NOT NULL,
                              "menu_ids" text COLLATE "pg_catalog"."default",
                              "perm_ids" text COLLATE "pg_catalog"."default",
                              "namespace_ids" text COLLATE "pg_catalog"."default",
                              "app_ids" text COLLATE "pg_catalog"."default",
                              "deleted" int2 NOT NULL,
                              "delete_time" numeric(20,0) NOT NULL,
                              "update_time" numeric(20,0) NOT NULL,
                              "create_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "admin_role" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "admin_role" ("id", "name", "desc", "admin", "menu_ids", "perm_ids", "namespace_ids", "app_ids", "deleted", "delete_time", "update_time", "create_time") VALUES (1, 'Admin', 'Administrator role', 1, '[]', '[]', '[]', '[]', 2, 0, 1670255999, 1670255999);
COMMIT;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS "admin_user";
create sequence admin_user_id start with 3 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "admin_user" (
                              "id" numeric(20,0) NOT NULL DEFAULT nextval('admin_user_id'::regclass),
                              "username" varchar(48) COLLATE "pg_catalog"."default" NOT NULL,
                              "nickname" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                              "passwd" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                              "session_key" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                              "session_expire_at" int8 NOT NULL,
                              "token" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                              "role_ids" text COLLATE "pg_catalog"."default",
                              "deleted" int2 NOT NULL,
                              "delete_time" numeric(20,0) NOT NULL,
                              "update_time" numeric(20,0) NOT NULL,
                              "create_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "admin_user" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "admin_user" ("id", "username", "nickname", "passwd", "session_key", "session_expire_at", "token", "role_ids", "deleted", "delete_time", "update_time", "create_time") VALUES (1, 'openjob', 'Administrator', '144f039395e24903a08926b978340a1554660c4da724d0700e3dceac9573675f', '79f74383e2c92ae01e172ced4c9267d5', 0, '79f74383e2c92ae01e172ced4c9267d5', '[1]', 2, 0, 1670255999, 1670255999);
COMMIT;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS "app";
create sequence app_id start with 3 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "app" (
                       "id" numeric(20,0) NOT NULL DEFAULT nextval('app_id'::regclass),
                       "namespace_id" int8 NOT NULL,
                       "name" varchar(64) COLLATE "pg_catalog"."default",
                       "desc" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
                       "deleted" int2 NOT NULL,
                       "delete_time" numeric(20,0) NOT NULL,
                       "create_time" numeric(20,0) NOT NULL,
                       "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "app" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "app" ("id", "namespace_id", "name", "desc", "deleted", "delete_time", "create_time", "update_time") VALUES (1, 1, 'openjob', 'openjob', 2, 0, 1658473199, 1678796017);
COMMIT;

-- ----------------------------
-- Table structure for delay
-- ----------------------------
DROP TABLE IF EXISTS "delay";
create sequence delay_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "delay" (
                         "id" numeric(20,0) NOT NULL DEFAULT nextval('delay_id'::regclass),
                         "pid" int8 NOT NULL,
                         "cid" int8 NOT NULL,
                         "namespace_id" int8 NOT NULL,
                         "app_id" int8 NOT NULL,
                         "name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                         "description" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
                         "processor_info" varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
                         "fail_retry_times" int8 NOT NULL,
                         "fail_retry_interval" int8 NOT NULL,
                         "execute_timeout" int8 NOT NULL,
                         "concurrency" int8 NOT NULL,
                         "blocking_size" int8 NOT NULL,
                         "topic" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                         "fail_topic_enable" int2 NOT NULL,
                         "fail_topic_concurrency" int4 NOT NULL,
                         "deleted" int2 NOT NULL,
                         "delete_time" numeric(20,0) NOT NULL,
                         "create_time" int8 NOT NULL,
                         "update_time" int8 NOT NULL
)
;
ALTER TABLE "delay" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for delay_instance
-- ----------------------------
DROP TABLE IF EXISTS "delay_instance";
create sequence delay_instance_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "delay_instance" (
                                  "id" numeric(20,0) NOT NULL DEFAULT nextval('delay_instance_id'::regclass),
                                  "app_id" numeric(20,0) NOT NULL,
                                  "namespace_id" numeric(20,0) NOT NULL,
                                  "task_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "topic" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                  "delay_id" numeric(20,0) NOT NULL,
                                  "delay_params" text COLLATE "pg_catalog"."default" NOT NULL,
                                  "delay_extra" text COLLATE "pg_catalog"."default" NOT NULL,
                                  "status" int2 NOT NULL,
                                  "execute_time" int8 NOT NULL,
                                  "complete_time" int8 NOT NULL,
                                  "worker_address" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                  "deleted" int2 NOT NULL,
                                  "delete_time" numeric(20,0) NOT NULL,
                                  "create_time" int8 NOT NULL,
                                  "update_time" int8 NOT NULL,
                                  "create_time_date" int4 NOT NULL,
                                  "create_time_hour" int4 NOT NULL
)
;
ALTER TABLE "delay_instance" OWNER TO "postgres";
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
BEGIN;
COMMIT;
-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS "job";
create sequence job_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "job" (
                       "id" numeric(20,0) NOT NULL DEFAULT nextval('job_id'::regclass),
                       "namespace_id" numeric(20,0) NOT NULL,
                       "app_id" numeric(20,0) NOT NULL,
                       "workflow_id" numeric(20,0) NOT NULL,
                       "name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                       "description" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                       "processor_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                       "processor_info" text COLLATE "pg_catalog"."default" NOT NULL,
                       "execute_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                       "params" varchar(3096) COLLATE "pg_catalog"."default" NOT NULL,
                       "params_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                       "extend_params_type" varchar(16) COLLATE "pg_catalog"."default",
                       "extend_params" varchar(3096) COLLATE "pg_catalog"."default" NOT NULL,
                       "fail_retry_times" int8 NOT NULL,
                       "fail_retry_interval" int8 NOT NULL,
                       "concurrency" int8 NOT NULL,
                       "time_expression_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                       "time_expression" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                       "execute_strategy" int2 NOT NULL,
                       "status" int2 NOT NULL,
                       "next_execute_time" numeric(20,0) NOT NULL,
                       "slots_id" int8 NOT NULL,
                       "deleted" int2 NOT NULL,
                       "delete_time" numeric(20,0) NOT NULL,
                       "create_time" numeric(20,0) NOT NULL,
                       "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "job" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for job_instance
-- ----------------------------
DROP TABLE IF EXISTS "job_instance";
create sequence job_instance_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "job_instance" (
                                "id" numeric(20,0) NOT NULL DEFAULT nextval('job_instance_id'::regclass),
                                "job_id" numeric(20,0) NOT NULL,
                                "params" varchar(3096) COLLATE "pg_catalog"."default" NOT NULL,
                                "params_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                                "extend_params_type" varchar(16) COLLATE "pg_catalog"."default",
                                "extend_params" varchar(3096) COLLATE "pg_catalog"."default" NOT NULL,
                                "status" int2 NOT NULL,
                                "slots_id" numeric(20,0) NOT NULL,
                                "workflow_id" numeric(20,0) NOT NULL,
                                "namespace_id" numeric(20,0) NOT NULL,
                                "app_id" numeric(20,0) NOT NULL,
                                "execute_time" numeric(20,0) NOT NULL,
                                "complete_time" numeric(20,0) NOT NULL,
                                "last_report_time" numeric(20,0) NOT NULL,
                                "processor_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                                "processor_info" text COLLATE "pg_catalog"."default" NOT NULL,
                                "execute_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                                "fail_retry_times" int8 NOT NULL,
                                "fail_retry_interval" int8 NOT NULL,
                                "time_expression_type" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                                "time_expression" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                "concurrency" int8 NOT NULL,
                                "worker_address" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                "execute_strategy" int2 NOT NULL,
                                "deleted" int2 NOT NULL,
                                "delete_time" numeric(20,0) NOT NULL,
                                "update_time" numeric(20,0) NOT NULL,
                                "create_time_date" int4 NOT NULL,
                                "create_time_hour" int4 NOT NULL,
                                "create_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "job_instance" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for job_instance_log
-- ----------------------------
DROP TABLE IF EXISTS "job_instance_log";
create sequence job_instance_log_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "job_instance_log" (
                                    "id" numeric(20,0) NOT NULL DEFAULT nextval('job_instance_log_id'::regclass),
                                    "job_id" numeric(20,0) NOT NULL,
                                    "job_instance_id" numeric(20,0) NOT NULL,
                                    "message" text COLLATE "pg_catalog"."default",
                                    "deleted" int2 NOT NULL,
                                    "delete_time" numeric(20,0) NOT NULL,
                                    "create_time" numeric(20,0) NOT NULL,
                                    "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "job_instance_log" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for job_instance_task
-- ----------------------------
DROP TABLE IF EXISTS "job_instance_task";
create sequence job_instance_task_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "job_instance_task" (
                                     "id" numeric(20,0) NOT NULL DEFAULT nextval('job_instance_task_id'::regclass),
                                     "job_id" numeric(20,0) NOT NULL,
                                     "job_instance_id" numeric(20,0) NOT NULL,
                                     "circle_id" numeric(20,0) NOT NULL,
                                     "task_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                     "parent_task_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                     "task_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                     "status" int2 NOT NULL,
                                     "result" text COLLATE "pg_catalog"."default",
                                     "worker_address" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                     "deleted" int2 NOT NULL,
                                     "delete_time" numeric(20,0) NOT NULL,
                                     "create_time" numeric(20,0),
                                     "update_time" numeric(20,0)
)
;
ALTER TABLE "job_instance_task" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for job_slots
-- ----------------------------
DROP TABLE IF EXISTS "job_slots";
create sequence job_slots_id start with 257 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "job_slots" (
                             "id" numeric(20,0) NOT NULL DEFAULT nextval('job_slots_id'::regclass),
                             "server_id" numeric(20,0) NOT NULL,
                             "deleted" int2 NOT NULL,
                             "delete_time" numeric(20,0) NOT NULL,
                             "create_time" numeric(20,0) NOT NULL,
                             "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "job_slots" OWNER TO "postgres";
COMMENT ON COLUMN "job_slots"."id" IS 'PK';
COMMENT ON COLUMN "job_slots"."server_id" IS 'Server id';
COMMENT ON COLUMN "job_slots"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "job_slots"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "job_slots"."create_time" IS 'Create time';
COMMENT ON COLUMN "job_slots"."update_time" IS 'Update time';

-- ----------------------------
-- Records of job_slots
-- ----------------------------
BEGIN;
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (1, 1, 2, 0, 1655781998, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (2, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (3, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (4, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (5, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (6, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (7, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (8, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (9, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (10, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (11, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (12, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (13, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (14, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (15, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (16, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (17, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (18, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (19, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (20, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (21, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (22, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (23, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (24, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (25, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (26, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (27, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (28, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (29, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (30, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (31, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (32, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (33, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (34, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (35, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (36, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (37, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (38, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (39, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (40, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (41, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (42, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (43, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (44, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (45, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (46, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (47, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (48, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (49, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (50, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (51, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (52, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (53, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (54, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (55, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (56, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (57, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (58, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (59, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (60, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (61, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (62, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (63, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (64, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (65, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (66, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (67, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (68, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (69, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (70, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (71, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (72, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (73, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (74, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (75, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (76, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (77, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (78, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (79, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (80, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (81, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (82, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (83, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (84, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (85, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (86, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (87, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (88, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (89, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (90, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (91, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (92, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (93, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (94, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (95, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (96, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (97, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (98, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (99, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (100, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (101, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (102, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (103, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (104, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (105, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (106, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (107, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (108, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (109, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (110, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (111, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (112, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (113, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (114, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (115, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (116, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (117, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (118, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (119, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (120, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (121, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (122, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (123, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (124, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (125, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (126, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (127, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (128, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (129, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (130, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (131, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (132, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (133, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (134, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (135, 1, 2, 0, 1655781999, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (136, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (137, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (138, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (139, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (140, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (141, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (142, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (143, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (144, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (145, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (146, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (147, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (148, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (149, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (150, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (151, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (152, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (153, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (154, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (155, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (156, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (157, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (158, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (159, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (160, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (161, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (162, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (163, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (164, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (165, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (166, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (167, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (168, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (169, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (170, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (171, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (172, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (173, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (174, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (175, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (176, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (177, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (178, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (179, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (180, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (181, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (182, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (183, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (184, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (185, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (186, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (187, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (188, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (189, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (190, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (191, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (192, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (193, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (194, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (195, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (196, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (197, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (198, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (199, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (200, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (201, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (202, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (203, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (204, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (205, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (206, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (207, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (208, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (209, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (210, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (211, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (212, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (213, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (214, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (215, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (216, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (217, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (218, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (219, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (220, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (221, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (222, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (223, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (224, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (225, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (226, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (227, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (228, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (229, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (230, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (231, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (232, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (233, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (234, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (235, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (236, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (237, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (238, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (239, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (240, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (241, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (242, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (243, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (244, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (245, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (246, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (247, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (248, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (249, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (250, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (251, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (252, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (253, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (254, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (255, 1, 2, 0, 1655782000, 1687953090);
INSERT INTO "job_slots" ("id", "server_id", "deleted", "delete_time", "create_time", "update_time") VALUES (256, 1, 2, 0, 1655782000, 1687953090);
COMMIT;


-- ----------------------------
-- Table structure for namespace
-- ----------------------------
DROP TABLE IF EXISTS "namespace";
create sequence namespace_id start with 3 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "namespace" (
                             "id" numeric(20,0) NOT NULL DEFAULT nextval('namespace_id'::regclass),
                             "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                             "uuid" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                             "deleted" int2 NOT NULL,
                             "delete_time" numeric(20,0) NOT NULL,
                             "create_time" numeric(20,0) NOT NULL,
                             "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "namespace" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "namespace" ("id", "name", "uuid", "deleted", "delete_time", "create_time", "update_time") VALUES (1, 'default', 'a65d3fe5-258d-43e3-a5e6-dc12c4879ed6', 2, 0, 1657528102, 1683203496);
COMMIT;

-- ----------------------------
-- Table structure for processor_log
-- ----------------------------
DROP TABLE IF EXISTS "processor_log";
create sequence processor_log_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "processor_log" (
                                 "id" numeric(20,0) NOT NULL DEFAULT nextval('processor_log_id'::regclass),
                                 "task_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                 "worker_address" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                 "content" text COLLATE "pg_catalog"."default" NOT NULL,
                                 "time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "processor_log" OWNER TO "postgres";
COMMENT ON COLUMN "processor_log"."id" IS 'PK';
COMMENT ON COLUMN "processor_log"."task_id" IS 'Task id';
COMMENT ON COLUMN "processor_log"."worker_address" IS 'Worker address';
COMMENT ON COLUMN "processor_log"."content" IS 'Content';
COMMENT ON COLUMN "processor_log"."time" IS 'TIme';

-- ----------------------------
-- Records of processor_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for server
-- ----------------------------
DROP TABLE IF EXISTS "server";
create sequence server_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "server" (
                          "id" numeric(20,0) NOT NULL DEFAULT nextval('server_id'::regclass),
                          "ip" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                          "akka_address" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                          "status" int2 NOT NULL,
                          "deleted" int2 NOT NULL,
                          "delete_time" numeric(20,0) NOT NULL,
                          "create_time" numeric(20,0) NOT NULL,
                          "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "server" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for server_reports
-- ----------------------------
DROP TABLE IF EXISTS "server_reports";
create sequence server_reports_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "server_reports" (
                                  "id" numeric(20,0) NOT NULL DEFAULT nextval('server_reports_id'::regclass),
                                  "server_id" numeric(20,0) NOT NULL,
                                  "report_server_id" numeric(20,0) NOT NULL,
                                  "status" int2 NOT NULL,
                                  "deleted" int2 NOT NULL,
                                  "delete_time" numeric(20,0) NOT NULL,
                                  "create_time" numeric(20,0) NOT NULL,
                                  "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "server_reports" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system
-- ----------------------------
DROP TABLE IF EXISTS "system";
create sequence system_id start with 2 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "system" (
                          "id" numeric(20,0) NOT NULL DEFAULT nextval('system_id'::regclass),
                          "version" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
                          "cluster_version" numeric(20,0) NOT NULL,
                          "cluster_delay_version" numeric(20,0) NOT NULL,
                          "worker_supervisor_slot" int8 NOT NULL,
                          "delay_zset_slot" int8 NOT NULL,
                          "delay_fail_zset_slot" int4 NOT NULL,
                          "delay_add_list_slot" int8 NOT NULL,
                          "delay_status_list_slot" int8 NOT NULL,
                          "delay_delete_list_slot" int8 NOT NULL,
                          "max_slot" int8 NOT NULL,
                          "job_keep_days" int4 NOT NULL,
                          "delay_keep_days" int4 NOT NULL,
                          "server_keep_days" int4 NOT NULL,
                          "worker_keep_days" int4 NOT NULL,
                          "deleted" int2 NOT NULL,
                          "delete_time" numeric(20,0) NOT NULL,
                          "create_time" numeric(20,0) NOT NULL,
                          "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "system" OWNER TO "postgres";
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
BEGIN;
INSERT INTO "system" ("id", "version", "cluster_version", "cluster_delay_version", "worker_supervisor_slot", "delay_zset_slot", "delay_fail_zset_slot", "delay_add_list_slot", "delay_status_list_slot", "delay_delete_list_slot", "max_slot", "job_keep_days", "delay_keep_days", "server_keep_days", "worker_keep_days", "deleted", "delete_time", "create_time", "update_time") VALUES (1, '1.0.0', 438, 8, 32, 2, 2, 2, 2, 1, 256, 1, 7, 90, 180, 2, 0, 1663590330, 1663590330);
COMMIT;

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS "worker";
create sequence worker_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "worker" (
                          "id" numeric(20,0) NOT NULL DEFAULT nextval('worker_id'::regclass),
                          "app_id" numeric(20,0) NOT NULL,
                          "namespace_id" numeric(20,0) NOT NULL,
                          "app_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                          "worker_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                          "slots_id" numeric(20,0) NOT NULL,
                          "address" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                          "protocol_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
                          "version" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                          "last_heartbeat_time" numeric(20,0) NOT NULL,
                          "status" int2 NOT NULL,
                          "metric" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
                          "deleted" int2 NOT NULL,
                          "delete_time" numeric(20,0) NOT NULL,
                          "create_time" numeric(20,0) NOT NULL,
                          "update_time" numeric(20,0) NOT NULL
)
;
ALTER TABLE "worker" OWNER TO "postgres";
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
BEGIN;
COMMIT;

-- ----------------------------
-- Indexes structure for table admin_permission
-- ----------------------------
CREATE INDEX "idx_path" ON "admin_permission" USING btree (
    "path" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_pid" ON "admin_permission" USING btree (
    "pid" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table admin_permission
-- ----------------------------
ALTER TABLE "admin_permission" ADD CONSTRAINT "admin_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table admin_role
-- ----------------------------
CREATE INDEX "idx_name" ON "admin_role" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table admin_role
-- ----------------------------
ALTER TABLE "admin_role" ADD CONSTRAINT "admin_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table admin_user
-- ----------------------------
CREATE INDEX "idx_name_admin_user" ON "admin_user" USING btree (
    "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_token" ON "admin_user" USING btree (
    "token" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table admin_user
-- ----------------------------
ALTER TABLE "admin_user" ADD CONSTRAINT "admin_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table app
-- ----------------------------
CREATE INDEX "idx_namespace_id_name" ON "app" USING btree (
    "namespace_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_name" ON "app" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table app
-- ----------------------------
ALTER TABLE "app" ADD CONSTRAINT "app_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table delay
-- ----------------------------
CREATE INDEX "idx_namespace_id_app_id" ON "delay" USING btree (
    "namespace_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_name_delay" ON "delay" USING btree (
    "namespace_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_topic" ON "delay" USING btree (
    "namespace_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "topic" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_topic" ON "delay" USING btree (
    "topic" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table delay
-- ----------------------------
ALTER TABLE "delay" ADD CONSTRAINT "delay_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table delay_instance
-- ----------------------------
CREATE INDEX "idx_create_time_day" ON "delay_instance" USING btree (
    "create_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "create_time_date" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_create_time_hour" ON "delay_instance" USING btree (
    "create_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
    "create_time_hour" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_delay_id" ON "delay_instance" USING btree (
    "delay_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_app_id_delay_id_create_time" ON "delay_instance" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "app_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "delay_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_create_time" ON "delay_instance" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_task_id" ON "delay_instance" USING btree (
    "task_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table delay_instance
-- ----------------------------
ALTER TABLE "delay_instance" ADD CONSTRAINT "delay_instance_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job
-- ----------------------------
CREATE INDEX "idx_namespace_id_app_id_name" ON "job" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "app_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_name_job" ON "job" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table job
-- ----------------------------
ALTER TABLE "job" ADD CONSTRAINT "job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance
-- ----------------------------
CREATE INDEX "idx_create_time_day_job_instance" ON "job_instance" USING btree (
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time_date" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_create_time_hour_job_instance" ON "job_instance" USING btree (
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time_hour" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_execute_time_slots_id" ON "job_instance" USING btree (
    "execute_time" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "slots_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_job_id_status" ON "job_instance" USING btree (
    "job_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "status" "pg_catalog"."int2_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_last_report_time_slots_id" ON "job_instance" USING btree (
    "last_report_time" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "slots_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_app_id_job_id_create_time" ON "job_instance" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "app_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "job_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_namespace_id_create_time_job_instance" ON "job_instance" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table job_instance
-- ----------------------------
ALTER TABLE "job_instance" ADD CONSTRAINT "job_instance_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance_log
-- ----------------------------
CREATE INDEX "idx_job_instance_id_create_time" ON "job_instance_log" USING btree (
    "job_instance_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table job_instance_log
-- ----------------------------
ALTER TABLE "job_instance_log" ADD CONSTRAINT "job_instance_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_instance_task
-- ----------------------------
CREATE INDEX "idx_job_instance_id_create_time_job_instance_task" ON "job_instance_task" USING btree (
    "job_instance_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_parent_task_id" ON "job_instance_task" USING btree (
    "parent_task_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_task_id_job_instance_task" ON "job_instance_task" USING btree (
    "task_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table job_instance_task
-- ----------------------------
ALTER TABLE "job_instance_task" ADD CONSTRAINT "job_instance_task_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table job_slots
-- ----------------------------
CREATE INDEX "idx_server_id" ON "job_slots" USING btree (
    "server_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table job_slots
-- ----------------------------
ALTER TABLE "job_slots" ADD CONSTRAINT "job_slots_pkey" PRIMARY KEY ("id");


-- ----------------------------
-- Indexes structure for table namespace
-- ----------------------------
CREATE INDEX "idx_name_namespace" ON "namespace" USING btree (
    "name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table namespace
-- ----------------------------
ALTER TABLE "namespace" ADD CONSTRAINT "namespace_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table processor_log
-- ----------------------------
CREATE INDEX "idx_task_id" ON "processor_log" USING btree (
    "task_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_task_unique_id_time" ON "processor_log" USING btree (
    "time" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table processor_log
-- ----------------------------
ALTER TABLE "processor_log" ADD CONSTRAINT "processor_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table server
-- ----------------------------
CREATE UNIQUE INDEX "udx_akka_address" ON "server" USING btree (
    "akka_address" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table server
-- ----------------------------
ALTER TABLE "server" ADD CONSTRAINT "server_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table server_reports
-- ----------------------------
CREATE INDEX "idx_create_time_server_id" ON "server_reports" USING btree (
    "create_time" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "server_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table server_reports
-- ----------------------------
ALTER TABLE "server_reports" ADD CONSTRAINT "server_reports_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table system
-- ----------------------------
ALTER TABLE "system" ADD CONSTRAINT "system_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table worker
-- ----------------------------
CREATE INDEX "idx_namespace_id_app_id_worker" ON "worker" USING btree (
    "namespace_id" "pg_catalog"."numeric_ops" ASC NULLS LAST,
    "app_id" "pg_catalog"."numeric_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "udx_address" ON "worker" USING btree (
    "address" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table worker
-- ----------------------------
ALTER TABLE "worker" ADD CONSTRAINT "worker_pkey" PRIMARY KEY ("id");