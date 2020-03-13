package cn.org.faster.cms.admin.article.model.request;

import lombok.Data;
import cn.org.faster.framework.mybatis.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @author faster-builder
 * 文章 request
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleAddRequest extends BaseEntity{
    /**
     * 标题
     */
    private String title;
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
     * 栏目id
     */
    private Long sectionId;

    /**
     * 发布时间
     */
    private LocalDateTime publishDate;

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
     * 富文本内容
     */
    private String content;
    /**
     * 资源地址
     */
    private String resourceUrl;
}