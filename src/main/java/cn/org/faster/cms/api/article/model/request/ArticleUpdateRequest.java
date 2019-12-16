package cn.org.faster.cms.api.article.model.request;

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
public class ArticleUpdateRequest extends BaseEntity{
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 封面图片
     */
    private String img;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 发布状态（0.未发布1.已发布）
     */
    private Integer publishStatus;
    /**
     * 发布时间
     */
    private LocalDateTime publishDate;

    /**
     * 富文本内容
     */
    private String content;
}