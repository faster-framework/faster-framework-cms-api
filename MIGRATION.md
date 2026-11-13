# 1.x → 2.0 迁移说明

2.0 是一次**整体重写**，不是增量升级：删除了内嵌的 `cn.org.faster.framework.*` 私有代码，改用 Spring Boot 官方生态组件；业务能力（文章 / 栏目 / 字典 / RBAC / 静态化 / 模板标签）保持不变，但包结构、依赖与部分接口契约有变化。

---

## 1. 技术栈变化

| 项 | 1.x | 2.0 |
|---|---|---|
| JDK | 8 | **17** |
| Spring Boot | 由私有 BOM 间接管理 | **4.1.2**（官方 parent） |
| Spring Framework | 5.x | **7.0.10** |
| Web | `spring-boot-starter-web` | `spring-boot-starter-webmvc` |
| 安全 | Apache Shiro 1.x + 私有 JWT 实现 | **Spring Security 7.1.2 + Nimbus JOSE** |
| 持久层 | 私有 `spring-boot-starter-mybatis` | `mybatis-plus-spring-boot4-starter` 3.5.18 + `mybatis-plus-jsqlparser` |
| 模板 | FreeMarker 2.3.28 | FreeMarker 2.3.35 |
| 依赖 | 4 个 `cn.org.faster:*` 私有 starter | **零私有依赖** |
| 测试 | 无 | 39 个单元/集成测试（H2 + MockMvc） |
| 错误响应 | 自定义 `ResponseErrorEntity` | RFC 7807 `ProblemDetail` |
| javax | `javax.*` | `jakarta.*` |

---

## 2. 包结构映射

| 1.x | 2.0 |
|---|---|
| `cn.org.faster.framework.admin.*`（43 个类，源码内嵌） | 删除；等价能力落到 `cms.security` / `cms.service` / `cms.web.admin` |
| `cms.admin.article.*`、`cms.admin.section.*` | `cms.domain` + `cms.service` + `cms.web.admin` |
| `cms.api.article.*`、`cms.api.section.*`、`cms.api.dict.*` | 合并进 `cms.service`（读写统一，删除重复模型） |
| `cms.common.config.*`、`cms.common.properties.*`、`cms.common.utils.*` | `cms.config`、`cms.common` |
| `cms.common.directive.*` | `cms.directive` |
| `cms.view.*` | `cms.web.site` |
| `cms.admin.site.strategy.*` | `cms.service.SiteGenerationService` + `SiteRenderService` |
| `META-INF/spring.factories` | 删除（不再需要自动配置注册） |

实体由 3 份（admin/api/request）合并为 1 份，查询条件用不可变 `record` 承载，去掉了约 500 行复制粘贴代码。

---

## 3. 认证与授权

| 能力 | 1.x | 2.0 |
|---|---|---|
| 密码存储 | MD5 无盐 | **BCrypt** |
| 默认账号 | `admin/admin` | `admin/admin`（BCrypt，首次登录请改密） |
| Token | 自研 JWT，`createToken(id, -1)`（疑似永不过期） | Nimbus HS256，默认 2 小时（`app.jwt.ttl`） |
| 登出 | 仅清授权缓存，token 仍有效 | **token 立即进黑名单** |
| 改密/删用户 | 旧 token 仍有效 | 该用户此前 token 全部失效 |
| 权限注解 | Shiro `@RequiresPermissions` | Spring Security `@PreAuthorize("hasAuthority('xxx')")` |
| 过滤器 | Shiro filter chain | `SecurityFilterChain` + `JwtAuthenticationFilter` |
| 多端登录 | 缓存中只保留一个 token | 支持多端（黑名单按 jti 失效） |

权限码（`article:list` 等）保持完全一致，因此 `sys_permission` 数据可直接沿用。

> `TokenDenylist` 与验证码均为进程内存储；多实例部署请替换为 Redis。

---

## 4. 接口契约变化

| 接口 | 1.x | 2.0 | 兼容性 |
|---|---|---|---|
| 登录响应 | `{token}` | `{token, expiresAt}` | 向后兼容（新增字段） |
| 错误响应 | 自定义 JSON | `application/problem+json`，含 `code` | **需前端适配** |
| 更新文章/栏目 | `201 Created` | `200 OK` + 更新后实体 | 状态码变化 |
| 新增文章/栏目 | `201` 无响应体 | `201` + 实体 | 向后兼容 |
| 用户选择角色 | `ListWrapper<SysUserRole>` | `{"roleIds":[1,2]}` | **需前端适配** |
| 角色选择权限 | `ListWrapper<SysRolePermission>` | `{"permissionIds":[1,2]}` | **需前端适配** |
| 重置密码 | `204`，固定 `123456` | `200` + 一次性随机密码 | **需前端适配** |
| 验证码 | 图片接口 | `{token, image}`，image 为 `data:image/png;base64,...` | **需前端适配** |
| 上传 | `/admin/media/**` 匿名 | `/admin/media/upload` 需登录，返回 `{url,name,size,contentType}` | **需前端适配** |
| 列表检索 | 标题精确匹配 | 标题/账号/角色模糊匹配 | 行为增强 |
| 静态化 | 恒返回 200 | 返回 `{pages,failed,duration,errors}` | 向后兼容 |

