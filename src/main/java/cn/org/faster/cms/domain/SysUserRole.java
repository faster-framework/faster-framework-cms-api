package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户-角色关系。
 */
@Getter
@Setter
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    private Long userId;
    private Long roleId;
}
