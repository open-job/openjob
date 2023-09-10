-- ----------------------------
-- Table structure for job_instance
-- ----------------------------
ALTER TABLE "job_instance"
    ADD COLUMN  "dispatch_version" numeric(20,0);
ALTER TABLE "job_instance"
    ADD COLUMN  "execute_once" int2;

COMMENT ON COLUMN "job_instance"."dispatch_version" IS 'Dispatch version';
COMMENT ON COLUMN "job_instance"."execute_once" IS 'Execute once, 1=yes 2=no';

UPDATE "job_instance" SET "dispatch_version" = 0, "execute_once" = 2;
-- ----------------------------
-- Table structure for job_instance_task
-- ----------------------------
ALTER TABLE "job_instance_task"
    ADD COLUMN  "dispatch_version" numeric(20,0);

COMMENT ON COLUMN "job_instance_task"."dispatch_version" IS 'Dispatch version';

UPDATE "job_instance_task" SET "dispatch_version" = 0;