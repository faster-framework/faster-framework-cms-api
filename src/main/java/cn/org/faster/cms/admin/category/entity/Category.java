package cn.org.faster.cms.admin.category.entity;

import cn.org.faster.framework.core.entity.TreeNode;
import cn.org.faster.framework.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author faster-builder
 * 分类 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_category")
public class Category extends BaseEntity implements TreeNode {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 位置（存储在字典中）
     */
    private String position;
    /**
     * 描述
     */
    private String description;
    /**
     * 封面图片
     */
    private String img;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 全部父id
     */
    private String parentIds;
    /**
     * 跳转类型（存储在字典中）
     */
    private String reqType;
    /**
     * 跳转地址（基于跳转类型生成跳转地址）
     */
    private String reqLocation;
    /**
     * 发布状态（0.未发布1.已发布）
     */
    private Integer publishStatus;
    /**
     * 展示状态（0.隐藏1.展示）
     */
    private Integer showStatus;
    /**
     * 模板地址
     */
    private String templatePath;
    /**
     * 内容模板地址
     */
    private String contentTemplatePath;
    /**
     * 子类
     */
    @TableField(exist = false)
    private List<TreeNode> children;
}