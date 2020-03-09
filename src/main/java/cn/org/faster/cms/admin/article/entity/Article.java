package cn.org.faster.cms.admin.article.entity;

import cn.org.faster.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @author faster-builder
 * 文章 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_article")
public class Article extends BaseEntity{
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
     * 栏目id
     */
    private Long sectionId;
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