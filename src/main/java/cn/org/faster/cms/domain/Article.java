package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 文章。
 */
@Getter
@Setter
@TableName("tb_article")
public class Article extends BaseEntity {

    private String title;
    private String code;
    private String description;
    private String img;
    private Long sectionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;

    /**
     * 供模板使用的格式化发布时间（FreeMarker 会把 java.time 类型当作字符串，无法再调用 ?string）。
     */
    @JsonIgnore
    public String getPublishDateText() {
        return publishDate == null ? null : publishDate.format(DATE_TEXT);
    }

    private static final java.time.format.DateTimeFormatter DATE_TEXT =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** 0 未发布 / 1 已发布 */
    private Integer publishStatus;

    /** 0 不展示 / 1 展示 */
    private Integer showStatus;

    /** 0 否 / 1 置顶 */
    private Integer topStatus;

    private String content;
    private String resourceUrl;

    public boolean published() {
        return Integer.valueOf(1).equals(publishStatus);
    }

    public boolean visible() {
        return Integer.valueOf(1).equals(showStatus);
    }
}
