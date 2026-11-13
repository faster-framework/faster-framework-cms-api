-- 测试种子数据：管理员 admin / admin
INSERT INTO sys_user (id, account, password, name, create_date, update_date, sort, deleted)
VALUES (0, 'admin', '$2a$10$dio.IefY8pDHV6iV.RXXCeVIbhUi5fHxLMrztwpCNSYqPFdGOipka', '超级管理员', NOW(), NOW(), 0, 0);

INSERT INTO sys_role (id, name, create_date, update_date, sort, deleted)
VALUES (0, '超级管理员', NOW(), NOW(), 0, 0);

INSERT INTO sys_user_role (id, user_id, role_id, create_date, update_date, sort, deleted)
VALUES (1, 0, 0, NOW(), NOW(), 0, 0);

INSERT INTO sys_permission (id, name, code, parent_id, parent_ids, create_date, update_date, sort, deleted) VALUES
(1, '权限管理', 'permissions:manage', 0, '[0]', NOW(), NOW(), 10, 0),
(2, '权限列表', 'permissions:list', 1, '[0],[1]', NOW(), NOW(), 11, 0),
(3, '权限详情', 'permissions:info', 1, '[0],[1]', NOW(), NOW(), 12, 0),
(4, '权限新增', 'permissions:add', 1, '[0],[1]', NOW(), NOW(), 13, 0),
(5, '权限编辑', 'permissions:modify', 1, '[0],[1]', NOW(), NOW(), 14, 0),
(6, '权限删除', 'permissions:delete', 1, '[0],[1]', NOW(), NOW(), 15, 0),
(7, '权限树', 'permissions:tree', 1, '[0],[1]', NOW(), NOW(), 16, 0),
(10, '角色管理', 'roles:manage', 0, '[0]', NOW(), NOW(), 20, 0),
(11, '角色列表', 'roles:list', 10, '[0],[10]', NOW(), NOW(), 21, 0),
(12, '角色详情', 'roles:info', 10, '[0],[10]', NOW(), NOW(), 22, 0),
(13, '角色新增', 'roles:add', 10, '[0],[10]', NOW(), NOW(), 23, 0),
(14, '角色编辑', 'roles:modify', 10, '[0],[10]', NOW(), NOW(), 24, 0),
(15, '角色删除', 'roles:delete', 10, '[0],[10]', NOW(), NOW(), 25, 0),
(16, '角色权限分配', 'roles:permissions:choose', 10, '[0],[10]', NOW(), NOW(), 26, 0),
(17, '角色权限列表', 'roles:permissions:list', 10, '[0],[10]', NOW(), NOW(), 27, 0),
(20, '用户管理', 'users:manage', 0, '[0]', NOW(), NOW(), 30, 0),
(21, '用户列表', 'users:list', 20, '[0],[20]', NOW(), NOW(), 31, 0),
(22, '用户详情', 'users:info', 20, '[0],[20]', NOW(), NOW(), 32, 0),
(23, '用户新增', 'users:add', 20, '[0],[20]', NOW(), NOW(), 33, 0),
(24, '用户编辑', 'users:modify', 20, '[0],[20]', NOW(), NOW(), 34, 0),
(25, '用户删除', 'users:delete', 20, '[0],[20]', NOW(), NOW(), 35, 0),
(26, '用户改密', 'users:password:change', 20, '[0],[20]', NOW(), NOW(), 36, 0),
(27, '用户重置密码', 'users:password:reset', 20, '[0],[20]', NOW(), NOW(), 37, 0),
(28, '用户角色分配', 'users:roles:choose', 20, '[0],[20]', NOW(), NOW(), 38, 0),
(29, '用户角色列表', 'users:roles:list', 20, '[0],[20]', NOW(), NOW(), 39, 0),
(30, '字典管理', 'dict:manage', 0, '[0]', NOW(), NOW(), 40, 0),
(31, '字典列表', 'dict:list', 30, '[0],[30]', NOW(), NOW(), 41, 0),
(32, '字典详情', 'dict:info', 30, '[0],[30]', NOW(), NOW(), 42, 0),
(33, '字典新增', 'dict:add', 30, '[0],[30]', NOW(), NOW(), 43, 0),
(34, '字典编辑', 'dict:modify', 30, '[0],[30]', NOW(), NOW(), 44, 0),
(35, '字典删除', 'dict:delete', 30, '[0],[30]', NOW(), NOW(), 45, 0),
(40, '文章管理', 'article:manage', 0, '[0]', NOW(), NOW(), 50, 0),
(41, '文章列表', 'article:list', 40, '[0],[40]', NOW(), NOW(), 51, 0),
(42, '文章详情', 'article:info', 40, '[0],[40]', NOW(), NOW(), 52, 0),
(43, '文章新增', 'article:add', 40, '[0],[40]', NOW(), NOW(), 53, 0),
(44, '文章编辑', 'article:modify', 40, '[0],[40]', NOW(), NOW(), 54, 0),
(45, '文章删除', 'article:delete', 40, '[0],[40]', NOW(), NOW(), 55, 0),
(50, '栏目管理', 'section:manage', 0, '[0]', NOW(), NOW(), 60, 0),
(51, '栏目列表', 'section:list', 50, '[0],[50]', NOW(), NOW(), 61, 0),
(52, '栏目详情', 'section:info', 50, '[0],[50]', NOW(), NOW(), 62, 0),
(53, '栏目新增', 'section:add', 50, '[0],[50]', NOW(), NOW(), 63, 0),
(54, '栏目编辑', 'section:modify', 50, '[0],[50]', NOW(), NOW(), 64, 0),
(55, '栏目删除', 'section:delete', 50, '[0],[50]', NOW(), NOW(), 65, 0),
(56, '栏目树', 'section:tree', 50, '[0],[50]', NOW(), NOW(), 66, 0),
(60, '站点管理', 'site:manage', 0, '[0]', NOW(), NOW(), 70, 0);

INSERT INTO sys_dict (id, name, type, dict_value, show_status, create_date, update_date, sort, remark, deleted) VALUES
(101, '站点地址', 'site-url', 'http://localhost:8080', 1, NOW(), NOW(), 0, NULL, 0),
(102, '普通栏目模板', 'section-template', 'section/normal.ftl', 1, NOW(), NOW(), 1, NULL, 0),
(103, '分页栏目模板', 'section-template', 'section/page.ftl', 1, NOW(), NOW(), 2, NULL, 0),
(104, '文章模板', 'article-template', 'article/article.ftl', 1, NOW(), NOW(), 3, NULL, 0);
