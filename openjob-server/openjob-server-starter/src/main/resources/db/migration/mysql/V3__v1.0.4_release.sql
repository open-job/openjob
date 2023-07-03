# `admin_permission`
# ------------------------------------------------------------
ALTER TABLE `admin_permission` CHANGE `meta` `meta` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;

# `admin_role`
# ------------------------------------------------------------
ALTER TABLE `admin_role` CHANGE `menu_ids` `menu_ids` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;
ALTER TABLE `admin_role` CHANGE `perm_ids` `perm_ids` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;
ALTER TABLE `admin_role` CHANGE `namespace_ids` `namespace_ids` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;
ALTER TABLE `admin_role` CHANGE `app_ids` `app_ids` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;

# `admin_user`
# ------------------------------------------------------------
ALTER TABLE `admin_user` CHANGE `role_ids` `role_ids` TEXT  CHARACTER SET utf8mb4  COLLATE utf8mb4_general_ci  NOT NULL;

# `server`
# ------------------------------------------------------------
ALTER TABLE `server` DROP INDEX `udx_akka_address`;
ALTER TABLE `server` ADD UNIQUE INDEX `udx_akka_address` (`akka_address`);

# `worker`
# ------------------------------------------------------------
ALTER TABLE `worker` DROP INDEX `udx_address`;
ALTER TABLE `worker` ADD UNIQUE INDEX `udx_address` (`address`);