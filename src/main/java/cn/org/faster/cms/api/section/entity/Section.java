package cn.org.faster.cms.api.section.entity;

import cn.org.faster.framework.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author faster-builder
 * 栏目 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_section")
public class Section extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
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
     * 模板类型（存储在字典中）
     */
    private Integer templateType;
    /**
     * 模板地址
     */
    private String templatePath;
    /**
     * 链接地址
     */
    private String link;
    /**
     * 是否发布（0. 未发布 1.已发布）
     */
    private Integer publishStatus;
    /**
     * 列表是否展示（0. 隐藏  1. 展示）
     */
    private Integer showStatus;
    /**
     * 是否置顶（0. 否 1. 是）
     */
    private Integer topStatus;
    /**
     * 文章模板地址
     */
    private String articleTemplatePath;
    /**
     * 富文本内容
     */
    private String content;
    /**
     * 资源地址
     */
    private String resourceUrl;
}