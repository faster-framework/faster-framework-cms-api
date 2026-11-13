package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色-权限关系。
 */
@Getter
@Setter
@TableName("sys_role_permission")
public class SysRolePermission extends BaseEntity {

    private Long roleId;
    private Long permissionId;
}
