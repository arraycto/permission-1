/*
 Navicat Premium Data Transfer

 Source Server         : 权限模板-开发
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : tx.unclezs.com:3306
 Source Schema         : permission

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 27/03/2020 18:14:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '$2a$10$tNceMQPGqx7.NM9ubsPQw.rVN4AmWVEYUTweYg80p9qeHzohdM1aq', 'Uncle', '15023814324', '1585503311@qq.com', 1);

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `aid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员ID',
  `rid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`aid`, `rid`) USING BTREE,
  INDEX `id_role`(`rid`) USING BTREE,
  CONSTRAINT `id_admin` FOREIGN KEY (`aid`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_role` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '1');
INSERT INTO `admin_role` VALUES ('1', '2');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `op_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名称',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者IP',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者用户名',
  `spend_time` int(0) NULL DEFAULT NULL COMMENT '耗时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('136d3bc4b9cc93cc9ea81a4ab52a2101', '2020-02-24 12:41:21', '删除管理员信息', '127.0.0.1', 'admin', 129);
INSERT INTO `log` VALUES ('160c2429d7b9d8f71020cbedf4dd29bf', '2020-02-24 12:41:49', '删除管理员信息', '127.0.0.1', 'admin', 137);
INSERT INTO `log` VALUES ('25782665653b4b65f6cddba693b0d9e9', '2020-02-24 12:40:58', '清空日志', '127.0.0.1', 'admin', 160);
INSERT INTO `log` VALUES ('a24e292c37da921e1ee9ba78fe6fc794', '2020-02-24 12:41:11', '删除角色', '127.0.0.1', 'admin', 122);
INSERT INTO `log` VALUES ('cd106044924a618d90bb2de7e4051b99', '2020-02-24 13:58:36', '管理员登陆', '127.0.0.1', 'admin', 9);
INSERT INTO `log` VALUES ('f2e133d3d1026555c9d15d0f6d95f763', '2020-02-24 12:49:13', '管理员登陆', '127.0.0.1', 'admin', 2);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父菜单ID',
  `component` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件名称',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单类型(1侧边栏菜单 2按钮菜单)',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由路径',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单图标样式',
  `req_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求Path，用于权限',
  `order_by` int(0) NOT NULL COMMENT '排序',
  `hidden` tinyint(1) NOT NULL COMMENT '是否隐藏',
  `keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', '0', '/Home', '1', '/system', 'el-icon-help', '/system', 1, 0, 1);
INSERT INTO `menu` VALUES ('2', '管理员管理', '1', '/system/admin', '1', '/system/admin', 'el-icon-user-solid', '/admin/query/**', 2, 0, 1);
INSERT INTO `menu` VALUES ('2_1', '添加管理员', '2', '', '2', 'add', 'el-icon-setting', '/admin/add/**', 2, 1, 1);
INSERT INTO `menu` VALUES ('2_2', '删除管理员', '2', '', '2', 'del', 'el-icon-setting', '/admin/del/**', 3, 1, 1);
INSERT INTO `menu` VALUES ('2_3', '编辑管理员', '2', '', '2', 'edit', 'el-icon-setting', '/admin/edit/**', 3, 1, 1);
INSERT INTO `menu` VALUES ('3', '角色管理', '1', '/system/role', '1', '/system/role', 'el-icon-s-opportunity', '/role/query/**', 3, 0, 1);
INSERT INTO `menu` VALUES ('3_1', '添加角色', '3', '', '2', 'add', 'el-icon-setting', '/role/add/**', 2, 1, 1);
INSERT INTO `menu` VALUES ('3_2', '删除角色', '3', '', '2', 'del', 'el-icon-setting', '/role/del/**', 3, 1, 1);
INSERT INTO `menu` VALUES ('3_3', '编辑角色', '3', '', '2', 'edit', 'el-icon-setting', 'role/edit/**', 3, 1, 1);
INSERT INTO `menu` VALUES ('4', '菜单管理', '1', '/system/menu', '1', '/system/menu', 'el-icon-s-unfold', '/menu/query/**', 3, 0, 1);
INSERT INTO `menu` VALUES ('4_1', '添加菜单', '4', '', '2', 'add', 'el-icon-setting', '/menu/add/**', 2, 1, 1);
INSERT INTO `menu` VALUES ('4_2', '删除菜单', '4', '', '2', 'del', 'el-icon-setting', '/menu/del/**', 3, 1, 1);
INSERT INTO `menu` VALUES ('4_3', '编辑菜单', '4', '', '2', 'edit', 'el-icon-setting', '/menu/edit/**', 3, 1, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `introduce` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色介绍',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `name_zh` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '拥有至高无上的权力', '2020-02-15 16:20:15', 1, '超级管理员');
INSERT INTO `role` VALUES ('2', 'user', '普通管理员权力', '2020-02-15 16:20:58', 1, '普通管理员');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `rid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `mid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`rid`, `mid`) USING BTREE,
  INDEX `menu_id`(`mid`) USING BTREE,
  CONSTRAINT `menu_id` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_id` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('2', '1');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('2', '2');
INSERT INTO `role_menu` VALUES ('1', '2_1');
INSERT INTO `role_menu` VALUES ('2', '2_1');
INSERT INTO `role_menu` VALUES ('1', '2_2');
INSERT INTO `role_menu` VALUES ('1', '2_3');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('2', '3');
INSERT INTO `role_menu` VALUES ('1', '3_1');
INSERT INTO `role_menu` VALUES ('2', '3_1');
INSERT INTO `role_menu` VALUES ('1', '3_2');
INSERT INTO `role_menu` VALUES ('1', '3_3');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '4_1');
INSERT INTO `role_menu` VALUES ('1', '4_2');
INSERT INTO `role_menu` VALUES ('1', '4_3');

SET FOREIGN_KEY_CHECKS = 1;
