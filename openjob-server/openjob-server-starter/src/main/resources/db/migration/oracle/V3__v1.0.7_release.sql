-- ----------------------------
-- Table structure for job_instance
-- ----------------------------
ALTER TABLE "job_instance" ADD "dispatch_version" NUMBER(32);
ALTER TABLE "job_instance" ADD "execute_once" NUMBER(2);

-- ----------------------------
-- Table structure for job_instance_task
-- ----------------------------
ALTER TABLE "job_instance_task" ADD "dispatch_version" NUMBER(32);

-- ----------------------------
-- COMMENT
-- ----------------------------
COMMENT ON COLUMN "job_instance"."dispatch_version" IS 'Dispatch version';

COMMENT ON COLUMN "job_instance"."execute_once" IS 'Execute once, 1=yes 2=no';

COMMENT ON COLUMN "job_instance_task"."dispatch_version" IS 'Dispatch version';

-- ----------------------------
-- UPDATE
-- ----------------------------
UPDATE "job_instance" SET "dispatch_version" = 0, "execute_once" = 2;
UPDATE "job_instance_task" SET "dispatch_version" = 0;
