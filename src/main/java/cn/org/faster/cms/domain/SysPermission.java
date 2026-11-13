package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 权限（树形）。
 */
@Getter
@Setter
@TableName("sys_permission")
public class SysPermission extends BaseEntity {

    private String name;
    private String code;
    private Long parentId;
    private String parentIds;

    @TableField(exist = false)
    private List<SysPermission> children;
}
