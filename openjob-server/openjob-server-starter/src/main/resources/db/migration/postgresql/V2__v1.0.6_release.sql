-- ----------------------------
-- Table structure for alert_rule
-- ----------------------------
DROP TABLE IF EXISTS "alert_rule";
create sequence alert_rule_id start with 1 increment by 1 no minvalue no maxvalue cache 1;
CREATE TABLE "alert_rule" (
                                    "id" numeric(20,0) NOT NULL DEFAULT nextval('alert_rule_id'::regclass),
                                    "name" varchar(32) COLLATE "pg_catalog"."default",
                                    "namespace_app_ids" text COLLATE "pg_catalog"."default",
                                    "events"            text COLLATE "pg_catalog"."default",
                                    "metrics"           text COLLATE "pg_catalog"."default",
                                    "locale"            varchar(16) COLLATE "pg_catalog"."default",
                                    "method"            varchar(32)  COLLATE "pg_catalog"."default",
                                    "url"               varchar(256) COLLATE "pg_catalog"."default",
                                    "secret"            varchar(256) COLLATE "pg_catalog"."default",
                                    "status"            int2,
                                    "deleted"           int2,
                                    "delete_time"       numeric(20,0),
                                    "create_time"       numeric(20,0),
                                    "update_time"       numeric(20,0)
)
;

ALTER TABLE "alert_rule" OWNER TO "postgres";
COMMENT ON COLUMN "alert_rule"."id" IS 'PK';
COMMENT ON COLUMN "alert_rule"."name" IS 'Rule name';
COMMENT ON COLUMN "alert_rule"."namespace_app_ids" IS 'Namespace and applicatioin ids';
COMMENT ON COLUMN "alert_rule"."events" IS 'Events';
COMMENT ON COLUMN "alert_rule"."metrics" IS 'Metrics';
COMMENT ON COLUMN "alert_rule"."locale" IS 'Alert Locale(zh_CN/en_US)';
COMMENT ON COLUMN "alert_rule"."method" IS 'Alert method feishu/dingding/wecom/webhook';
COMMENT ON COLUMN "alert_rule"."url" IS 'Alert url';
COMMENT ON COLUMN "alert_rule"."secret" IS 'Alert secret';
COMMENT ON COLUMN "alert_rule"."status" IS 'Rule status 1=on 2=off';
COMMENT ON COLUMN "alert_rule"."deleted" IS 'Delete status. 1=yes 2=no';
COMMENT ON COLUMN "alert_rule"."delete_time" IS 'Delete time';
COMMENT ON COLUMN "alert_rule"."create_time" IS 'Create time';
COMMENT ON COLUMN "alert_rule"."update_time" IS 'Update time';

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time")
VALUES  (106, 0, 1, 'userProfile', '/personal', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.userProfile", "isLink": "", "isAffix": false, "isIframe": false, "component": "/personal/index", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320),
        (170, 0, 1, 'Alert', '/e', '{"icon": "ele-Aim", "roles": ["admin"], "title": "message.router.alert", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320),
        (171, 170, 1, 'AlertRule', '/admin/alert/list', '{"icon": "ele-DocumentCopy", "roles": ["admin"], "title": "message.router.alertRule", "isLink": "", "isAffix": false, "isIframe": false, "component": "/alert/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
ALTER TABLE "admin_user"
    ADD COLUMN  "login_time" numeric(20,0);
ALTER TABLE "admin_user"
    ADD COLUMN  "login_ip" VARCHAR(32) COLLATE "pg_catalog"."default";

ALTER TABLE "admin_user" OWNER TO "postgres";
COMMENT ON COLUMN "admin_user"."login_time" IS 'Login time';
COMMENT ON COLUMN "admin_user"."login_ip" IS 'Login IP';

-- ----------------------------
-- Table structure for job
-- ----------------------------
ALTER TABLE "job"
    ADD COLUMN  "execute_timeout" numeric(20,0);

ALTER TABLE "job" OWNER TO "postgres";
COMMENT ON COLUMN "job"."execute_timeout" IS 'Execute timeout';

-- ----------------------------
-- Table structure for job_instance
-- ----------------------------
ALTER TABLE "job_instance"
    ADD COLUMN  "fail_status" int2;
ALTER TABLE "job_instance"
    ADD COLUMN  "execute_timeout" numeric(20,0);

ALTER TABLE "job_instance" OWNER TO "postgres";
COMMENT ON COLUMN "job_instance"."fail_status" IS 'Fail status';
COMMENT ON COLUMN "job_instance"."execute_timeout" IS 'Execute timeout';

-- ----------------------------
-- Table structure for delay_instance
-- ----------------------------
ALTER TABLE "delay_instance"
    ADD COLUMN  "fail_status" int2;

ALTER TABLE "delay_instance" OWNER TO "postgres";
COMMENT ON COLUMN "delay_instance"."fail_status" IS 'Delay fail status';