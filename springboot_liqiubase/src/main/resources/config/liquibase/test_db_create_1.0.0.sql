--liquibase formatted sql

--changeset lkz:test.v1.0.0
CREATE TABLE IF NOT EXISTS `hlms_dict` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
    `dict_type_code` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典类型编码',
    `dict_code` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典编码',
    `dict_name` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典名称',
    `dict_sort` tinyint(3) DEFAULT NULL COMMENT '排序',
    `dict_status` tinyint(1) NOT NULL COMMENT '字典状态 （0-停用   1-启用）',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统字典表';
