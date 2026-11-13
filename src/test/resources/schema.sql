-- H2 (MODE=MySQL) schema for tests
DROP TABLE IF EXISTS tb_article;
CREATE TABLE tb_article (
    id BIGINT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    code VARCHAR(256),
    description VARCHAR(4096),
    img VARCHAR(1024),
    section_id BIGINT NOT NULL,
    publish_date DATETIME,
    publish_status TINYINT NOT NULL DEFAULT 0,
    show_status TINYINT NOT NULL DEFAULT 0,
    top_status TINYINT NOT NULL DEFAULT 0,
    content CLOB,
    resource_url VARCHAR(1024),
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS tb_section;
CREATE TABLE tb_section (
    id BIGINT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    code VARCHAR(256) NOT NULL,
    description VARCHAR(4096),
    img VARCHAR(1024),
    parent_id BIGINT NOT NULL DEFAULT 0,
    parent_ids VARCHAR(1024) NOT NULL DEFAULT '[0]',
    template_type TINYINT NOT NULL DEFAULT 1,
    template_path VARCHAR(256),
    article_template_path VARCHAR(256),
    link VARCHAR(256),
    publish_status TINYINT NOT NULL DEFAULT 0,
    show_status TINYINT NOT NULL DEFAULT 0,
    top_status TINYINT NOT NULL DEFAULT 0,
    content CLOB,
    resource_url VARCHAR(1024),
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY,
    account VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(64) NOT NULL,
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission (
    id BIGINT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    code VARCHAR(64) NOT NULL,
    parent_id BIGINT NOT NULL DEFAULT 0,
    parent_ids VARCHAR(1024) NOT NULL DEFAULT '[0]',
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
    id BIGINT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict (
    id BIGINT PRIMARY KEY,
    name VARCHAR(128),
    type VARCHAR(128),
    dict_value CLOB,
    show_status TINYINT NOT NULL DEFAULT 1,
    create_by BIGINT,
    update_by BIGINT,
    create_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    sort INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024),
    deleted TINYINT NOT NULL DEFAULT 0
);
