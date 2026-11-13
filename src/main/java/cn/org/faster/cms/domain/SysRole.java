package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色。
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /** 内置超级管理员角色主键 */
    public static final long ADMIN_ROLE_ID = 0L;

    private String name;
}
