/*
 Navicat MySQL Data Transfer

 Source Server         : 外网测试服务器
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 139.199.23.31:3306
 Source Schema         : faster-cms

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 16/12/2019 15:46:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '字典key',
  `type` varchar(128) DEFAULT NULL COMMENT '类型',
  `dict_value` longtext COMMENT '字典值',
  `show_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '展示状态（0.不展示 1.展示）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (232859096359895040, '111', '111', '111', 1, 0, 0, '2019-12-02 11:51:53', '2019-12-02 11:51:53', 0, '111', 1);
INSERT INTO `sys_dict` VALUES (232863405952729088, 'name', 'type', '1', 1, 0, 0, '2019-12-02 12:09:00', '2019-12-02 12:09:00', 0, '1', 1);
INSERT INTO `sys_dict` VALUES (232863688996945920, '121212', 'type', '12', 1, 0, 0, '2019-12-02 12:10:08', '2019-12-02 12:10:08', 0, NULL, 1);
INSERT INTO `sys_dict` VALUES (232886200787009536, '顶部导航', 'category:position', 'top-nav', 1, 0, 0, '2019-12-02 13:39:35', '2019-12-05 10:16:39', 11, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886248065204224, '底部导航', 'category:position', 'footer-nav', 1, 0, 0, '2019-12-02 13:39:46', '2019-12-02 13:39:46', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886374452166656, '顶部菜单', 'category:position', 'top-menu', 1, 0, 0, '2019-12-02 13:40:16', '2019-12-02 13:40:16', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886452973731840, '底部菜单', 'category:position', 'footer-menu', 1, 0, 0, '2019-12-02 13:40:35', '2019-12-02 13:40:35', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886532162191360, '列表', 'category:reqType', 'list', 1, 0, 0, '2019-12-02 13:40:54', '2019-12-02 13:40:54', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886578974818304, '链接', 'category:reqType', 'url', 1, 0, 0, '2019-12-02 13:41:05', '2019-12-02 13:41:05', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886630912884736, '富文本', 'category:reqType', 'editor', 1, 0, 0, '2019-12-02 13:41:17', '2019-12-02 13:41:17', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886854553174016, '自定义模板', 'category:reqType', 'template', 1, 0, 0, '2019-12-02 13:42:11', '2019-12-02 13:42:11', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (232886894545862656, '无跳转', 'category:reqType', 'none', 1, 0, 0, '2019-12-02 13:42:20', '2019-12-02 13:42:20', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233283241098870784, '微信', 'contact:weixin', '18764050615', 1, 0, 0, '2019-12-03 15:57:17', '2019-12-03 15:57:34', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233283281246748672, 'qq', 'contact:qq', '515445681', 1, 0, 0, '2019-12-03 15:57:26', '2019-12-03 16:00:39', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233284163078193152, '微博', 'contact:weibo', '没有', 1, 0, 0, '2019-12-03 16:00:56', '2019-12-03 16:00:56', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233284256309182464, '公司地址', 'contact:address', '山东省济南市历下区', 1, 0, 0, '2019-12-03 16:01:19', '2019-12-03 16:01:19', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233284401843142656, '邮箱', 'contact:email', '515445681@qq.com', 1, 0, 0, '2019-12-03 16:01:53', '2019-12-03 16:01:53', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233284483049062400, '电话', 'contact:phone', '18764050615', 1, 0, 0, '2019-12-03 16:02:13', '2019-12-03 16:02:13', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233284647507722240, '版权信息', 'copyright', '©济南小龟信息科技有限公司', 1, 0, 0, '2019-12-03 16:02:52', '2019-12-03 16:02:52', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233658463597625344, '首页轮播图', 'category:position', 'banner', 1, 0, 0, '2019-12-04 16:48:17', '2019-12-05 15:07:36', 0, NULL, 0);
INSERT INTO `sys_dict` VALUES (233661863877935104, '其他', 'category:position', 'none', 1, 0, 0, '2019-12-04 17:01:47', '2019-12-05 15:07:46', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `code` varchar(64) NOT NULL COMMENT '权限code',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `parent_ids` longtext NOT NULL COMMENT '父id列表（排序使用）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (37614050900705280, '权限管理', 'permissions:manage', 0, '[0]', 32907538873712640, 32907538873712640, '2018-06-11 17:17:46', '2018-06-11 17:17:46', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37614233789136896, '权限列表', 'permissions:list', 37614050900705280, '[0],[37614050900705280]', 32907538873712640, 32907538873712640, '2018-06-11 17:18:30', '2018-06-11 17:18:30', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37614578846138368, '权限添加', 'permissions:add', 37614050900705280, '[0],[37614050900705280]', 32907538873712640, 32907538873712640, '2018-06-11 17:19:52', '2018-06-11 17:19:52', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37614579462701056, '权限删除', 'permissions:delete', 37614050900705280, '[0],[37614050900705280]', 32907538873712640, 32907538873712640, '2018-06-11 17:19:52', '2018-06-11 17:19:52', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37614579856965632, '权限编辑', 'permissions:modify', 37614050900705280, '[0],[37614050900705280]', 32907538873712640, 32907538873712640, '2018-06-11 17:19:52', '2018-06-11 17:19:52', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37614580247035904, '权限详情', 'permissions:info', 37614050900705280, '[0],[37614050900705280]', 32907538873712640, 32907538873712640, '2018-06-11 17:19:53', '2018-06-11 17:19:53', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615037732356096, '角色管理', 'roles:manage', 0, '[0]', 32907538873712640, 0, '2018-06-11 17:21:42', '2018-07-23 17:40:06', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615359716491264, '角色列表', 'roles:list', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:22:58', '2018-06-11 17:22:58', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615360337248256, '角色添加', 'roles:add', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:22:59', '2018-06-11 17:22:59', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615360702152704, '角色删除', 'roles:delete', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:22:59', '2018-06-11 17:22:59', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615361062862848, '角色编辑', 'roles:modify', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:22:59', '2018-06-11 17:22:59', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615361457127424, '角色详情', 'roles:info', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:22:59', '2018-06-11 17:22:59', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615593091760128, '用户管理', 'users:manage', 0, '[0]', 32907538873712640, 32907538873712640, '2018-06-11 17:23:54', '2018-06-11 17:23:54', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615786340122624, '用户列表', 'users:list', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:24:40', '2018-06-11 17:24:40', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615787048960000, '用户添加', 'users:add', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:24:40', '2018-06-11 17:24:40', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615787480973312, '用户删除', 'users:delete', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:24:40', '2018-06-11 17:24:40', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615787912986624, '用户编辑', 'users:modify', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:24:41', '2018-06-11 17:24:41', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37615788382748672, '用户详情', 'users:info', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:24:41', '2018-06-11 17:24:41', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37616009674227712, '用户修改密码', 'users:password:change', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:25:33', '2018-06-11 17:25:33', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37616123360837632, '用户重置密码', 'users:password:reset', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:26:00', '2018-06-11 17:26:00', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37616523644239872, '用户选择权限', 'roles:permissions:choose', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:27:36', '2018-06-11 17:27:36', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (37616673557053440, '用户选择角色', 'users:roles:choose', 37615593091760128, '[0],[37615593091760128]', 32907538873712640, 32907538873712640, '2018-06-11 17:28:12', '2018-06-11 17:28:12', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (43409414580862976, '角色权限列表', 'roles:permissions:list', 37615037732356096, '[0],[37615037732356096]', 32907538873712640, 32907538873712640, '2018-06-11 17:27:36', '2018-06-11 17:27:36', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (47660043591483392, '权限树', 'permissions:tree', 37614050900705280, '[0],[37614050900705280]', 0, 0, '2018-07-09 10:36:58', '2018-07-09 10:36:58', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (102551194987134976, '用户角色列表', 'users:roles:list', 37615593091760128, '[0],[37615593091760128]', 0, 0, '2018-12-07 21:54:48', '2018-12-07 21:54:48', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334243177922560, '字典管理', 'dict:manage', 0, '[0]', 0, 0, '2019-10-31 16:36:14', '2019-10-31 16:36:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334624826032128, '字典列表', 'dict:list', 221334243177922560, '[0],[221334243177922560]', 0, 0, '2019-10-31 16:37:45', '2019-10-31 16:37:45', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334692169777152, '字典添加', 'dict:add', 221334243177922560, '[0],[221334243177922560]', 0, 0, '2019-10-31 16:38:01', '2019-10-31 16:38:01', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334755814146048, '字典编辑', 'dict:modify', 221334243177922560, '[0],[221334243177922560]', 0, 0, '2019-10-31 16:38:16', '2019-10-31 16:38:16', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334915914924032, '字典删除', 'dict:delete', 221334243177922560, '[0],[221334243177922560]', 0, 0, '2019-10-31 16:38:54', '2019-10-31 16:38:54', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (221334992398057472, '字典详情', 'dict:info', 221334243177922560, '[0],[221334243177922560]', 0, 0, '2019-10-31 16:39:12', '2019-10-31 16:39:12', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454074667008, '文章管理', 'article:manage', 0, '[0]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454074667009, '文章添加', 'article:add', 231799454074667008, '[0],[231799454074667008]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221440, '文章编辑', 'article:modify', 231799454074667008, '[0],[231799454074667008]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221441, '文章列表', 'article:list', 231799454074667008, '[0],[231799454074667008]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221442, '文章删除', 'article:delete', 231799454074667008, '[0],[231799454074667008]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221443, '文章详情', 'article:info', 231799454074667008, '[0],[231799454074667008]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221444, '分类管理', 'category:manage', 0, '[0]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221445, '分类添加', 'category:add', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454108221446, '分类编辑', 'category:modify', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454112415744, '分类列表', 'category:list', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454112415745, '分类删除', 'category:delete', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (231799454112415746, '分类详情', 'category:info', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-11-29 13:41:14', '2019-11-29 13:41:14', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (232884423559741440, '分类树', 'category:tree', 231799454108221444, '[0],[231799454108221444]', 0, 0, '2019-12-02 13:32:31', '2019-12-02 13:32:31', 0, NULL, 0);
INSERT INTO `sys_permission` VALUES (237934442906124288, '站点管理', 'site:manage', 0, '[0]', 0, 0, '2019-12-16 11:59:30', '2019-12-16 11:59:30', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (0, '超级管理员', NULL, NULL, '2018-05-29 17:35:47', '2018-05-29 17:35:47', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `account` varchar(64) NOT NULL COMMENT '账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (0, 'admin', '21232f297a57a5a743894a0e4a801fc3', '超级管理员', NULL, NULL, '2018-05-29 17:35:46', '2018-05-29 17:35:46', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(10240) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (0, 0, 0, NULL, NULL, '2018-06-11 17:47:05', '2018-06-11 17:47:08', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `title` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `description` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `img` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '封面图片',
  `category_id` bigint(20) NOT NULL COMMENT '菜单id',
  `publish_status` tinyint(4) NOT NULL COMMENT '发布状态（0. 未发布 1.已发布）',
  `publish_date` datetime DEFAULT NULL COMMENT '发布时间',
  `content` longtext COLLATE utf8mb4_bin NOT NULL COMMENT '内容（富文本）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章';

-- ----------------------------
-- Records of tb_article
-- ----------------------------
BEGIN;
INSERT INTO `tb_article` VALUES (1, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, '2019-12-05 15:26:49', '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 15:26:49', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (2, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, '2019-12-05 15:26:52', '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 15:26:52', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (3, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, '2019-12-05 15:27:07', '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 15:27:07', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (4, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (5, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (6, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (7, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (8, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (9, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (10, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (11, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (12, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (13, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (14, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (15, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (16, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (17, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (18, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (19, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (20, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (21, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (22, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (23, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (24, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (25, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (26, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (27, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (28, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (29, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (30, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (31, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (32, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (33, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (34, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (35, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (36, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (37, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (38, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (39, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (40, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (42, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (43, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (44, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (45, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (46, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (47, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (48, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (49, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (50, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (51, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (52, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (53, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (54, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (55, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (56, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (57, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (58, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (60, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (61, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (441, '我们的案例1', NULL, 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, NULL, '<p>这是案例的内容</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 14:30:30', 0, NULL, 0);
INSERT INTO `tb_article` VALUES (233986180327145472, '我们的案例1', 'asassaasassa', 'http://127.0.0.1:8080/media/2019120514301317.png', 233995711752962048, 1, '2019-12-05 15:26:42', '<p>这是案例的内容asa</p>', 0, 0, '2019-12-05 14:30:30', '2019-12-05 15:34:21', 0, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(36) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `code` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `position` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '位置（存储在字典中）',
  `description` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `img` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '封面图片',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `parent_ids` longtext COLLATE utf8mb4_bin NOT NULL COMMENT '全部父id',
  `req_type` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '跳转类型（存储在字典中）',
  `req_location` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '跳转地址（基于跳转类型生成跳转地址）',
  `publish_status` tinyint(4) NOT NULL COMMENT '发布状态（0. 未发布 1.已发布）',
  `show_status` tinyint(4) NOT NULL COMMENT '展示状态（0. 隐藏  1. 展示）',
  `template_path` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板地址',
  `content_template_path` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容模板地址',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后更新人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最后更新时间',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0.,否 1.是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='分类';

-- ----------------------------
-- Records of tb_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_category` VALUES (233654130126618624, '首页', 'home', 'top-nav', 'Home', NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:31:04', '2019-12-13 13:43:28', 1, NULL, 0);
INSERT INTO `tb_category` VALUES (233654293381513216, '关于我们', 'about', 'top-nav', 'About', NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:31:42', '2019-12-13 13:43:25', 2, NULL, 0);
INSERT INTO `tb_category` VALUES (233654461384359936, '我们的服务', 'service', 'none', '巨擘文化发展公司，我们的业务', NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:32:22', '2019-12-13 13:43:14', 12, NULL, 0);
INSERT INTO `tb_category` VALUES (233654630062489600, '案例展示', 'demos', 'top-nav', 'Case', NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:33:03', '2019-12-13 13:43:18', 4, NULL, 0);
INSERT INTO `tb_category` VALUES (233655006945869824, '联系我们', 'contact', 'top-nav', 'Contact', NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:34:33', '2019-12-13 13:43:01', 5, NULL, 0);
INSERT INTO `tb_category` VALUES (233658714995818496, 'banner', 'banner', 'banner', NULL, NULL, 0, '[0]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-04 16:49:17', '2019-12-13 13:43:32', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233660090211631104, 'banner1', 'banner1', 'banner', NULL, 'http://127.0.0.1:8080/media/2019120416530879.jpg', 233658714995818496, '[0],[233658714995818496]', 'editor', NULL, 1, 1, NULL, 'article/info.ftl', 0, 0, '2019-12-04 16:54:45', '2019-12-04 16:55:30', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233660254166974464, 'banner2', 'banner2', 'banner', NULL, 'http://127.0.0.1:8080/media/2019120416552196.jpg', 233658714995818496, '[0],[233658714995818496]', 'editor', NULL, 1, 1, NULL, 'article/info.ftl', 0, 0, '2019-12-04 16:55:24', '2019-12-04 16:55:24', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233662126974042112, '文化项目运行', 'service1', 'none', NULL, 'http://127.0.0.1:8080/media/2019120417024209.png', 233654461384359936, '[0],[233654461384359936]', 'none', NULL, 1, 1, NULL, 'article/info.ftl', 0, 0, '2019-12-04 17:02:50', '2019-12-04 17:02:50', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233664094098423808, '影视节目制作', 'service2', 'none', NULL, 'http://127.0.0.1:8080/media/2019120417103793.png', 233654461384359936, '[0],[233654461384359936]', 'none', NULL, 1, 1, NULL, 'article/info.ftl', 0, 0, '2019-12-04 17:10:39', '2019-12-04 17:10:39', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233664481454981120, '服务内容', 'service-nav', 'top-nav', 'Service', NULL, 0, '[0]', 'url', NULL, 1, 1, NULL, 'article/info.ftl', 0, 0, '2019-12-04 17:12:11', '2019-12-04 17:12:47', 3, NULL, 0);
INSERT INTO `tb_category` VALUES (233995711752962048, '案例1', 'anli1', 'none', NULL, NULL, 233654630062489600, '[0],[233654630062489600]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-05 15:08:23', '2019-12-05 15:08:32', 0, NULL, 0);
INSERT INTO `tb_category` VALUES (233996003844292608, '案例2', 'anli2', 'none', 'wwww', NULL, 233654630062489600, '[0],[233654630062489600]', 'list', NULL, 1, 1, 'article/list.ftl', 'article/info.ftl', 0, 0, '2019-12-05 15:09:33', '2019-12-05 15:09:33', 0, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
