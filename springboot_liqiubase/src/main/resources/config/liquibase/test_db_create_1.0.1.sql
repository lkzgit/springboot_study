--liquibase formatted sql

--changeset lkz:test.addColumn
ALTER TABLE hlms_dict add `dev_id` varchar(64) DEFAULT NULL COMMENT '报修设备主键dd';

--changeset lkz:test.updateColumn
ALTER TABLE hlms_dict CHANGE `dev_id` `devId` varchar(64) DEFAULT NULL COMMENT '报修设备主键';

