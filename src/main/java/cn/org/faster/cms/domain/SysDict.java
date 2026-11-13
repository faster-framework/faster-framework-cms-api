package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典。
 */
@Getter
@Setter
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    /** 字典 key */
    private String name;

    /** 字典类型 */
    private String type;

    /** 字典值 */
    private String dictValue;

    /** 0 不展示 / 1 展示 */
    private Integer showStatus;
}
