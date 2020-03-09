package cn.org.faster.cms.api.section.model.request;

import lombok.Data;
import cn.org.faster.framework.mybatis.entity.BaseEntity;
import lombok.EqualsAndHashCode;

/**
 * @author faster-builder
 * 栏目 request
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SectionListRequest extends BaseEntity{
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
}