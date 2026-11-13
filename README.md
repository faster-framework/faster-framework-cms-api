# Faster CMS API 2.0

基于 **JDK 17 + Spring Boot 4.1.2 + Spring Framework 7.0.10** 重写的内容管理系统后端，面向企业官网、新闻站点等资讯型网站。

它同时提供三件事：

1. **站点前台渲染**：`/`、`/s/{栏目}`、`/a/{文章}` 由 FreeMarker 服务端渲染，并提供 `<@section>` / `<@article>` / `<@dict>` 三个自定义标签；
2. **站点静态化**：`POST /admin/site/generate` 生成全站 HTML，运行时静态页优先命中，未命中才回落动态渲染；
3. **后台管理 API**：文章、栏目、字典、用户、角色、权限（RBAC）的 REST 接口，Spring Security 7 + JWT 鉴权、方法级权限控制。

> 1.x 的架构分析与 2.0 的迁移清单见 [INTRO.md](INTRO.md) 与 [MIGRATION.md](MIGRATION.md)。

---

## 技术栈

| 组件 | 版本 | 说明 |
|---|---|---|
| JDK | 17 | `maven.compiler.release=17` |
| Spring Boot | 4.1.2 | 官方 parent，统一依赖管理 |
| Spring Framework | 7.0.10 | 由 Boot BOM 管理 |
| Spring Security | 7.1.2 | 替代原 Shiro，JWT + 方法级权限 |
| MyBatis-Plus | 3.5.18 | `mybatis-plus-spring-boot4-starter` |
| FreeMarker | 2.3.35 | 模板后缀固定为 `.ftl` |
| MySQL | 8.0+ | `utf8mb4`，雪花主键 |
| H2 | 2.4 | 仅测试，`MODE=MySQL` |

不再依赖任何 `cn.org.faster:*` 私有 starter，`mvn package` 可在干净环境直接构建。

---

## 目录结构

```
src/main/java/cn/org/faster/cms
├── Application.java                  # 启动类（@ConfigurationPropertiesScan + @MapperScan）
├── common/                           # BaseEntity、PageResult、错误码、异常处理、树工具、模板名转换
├── config/                           # 配置属性、WebMvc、MyBatis-Plus、FreeMarker 标签注册
├── domain/                           # 实体（Article/Section/SysUser/...）
├── mapper/                           # MyBatis-Plus BaseMapper
├── security/                         # JWT、TokenDenylist、UserDetails、SecurityConfig
├── service/                          # 领域服务（文章/栏目/字典/静态化/RBAC/认证/验证码/上传）
├── directive/                        # 模板标签：BaseDirective + TagContext + 3 个实现
├── web/
│   ├── StaticPageInterceptor.java    # 静态页拦截
│   ├── admin/                        # 后台 REST 接口
│   ├── site/                         # 前台页面控制器
│   └── dto/                          # Java 17 record 请求体
└── resources/{application*.yml,templates/}
src/test/java/cn/org/faster/cms         # 43 个测试（单元 + MockMvc 集成）
db/faster-cms.sql                       # MySQL 建表 + 初始化数据
```

---

## 快速开始

### 1. 准备数据库

```bash
mysql -uroot -p < db/faster-cms.sql
```

脚本会创建 `faster-cms` 库、8 张表、45 条权限、4 条字典，以及超级管理员账号：

```
账号：admin
密码：admin
```

> 生产环境请登录后立即修改密码（密码使用 BCrypt 存储）。

### 2. 配置并启动

```bash
export DB_HOST=127.0.0.1 DB_PORT=3306 DB_NAME=faster-cms DB_USERNAME=root DB_PASSWORD=yourpass
export JWT_SECRET='至少32字节的随机密钥，务必覆盖默认值'
mvn spring-boot:run
```

或打包运行：

```bash
mvn clean package
java -jar target/faster-cms-api-2.0.0.jar --spring.profiles.active=prod
```

### 3. 访问

| 地址 | 说明 |
|---|---|
| `http://localhost:8080/` | 站点首页 |
| `http://localhost:8080/s/{code}` | 栏目页 |
| `http://localhost:8080/a/{id}` | 文章详情 |
| `http://localhost:8080/admin/login` | 后台登录接口 |

---

## 配置项

| 配置 | 默认值 | 说明 |
|---|---|---|
| `app.cms.site-dir` | `site` | 静态页输出目录 |
| `app.cms.page-size` | `20` | 栏目分页每页条数 |
| `app.cms.context-prefix` | `cms` | 模板数据模型根变量名 |
| `app.cms.list-max` | `500` | 模板标签 `list=true` 的单次上限 |
| `app.jwt.secret` | 开发默认值 | HS256 密钥，**至少 32 字节** |
| `app.jwt.ttl` | `PT2H` | token 有效期 |
| `app.captcha.enabled` | `true` | 是否启用图形验证码 |
| `app.captcha.ttl` | `PT2M` | 验证码有效期 |
| `app.upload.dir` | `data/upload` | 上传落盘目录 |
| `app.upload.url-prefix` | `/media` | 上传访问前缀 |
| `app.upload.max-size` | `10MB` | 单文件上限 |
| `app.upload.allowed-types` | png,jpg,jpeg,gif,webp,pdf,zip | 扩展名白名单 |
| `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USERNAME` / `DB_PASSWORD` | 本地 MySQL | 数据源环境变量 |

---

## 模板标签

所有标签共用一套参数，`data` 为默认输出变量名（可用 `as` 改名）。

