package cn.org.faster.cms.web.dto;

import cn.org.faster.cms.domain.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 文章请求体。
 */
public final class ArticleDtos {

    private ArticleDtos() {
    }

    public record Create(
            @NotBlank(message = "标题不能为空") @Size(max = 256, message = "标题最长 256 字符") String title,
            @Size(max = 256, message = "编码最长 256 字符") String code,
            @Size(max = 4096, message = "描述最长 4096 字符") String description,
            String img,
            @NotNull(message = "请选择栏目") Long sectionId,
            Integer publishStatus,
            Integer showStatus,
            Integer topStatus,
            String content,
            String resourceUrl,
            Integer sort,
            String remark
    ) {

        public Article toEntity() {
            Article article = new Article();
            article.setTitle(title);
            article.setCode(code);
            article.setDescription(description);
            article.setImg(img);
            article.setSectionId(sectionId);
            article.setPublishStatus(publishStatus);
            article.setShowStatus(showStatus);
            article.setTopStatus(topStatus);
            article.setContent(content);
            article.setResourceUrl(resourceUrl);
            article.setSort(sort);
            article.setRemark(remark);
            return article;
        }
    }

    public record Update(
            @Size(max = 256, message = "标题最长 256 字符") String title,
            @Size(max = 256, message = "编码最长 256 字符") String code,
            @Size(max = 4096, message = "描述最长 4096 字符") String description,
            String img,
            Long sectionId,
            Integer publishStatus,
            Integer showStatus,
            Integer topStatus,
            String content,
            String resourceUrl,
            Integer sort,
            String remark
    ) {

        public Article toEntity() {
            Article article = new Article();
            article.setTitle(title);
            article.setCode(code);
            article.setDescription(description);
            article.setImg(img);
            article.setSectionId(sectionId);
            article.setPublishStatus(publishStatus);
            article.setShowStatus(showStatus);
            article.setTopStatus(topStatus);
            article.setContent(content);
            article.setResourceUrl(resourceUrl);
            article.setSort(sort);
            article.setRemark(remark);
            return article;
        }
    }
}
