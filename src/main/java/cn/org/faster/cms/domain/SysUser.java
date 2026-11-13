package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户。
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /** 内置超级管理员账号的主键 */
    public static final long ADMIN_ID = 0L;

    private String account;

    @JsonIgnore
    private String password;

    private String name;
}
