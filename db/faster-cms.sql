-- Faster CMS 2.0 schema (MySQL 8.0+)
-- 字符集统一 utf8mb4；主键由应用侧雪花算法生成（ASSIGN_ID），不使用自增。

CREATE DATABASE IF NOT EXISTS `faster-cms` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `faster-cms`;

-- ----------------------------
-- 文章
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id`                    BIGINT       NOT NULL COMMENT '主键',
  `title`                 VARCHAR(256) NOT NULL COMMENT '标题',
  `code`                  VARCHAR(256) DEFAULT NULL COMMENT '编码',
  `description`           VARCHAR(4096) DEFAULT NULL COMMENT '描述',
  `img`                   VARCHAR(1024) DEFAULT NULL COMMENT '封面图',
  `section_id`            BIGINT       NOT NULL COMMENT '栏目id',
  `publish_date`          DATETIME     DEFAULT NULL COMMENT '发布时间',
  `publish_status`        TINYINT      NOT NULL DEFAULT 0 COMMENT '发布状态 0未发布 1已发布',
  `show_status`           TINYINT      NOT NULL DEFAULT 0 COMMENT '列表展示 0隐藏 1展示',
  `top_status`            TINYINT      NOT NULL DEFAULT 0 COMMENT '置顶 0否 1是',
  `content`               LONGTEXT     COMMENT '富文本内容',
  `resource_url`          VARCHAR(1024) DEFAULT NULL COMMENT '资源地址',
  `create_by`             BIGINT       DEFAULT NULL,
  `update_by`             BIGINT       DEFAULT NULL,
  `create_date`           DATETIME     NOT NULL,
  `update_date`           DATETIME     NOT NULL,
  `sort`                  INT          NOT NULL DEFAULT 0,
  `remark`                VARCHAR(1024) DEFAULT NULL,
  `deleted`               TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_article_section` (`section_id`, `publish_status`, `sort`, `publish_date`),
  KEY `idx_article_code` (`code`),
  KEY `idx_article_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章';

-- ----------------------------
-- 栏目
-- ----------------------------
DROP TABLE IF EXISTS `tb_section`;
CREATE TABLE `tb_section` (
  `id`                    BIGINT       NOT NULL COMMENT '主键',
  `name`                  VARCHAR(256) NOT NULL COMMENT '名称',
  `code`                  VARCHAR(256) NOT NULL COMMENT '编码',
  `description`           VARCHAR(4096) DEFAULT NULL,
  `img`                   VARCHAR(1024) DEFAULT NULL,
  `parent_id`             BIGINT       NOT NULL DEFAULT 0 COMMENT '父id',
  `parent_ids`            VARCHAR(1024) NOT NULL DEFAULT '[0]' COMMENT '祖先链',
  `template_type`         TINYINT      NOT NULL DEFAULT 1 COMMENT '模板类型 1普通 2分页',
  `template_path`         VARCHAR(256) DEFAULT NULL COMMENT '栏目模板',
  `article_template_path` VARCHAR(256) DEFAULT NULL COMMENT '文章模板',
  `link`                  VARCHAR(256) DEFAULT NULL,
  `publish_status`        TINYINT      NOT NULL DEFAULT 0,
  `show_status`           TINYINT      NOT NULL DEFAULT 0,
  `top_status`            TINYINT      NOT NULL DEFAULT 0,
  `content`               LONGTEXT,
  `resource_url`          VARCHAR(1024) DEFAULT NULL,
  `create_by`             BIGINT       DEFAULT NULL,
  `update_by`             BIGINT       DEFAULT NULL,
  `create_date`           DATETIME     NOT NULL,
  `update_date`           DATETIME     NOT NULL,
  `sort`                  INT          NOT NULL DEFAULT 0,
  `remark`                VARCHAR(1024) DEFAULT NULL,
  `deleted`               TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_section_code` (`code`),
  KEY `idx_section_parent` (`parent_id`, `sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目';

-- ----------------------------
-- 用户
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`          BIGINT       NOT NULL,
  `account`     VARCHAR(64)  NOT NULL,
  `password`    VARCHAR(100) NOT NULL COMMENT 'BCrypt 哈希',
  `name`        VARCHAR(64)  NOT NULL,
  `create_by`   BIGINT       DEFAULT NULL,
  `update_by`   BIGINT       DEFAULT NULL,
  `create_date` DATETIME     NOT NULL,
  `update_date` DATETIME     NOT NULL,
  `sort`        INT          NOT NULL DEFAULT 0,
  `remark`      VARCHAR(1024) DEFAULT NULL,
  `deleted`     TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户';

-- ----------------------------
-- 角色
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`          BIGINT      NOT NULL,
  `name`        VARCHAR(64) NOT NULL,
  `create_by`   BIGINT      DEFAULT NULL,
  `update_by`   BIGINT      DEFAULT NULL,
  `create_date` DATETIME    NOT NULL,
  `update_date` DATETIME    NOT NULL,
  `sort`        INT         NOT NULL DEFAULT 0,
  `remark`      VARCHAR(1024) DEFAULT NULL,
  `deleted`     TINYINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- 权限
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id`          BIGINT      NOT NULL,
  `name`        VARCHAR(64) NOT NULL,
  `code`        VARCHAR(64) NOT NULL,
  `parent_id`   BIGINT      NOT NULL DEFAULT 0,
  `parent_ids`  VARCHAR(1024) NOT NULL DEFAULT '[0]',
  `create_by`   BIGINT      DEFAULT NULL,
  `update_by`   BIGINT      DEFAULT NULL,
  `create_date` DATETIME    NOT NULL,
  `update_date` DATETIME    NOT NULL,
  `sort`        INT         NOT NULL DEFAULT 0,
  `remark`      VARCHAR(1024) DEFAULT NULL,
  `deleted`     TINYINT     NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_permission_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';

-- ----------------------------
-- 用户-角色 / 角色-权限
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id`          BIGINT   NOT NULL,
  `user_id`     BIGINT   NOT NULL,
  `role_id`     BIGINT   NOT NULL,
  `create_by`   BIGINT   DEFAULT NULL,
  `update_by`   BIGINT   DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  `sort`        INT      NOT NULL DEFAULT 0,
  `remark`      VARCHAR(1024) DEFAULT NULL,
  `deleted`     TINYINT  NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id`            BIGINT   NOT NULL,
  `role_id`       BIGINT   NOT NULL,
  `permission_id` BIGINT   NOT NULL,
  `create_by`     BIGINT   DEFAULT NULL,
  `update_by`     BIGINT   DEFAULT NULL,
  `create_date`   DATETIME NOT NULL,
  `update_date`   DATETIME NOT NULL,
  `sort`          INT      NOT NULL DEFAULT 0,
  `remark`        VARCHAR(1024) DEFAULT NULL,
  `deleted`       TINYINT  NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系';

-- ----------------------------
-- 字典
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id`          BIGINT       NOT NULL,
  `name`        VARCHAR(128) DEFAULT NULL,
  `type`        VARCHAR(128) DEFAULT NULL,
  `dict_value`  LONGTEXT,
  `show_status` TINYINT      NOT NULL DEFAULT 1,
  `create_by`   BIGINT       DEFAULT NULL,
  `update_by`   BIGINT       DEFAULT NULL,
  `create_date` DATETIME     NOT NULL,
  `update_date` DATETIME     NOT NULL,
  `sort`        INT          NOT NULL DEFAULT 0,
  `remark`      VARCHAR(1024) DEFAULT NULL,
  `deleted`     TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_dict_type` (`type`, `sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典';

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- 超级管理员：admin / admin（BCrypt cost 10，请首次登录后立即修改）
INSERT INTO `sys_user` (`id`, `account`, `password`, `name`, `create_date`, `update_date`, `sort`, `deleted`)
VALUES (0, 'admin', '$2a$10$dio.IefY8pDHV6iV.RXXCeVIbhUi5fHxLMrztwpCNSYqPFdGOipka', '超级管理员', NOW(), NOW(), 0, 0);

INSERT INTO `sys_role` (`id`, `name`, `create_date`, `update_date`, `sort`, `deleted`)
VALUES (0, '超级管理员', NOW(), NOW(), 0, 0);

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_date`, `update_date`, `sort`, `deleted`)
VALUES (1, 0, 0, NOW(), NOW(), 0, 0);

-- 权限：7 个模块，共 45 条
INSERT INTO `sys_permission` (`id`, `name`, `code`, `parent_id`, `parent_ids`, `create_date`, `update_date`, `sort`, `deleted`) VALUES
(1,  '权限管理', 'permissions:manage', 0, '[0]', NOW(), NOW(), 10, 0),
(2,  '权限列表', 'permissions:list',   1, '[0],[1]', NOW(), NOW(), 11, 0),
(3,  '权限详情', 'permissions:info',   1, '[0],[1]', NOW(), NOW(), 12, 0),
(4,  '权限新增', 'permissions:add',    1, '[0],[1]', NOW(), NOW(), 13, 0),
(5,  '权限编辑', 'permissions:modify', 1, '[0],[1]', NOW(), NOW(), 14, 0),
(6,  '权限删除', 'permissions:delete', 1, '[0],[1]', NOW(), NOW(), 15, 0),
(7,  '权限树',   'permissions:tree',   1, '[0],[1]', NOW(), NOW(), 16, 0),

(10, '角色管理', 'roles:manage', 0, '[0]', NOW(), NOW(), 20, 0),
(11, '角色列表', 'roles:list',   10, '[0],[10]', NOW(), NOW(), 21, 0),
(12, '角色详情', 'roles:info',   10, '[0],[10]', NOW(), NOW(), 22, 0),
(13, '角色新增', 'roles:add',    10, '[0],[10]', NOW(), NOW(), 23, 0),
(14, '角色编辑', 'roles:modify', 10, '[0],[10]', NOW(), NOW(), 24, 0),
(15, '角色删除', 'roles:delete', 10, '[0],[10]', NOW(), NOW(), 25, 0),
(16, '角色权限分配', 'roles:permissions:choose', 10, '[0],[10]', NOW(), NOW(), 26, 0),
(17, '角色权限列表', 'roles:permissions:list',   10, '[0],[10]', NOW(), NOW(), 27, 0),

(20, '用户管理', 'users:manage', 0, '[0]', NOW(), NOW(), 30, 0),
(21, '用户列表', 'users:list',   20, '[0],[20]', NOW(), NOW(), 31, 0),
(22, '用户详情', 'users:info',   20, '[0],[20]', NOW(), NOW(), 32, 0),
(23, '用户新增', 'users:add',    20, '[0],[20]', NOW(), NOW(), 33, 0),
(24, '用户编辑', 'users:modify', 20, '[0],[20]', NOW(), NOW(), 34, 0),
(25, '用户删除', 'users:delete', 20, '[0],[20]', NOW(), NOW(), 35, 0),
(26, '用户改密', 'users:password:change', 20, '[0],[20]', NOW(), NOW(), 36, 0),
(27, '用户重置密码', 'users:password:reset', 20, '[0],[20]', NOW(), NOW(), 37, 0),
(28, '用户角色分配', 'users:roles:choose', 20, '[0],[20]', NOW(), NOW(), 38, 0),
(29, '用户角色列表', 'users:roles:list',   20, '[0],[20]', NOW(), NOW(), 39, 0),

(30, '字典管理', 'dict:manage', 0, '[0]', NOW(), NOW(), 40, 0),
(31, '字典列表', 'dict:list',   30, '[0],[30]', NOW(), NOW(), 41, 0),
(32, '字典详情', 'dict:info',   30, '[0],[30]', NOW(), NOW(), 42, 0),
(33, '字典新增', 'dict:add',    30, '[0],[30]', NOW(), NOW(), 43, 0),
(34, '字典编辑', 'dict:modify', 30, '[0],[30]', NOW(), NOW(), 44, 0),
(35, '字典删除', 'dict:delete', 30, '[0],[30]', NOW(), NOW(), 45, 0),

(40, '文章管理', 'article:manage', 0, '[0]', NOW(), NOW(), 50, 0),
(41, '文章列表', 'article:list',   40, '[0],[40]', NOW(), NOW(), 51, 0),
(42, '文章详情', 'article:info',   40, '[0],[40]', NOW(), NOW(), 52, 0),
(43, '文章新增', 'article:add',    40, '[0],[40]', NOW(), NOW(), 53, 0),
(44, '文章编辑', 'article:modify', 40, '[0],[40]', NOW(), NOW(), 54, 0),
(45, '文章删除', 'article:delete', 40, '[0],[40]', NOW(), NOW(), 55, 0),

(50, '栏目管理', 'section:manage', 0, '[0]', NOW(), NOW(), 60, 0),
(51, '栏目列表', 'section:list',   50, '[0],[50]', NOW(), NOW(), 61, 0),
(52, '栏目详情', 'section:info',   50, '[0],[50]', NOW(), NOW(), 62, 0),
(53, '栏目新增', 'section:add',    50, '[0],[50]', NOW(), NOW(), 63, 0),
(54, '栏目编辑', 'section:modify', 50, '[0],[50]', NOW(), NOW(), 64, 0),
(55, '栏目删除', 'section:delete', 50, '[0],[50]', NOW(), NOW(), 65, 0),
(56, '栏目树',   'section:tree',   50, '[0],[50]', NOW(), NOW(), 66, 0),

(60, '站点管理', 'site:manage', 0, '[0]', NOW(), NOW(), 70, 0);

-- 字典示例
INSERT INTO `sys_dict` (`id`, `name`, `type`, `dict_value`, `show_status`, `create_date`, `update_date`, `sort`, `remark`, `deleted`) VALUES
(101, '站点地址', 'site-url', 'http://localhost:8080', 1, NOW(), NOW(), 0, '站点访问地址', 0),
(102, '普通栏目模板', 'section-template', 'section/normal.ftl', 1, NOW(), NOW(), 1, NULL, 0),
(103, '分页栏目模板', 'section-template', 'section/page.ftl', 1, NOW(), NOW(), 2, NULL, 0),
(104, '文章模板', 'article-template', 'article/article.ftl', 1, NOW(), NOW(), 3, NULL, 0);