删除接口保持 `DELETE /delete` + JSON 数组请求体的约定。

---

## 5. 数据库变化

表结构基本兼容，新增/收紧如下：

```sql
-- 1. 模板类型不允许为空（修复 1.x 的 NPE 根因）
UPDATE tb_section SET template_type = 1 WHERE template_type IS NULL;
ALTER TABLE tb_section MODIFY template_type TINYINT NOT NULL DEFAULT 1;

-- 2. 索引（提升栏目页与静态化性能）
ALTER TABLE tb_article
  ADD INDEX idx_article_section (section_id, publish_status, sort, publish_date),
  ADD INDEX idx_article_code (code),
  ADD INDEX idx_article_publish_date (publish_date);
ALTER TABLE tb_section
  ADD INDEX idx_section_code (code),
  ADD INDEX idx_section_parent (parent_id, sort);
ALTER TABLE sys_user_role
  ADD UNIQUE KEY uk_user_role (user_id, role_id);
ALTER TABLE sys_role_permission
  ADD UNIQUE KEY uk_role_permission (role_id, permission_id);
ALTER TABLE sys_permission ADD INDEX idx_permission_code (code);
ALTER TABLE sys_dict ADD INDEX idx_dict_type (type, sort);

-- 3. 密码升级为 BCrypt（示例：admin / admin）
UPDATE sys_user
SET password = '$2a$10$dio.IefY8pDHV6iV.RXXCeVIbhUi5fHxLMrztwpCNSYqPFdGOipka'
WHERE account = 'admin';
```

全新安装直接执行 [db/faster-cms.sql](db/faster-cms.sql) 即可（已包含上述结构与初始化数据）。

> 1.x 的权限 id 为雪花值、2.0 脚本使用 1~60 的短 id；已有库**无需**改 id，只要 `sys_permission.code` 齐全即可，代码按 code 鉴权。

---

## 6. 模板与静态化

| 项 | 说明 |
|---|---|
| 后缀 | Boot 4 默认模板后缀变为 `.ftlh`，本项目显式固定为 `.ftl`（`spring.freemarker.suffix`） |
| 模板路径 | 数据库中的 `template_path` 带不带 `.ftl` 都能解析（`TemplateNames` 统一处理） |
| 标签参数 | 全部兼容；`list=true` 时 `size` 作为上限生效（1.x 忽略） |
| 日期 | FreeMarker 会把 `LocalDateTime` 当字符串，模板中请使用 `${item.publishDateText!}` 或 `${cms.art.publishDateText!}` |
| 转义 | 手工对普通字段使用 `?html`；富文本 `content` 原样输出 |
| 静态页失效 | 删除文章、下线文章、删除栏目、重新生成栏目时会清理对应 HTML（1.x 无此能力） |
| 静态化一致性 | 动态渲染与静态生成共用 `SiteRenderService`，`plist` 顺序一致 |
| 失败可见 | 生成失败会逐页记录在响应的 `errors` 中，不再静默吞异常 |
| 写入安全 | 全部使用 try-with-resources，避免半截文件与句柄泄漏 |
| 路径安全 | 静态文件与模板路径均做 `normalize` + 越界校验 |

---

## 7. 升级步骤

1. 备份数据库；
2. 执行第 5 节的 SQL；
3. 覆盖 `app.jwt.secret`（≥32 字节）、数据库口令等环境变量；
4. 部署 2.0 包，登录验证；
5. 按第 4 节调整管理前端（错误结构、验证码、上传、选择角色/权限的请求体）；
6. 重新执行一次「站点生成」：`POST /admin/site/generate`，确认 `failed=0`；
7. 观察 `failed` 明细，修复模板中使用了 `?string` 的日期表达式。

## 8. 回滚

2.0 在独立提交中完成，可直接 `git revert` 或切回 1.x 标签；数据库改动以新增索引与 `template_type` 收紧为主，回滚时无需还原数据（BCrypt 口令需回滚为 MD5 才能用 1.x 登录）。