| 参数 | 说明 |
|---|---|
| `list=true` | 列表模式，返回全部（受 `list-max` 限制） |
| `page=true` | 分页模式，页码/页长取自全局 `cms.current` / `cms.size` |
| `size` / `current` | 显式指定条数 / 页码 |
| `id` / `code` | 主键 / 编码 |
| `pid` / `pcode` | 栏目父 id / 父栏目编码 |
| `sid` / `scode` | 文章所属栏目 id / 栏目编码 |
| `type` / `name` | 字典类型 / 名称 |
| `show` | 是否只取展示中的数据（默认 `true`） |
| `top` | 是否置顶 |
| `exclude` | 排除某个 id |
| `as` | 输出变量名（默认 `data`） |

示例：

```freemarker
<@section list=true pid=0>
  <#list data as item><a href="/s/${item.code}/">${item.name?html}</a></#list>
</@section>

<@article page=true sid=cms.s.id size=cms.size current=cms.current>
  <#list data as item><a href="/a/${item.id}/">${item.title?html}</a></#list>
</@article>

<@dict name='site-url' as='item'>${item.dictValue}</@dict>
```

模板上下文：

| 场景 | 变量 |
|---|---|
| 通用 | `cms` |
| 栏目页 | `cms.s`、`cms.p`、`cms.plist` |
| 栏目分页 | `cms.current`、`cms.size`、`cms.pages`、`cms.total`、`cms.hasp`、`cms.hasn`、`cms.pnum`、`cms.nnum` |
| 文章页 | `cms.art`、`cms.ps`、`cms.pslist` |

> 栏目的 `template_path` / `article_template_path` 支持带或不带 `.ftl` 后缀，均会正确解析。

---

## 接口概览

统一响应：

- 成功：`200` / `201` / `204`；
- 失败：RFC 7807 `application/problem+json`，包含 `title`、`status`、`detail` 与业务码 `code`。

### 认证

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/admin/captcha` | 图形验证码，返回 `{token, image(data URI)}` |
| POST | `/admin/login` | `{account,password,captcha,captchaToken}` → `{token,expiresAt}` |
| GET | `/admin/userInfo` | 当前用户与权限码 |
| DELETE | `/admin/logout` | 登出并立即使当前 token 失效 |

### 内容

| 方法 | 路径 | 权限 |
|---|---|---|
| GET | `/admin/article` | `article:list` |
| GET | `/admin/article/{id}`、`/admin/article/query` | `article:info` |
| POST / PUT / DELETE | `/admin/article`、`/admin/article/{id}`、`/admin/article/delete` | `article:add/modify/delete` |
| GET | `/admin/section/tree`、`/admin/section`、`/admin/section/{id}` | `section:tree/list/info` |
| POST / PUT / DELETE | `/admin/section`、`/admin/section/{id}`、`/admin/section/delete` | `section:add/modify/delete` |
| GET | `/admin/sys/dict`、`/admin/sys/dict/all` | `dict:list` / 登录即可 |
| POST | `/admin/site/generate` | `site:manage` |
| POST | `/admin/media/upload` | 登录即可（白名单 + 大小限制） |

### RBAC

`/admin/sys/users`、`/admin/sys/roles`、`/admin/sys/permissions` 提供标准 CRUD，以及：

| 方法 | 路径 | 说明 |
|---|---|---|
| PUT | `/admin/sys/users/{id}/roles/choose` | 请求体 `{"roleIds":[...]}` |
| PUT | `/admin/sys/roles/{id}/permissions/choose` | 请求体 `{"permissionIds":[...]}` |
| PUT | `/admin/sys/users/{id}/password/change` | `{oldPassword,newPassword}` |
| PUT | `/admin/sys/users/{id}/password/reset` | 返回一次性随机密码 `{password}` |

---

## 安全设计

- **密码**：BCrypt（cost 10），重置密码为随机 12 位，仅在响应中出现一次；
- **Token**：HS256，默认 2 小时过期；登出立即进入黑名单；改密/删除用户会使该用户此前所有 token 失效；
- **权限**：`@PreAuthorize("hasAuthority('article:list')")`，权限码来自 `sys_permission`；
- **验证码**：一次性、带过期时间、进程内存储（多实例部署需替换为共享存储）；
- **静态页**：路径 `normalize` + 根目录前缀校验，防止路径穿越；
- **上传**：需登录、扩展名白名单、大小限制、随机文件名、独立静态目录；
- **模板**：`template_path` 仅允许站点模板目录内路径。

> 生产部署前务必：覆盖 `JWT_SECRET`、修改 admin 密码、启用 HTTPS、把 `TokenDenylist` 换成 Redis。

---

## 测试

```bash
mvn test        # 43 个测试
mvn verify      # 测试 + 打包
```

测试基于 H2（`MODE=MySQL`）与 MockMvc，覆盖：

- 认证：登录、错误密码、未登录 401、登出后 token 失效；
- 文章：创建、发布态、标题模糊检索、编码冲突、删除；
- 栏目：树构建、移动栏目重写子孙 `parent_ids`、循环移动拦截、删除保护；
- 静态化：生成文件、静态页优先于动态渲染、删除后静态页失效、模板错误上报；
- RBAC：角色授权后按权限放行/拒绝；
- 单元：JWT 签发/校验、树工具、模板名转换、验证码。

---

## 文档

| 文件 | 内容 |
|---|---|
| [INTRO.md](INTRO.md) | 1.0 版本的架构分析与问题清单（历史参考） |
| [MIGRATION.md](MIGRATION.md) | 1.x → 2.0 的变更与升级步骤 |
| [db/faster-cms.sql](db/faster-cms.sql) | MySQL 建表与初始化数据 |

## License

[Apache License 2.0](LICENSE)
