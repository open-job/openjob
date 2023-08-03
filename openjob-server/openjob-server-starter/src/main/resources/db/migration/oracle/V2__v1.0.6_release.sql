-- ----------------------------
-- Table structure for alert_rule
-- ----------------------------
CREATE TABLE "alert_rule"
(
    "id"                NUMBER(20) NOT NULL,
    "name"              VARCHAR2(32),
    "namespace_app_ids" CLOB,
    "events"            CLOB,
    "metrics"           CLOB,
    "locale"            VARCHAR2(16),
    "method"            VARCHAR2(32),
    "url"               VARCHAR2(256),
    "secret"            VARCHAR2(256),
    "status"            NUMBER(1),
    "deleted"           NUMBER(2)  DEFAULT '2',
    "delete_time"       NUMBER(12) DEFAULT '0',
    "create_time"       NUMBER(12),
    "update_time"       NUMBER(12)
) ;
-- ADD COMMENTS TO THE TABLE
-- ------------------------------------------------------------
COMMENT ON TABLE "alert_rule" IS 'alert_rule';
-- ADD COMMENTS TO THE COLUMNS
-- ------------------------------------------------------------
  COMMENT ON COLUMN "alert_rule"."name"
  IS 'Rule name';
  COMMENT ON COLUMN "alert_rule"."namespace_app_ids"
  IS 'Namespace and applicatioin ids';
  COMMENT ON COLUMN "alert_rule"."events"
  IS 'Events';
  COMMENT ON COLUMN "alert_rule"."metrics"
  IS 'Metrics' ;
  COMMENT ON COLUMN "alert_rule"."locale"
  IS 'Alert Locale(zh_CN/en_US)';
  COMMENT ON COLUMN "alert_rule"."method"
  IS 'Alert method feishu/dingding/wecom/webhook';
  COMMENT ON COLUMN "alert_rule"."url"
  IS 'Alert url';
  COMMENT ON COLUMN "alert_rule"."secret"
  IS  'Alert secret';
  COMMENT ON COLUMN "alert_rule"."status"
  IS  'Rule status 1=on 2=off';
  COMMENT ON COLUMN "alert_rule"."deleted"
  IS 'Delete status. 1=yes 2=no';
  COMMENT ON COLUMN "alert_rule"."delete_time"
  IS 'Delete time';
  COMMENT ON COLUMN "alert_rule"."create_time"
  IS 'Create time';
  COMMENT ON COLUMN "alert_rule"."update_time"
  IS 'Update time';

---PRIMARY KEY
-- ------------------------------------------------------------
ALTER TABLE "alert_rule"ADD PRIMARY KEY ( "id");

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time")
VALUES  (106, 0, 1, 'userProfile', '/personal', '{"icon": "ele-ColdDrink", "roles": ["admin"], "title": "message.router.userProfile", "isLink": "", "isAffix": false, "isIframe": false, "component": "/personal/index", "isKeepAlive": true}', 1, 0, 2, 0, 1669972320, 1669972320);
COMMIT;
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time")
VALUES  (170, 0, 1, 'Alert', '/e', '{"icon": "ele-Aim", "roles": ["admin"], "title": "message.router.alert", "isLink": "", "isAffix": false, "isIframe": false, "component": "", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
COMMIT;
INSERT INTO "admin_permission" ("id", "pid", "type", "name", "path", "meta", "hidden", "sort", "deleted", "delete_time", "update_time", "create_time")
VALUES  (171, 170, 1, 'AlertRule', '/admin/alert/list', '{"icon": "ele-DocumentCopy", "roles": ["admin"], "title": "message.router.alertRule", "isLink": "", "isAffix": false, "isIframe": false, "component": "/alert/index", "isKeepAlive": true}', 2, 0, 2, 0, 1669972320, 1669972320);
COMMIT;

- ----------------------------
-- Table structure for admin_user
-- ----------------------------
ALTER TABLE "admin_user" ADD "login_time" NUMBER(32)  ;
COMMENT ON COLUMN "admin_user"."login_time" IS 'Login time';

ALTER TABLE "admin_user" ADD "login_ip" VARCHAR2(32) ;
COMMENT ON COLUMN "admin_user"."login_ip" IS 'Login IP';

- ----------------------------
-- Table structure for job
-- ----------------------------
ALTER TABLE "job" ADD "execute_timeout" NUMBER(11)  ;
COMMENT ON COLUMN "job"."execute_timeout" IS 'Execute timeout';

- ----------------------------
-- Table structure for job_instance
-- ----------------------------
ALTER TABLE "job_instance" ADD "fail_status" NUMBER(2)   ;
COMMENT ON COLUMN "job_instance"."fail_status" IS 'Fail status';

ALTER TABLE "job_instance" ADD "execute_timeout" NUMBER(11)  ;
COMMENT ON COLUMN "job_instance"."execute_timeout" IS 'Execute timeout';


- ----------------------------
-- Table structure for delay_instance
-- ----------------------------
ALTER TABLE "delay_instance"ADD "fail_status" NUMBER(2)  ;
COMMENT ON COLUMN "delay_instance"."fail_status" IS 'Delay fail status';

--"SEQUENCE"
---------------------------------------------------------------
CREATE SEQUENCE alert_rule_id
    MINVALUE 1
    MAXVALUE 999999999999
    START WITH 1
    INCREMENT BY 4
    CACHE 20
CYCLE;
