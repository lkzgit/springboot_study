/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 25/06/2020 12:22:10
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `permission_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限值',
  `permission_type` tinyint(0) NOT NULL COMMENT '权限类型 0:一级目录 1:二级目录 2:菜单 3:按钮',
  `permission_state` tinyint(0) NOT NULL DEFAULT 1 COMMENT '权限状态 0:禁止 1:正常',
  `superior_id` int(0) NOT NULL COMMENT '上级id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '系统管理', 'system_manage', 0, 0, 0, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (2, '用户管理', 'system_manage:user_manage', 2, 0, 1, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (3, '新增用户', 'system_manage:user_manage:insert', 2, 0, 2, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (4, '编辑用户', 'system_manage:user_manage:update', 2, 0, 2, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (5, '删除用户', 'system_manage:user_manage:delete', 2, 0, 2, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (6, '用户查询', 'system_manage:user_manage:select', 2, 0, 2, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (7, '组织管理', 'system_manage:organization_manage', 1, 0, 1, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (8, '新增组织', 'system_manage:organization_manage:insert', 2, 0, 7, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (9, '编辑组织', 'system_manage:organization_manage:update', 2, 0, 7, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (10, '删除组织', 'system_manage:organization_manage:delete', 2, 0, 7, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (11, '查询组织', 'system_manage:organization_manage:select', 2, 0, 7, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (12, '角色管理', 'system_manage:role_manage', 1, 0, 1, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (13, '新增角色', 'system_manage:role_manage:insert', 2, 0, 12, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (14, '编辑角色', 'system_manage:role_manage:update', 2, 0, 12, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (15, '删除角色', 'system_manage:role_manage:delete', 2, 0, 12, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (16, '查询角色', 'system_manage:role_manage:select', 2, 0, 12, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (17, '角色权限', 'system_manage:role_manage:role_permission', 2, 0, 12, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (18, '权限管理', 'system_manage:permission_manage', 1, 0, 1, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (19, '新增权限', 'system_manage:permission_manage:insert', 2, 0, 19, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (20, '编辑权限', 'system_manage:permission_manage:update', 2, 0, 19, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (21, '删除权限', 'system_manage:permission_manage:delete', 2, 0, 19, '2020-04-25 11:19:00', '2020-04-25 11:19:00');
INSERT INTO `permission` VALUES (22, '查询权限', 'system_manage:permission_manage:select', 2, 0, 19, '2020-04-25 11:19:00', '2020-04-25 11:19:00');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `state` int(0) NULL DEFAULT 0 COMMENT '状态(0:可用,1:不可用)',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'root', '管理员', '管理系统中的所有权限', '2019-05-29 09:37:00', '2020-04-30 11:31:01', 0);
INSERT INTO `role` VALUES (2, 'chairman', '工会主席', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:43:49', 0);
INSERT INTO `role` VALUES (3, 'general_manager', '总经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:54:19', 0);
INSERT INTO `role` VALUES (4, 'Vice_General_Manager', '副总经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:44:42', 0);
INSERT INTO `role` VALUES (5, 'division_manager', '部门经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:44:55', 0);
INSERT INTO `role` VALUES (6, 'Deputy_Department_Manager', '部门副经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:45:08', 0);
INSERT INTO `role` VALUES (7, 'project_manager', '项目经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:45:21', 0);
INSERT INTO `role` VALUES (8, 'Project_Manager_Technical_Manager', '技术经理', '管理系统中的所有权限', '2019-05-28 09:37:00', '2020-06-25 10:45:45', 0);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_permission_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色权限表id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `permission_id` int(0) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);
INSERT INTO `role_permission` VALUES (6, 1, 6);
INSERT INTO `role_permission` VALUES (7, 1, 7);
INSERT INTO `role_permission` VALUES (8, 1, 8);
INSERT INTO `role_permission` VALUES (9, 1, 9);
INSERT INTO `role_permission` VALUES (10, 1, 10);
INSERT INTO `role_permission` VALUES (11, 1, 11);
INSERT INTO `role_permission` VALUES (12, 1, 12);
INSERT INTO `role_permission` VALUES (13, 1, 13);
INSERT INTO `role_permission` VALUES (14, 1, 14);
INSERT INTO `role_permission` VALUES (15, 1, 15);
INSERT INTO `role_permission` VALUES (16, 1, 16);
INSERT INTO `role_permission` VALUES (17, 1, 17);
INSERT INTO `role_permission` VALUES (18, 1, 18);
INSERT INTO `role_permission` VALUES (19, 1, 19);
INSERT INTO `role_permission` VALUES (20, 1, 20);
INSERT INTO `role_permission` VALUES (21, 1, 21);
INSERT INTO `role_permission` VALUES (22, 1, 22);
INSERT INTO `role_permission` VALUES (23, 2, 1);
INSERT INTO `role_permission` VALUES (24, 2, 2);
INSERT INTO `role_permission` VALUES (25, 2, 3);
INSERT INTO `role_permission` VALUES (26, 2, 4);
INSERT INTO `role_permission` VALUES (27, 2, 5);
INSERT INTO `role_permission` VALUES (28, 2, 6);
INSERT INTO `role_permission` VALUES (102, 3, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` int(0) NULL DEFAULT NULL COMMENT '性别(0:男,1:女)',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `state` int(0) NOT NULL DEFAULT 0 COMMENT '用户状态(0:可用,1:不可用,2:暂时锁定)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2880540', '大卫斯特恩', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187123456789', '187123456789@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (2, '2880541', '科比', '$2a$10$AivB3S9zWbk5H33fk2MKuu3o.TF5sxXZrGZmuNwT8vp3yUI8cRADW', '187111111111', '187111111111@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-06-25 00:00:30');
INSERT INTO `user` VALUES (3, '2880542', '詹姆斯', '$2a$10$xPuvr3c.h4UInSQcuZC7tOoF3lcpoU43vRxXlcYRM6j7QouLG67hu', '187222222222', '187222222222@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-06-25 08:50:46');
INSERT INTO `user` VALUES (4, '2880543', '乔丹', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187333333333', '187333333333@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (5, '2880544', '加内特', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187444444444', '187444444444@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (6, '2880545', '麦迪', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187555555555', '187555555555@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (7, '2880546', '邓肯', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187666666666', '187666666666@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (8, '2880547', '奥尼尔', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187777777777', '187777777777@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (9, '2880548', '艾弗森', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187888888888', '187888888888@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');
INSERT INTO `user` VALUES (10, '2880549', '皮蓬', '$2a$10$Dd6iNjTj4ozFUvSwb9xPT.6TkMyFiFAz4citIvpJU7FZJVsMsyXR.', '187999999999', '187999999999@qq.com', 0, '', 0, '2020-04-18 14:35:00', '2020-04-30 11:29:35');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户角色表id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (8, 2, 2);
INSERT INTO `user_role` VALUES (9, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
