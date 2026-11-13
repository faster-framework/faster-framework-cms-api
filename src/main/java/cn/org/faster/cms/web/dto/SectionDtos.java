package cn.org.faster.cms.web.dto;

import cn.org.faster.cms.domain.Section;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 栏目请求体。
 */
public final class SectionDtos {

    private SectionDtos() {
    }

    public record Create(
            @NotBlank(message = "栏目名称不能为空") @Size(max = 256, message = "名称最长 256 字符") String name,
            @NotBlank(message = "栏目编码不能为空") @Size(max = 256, message = "编码最长 256 字符") String code,
            @Size(max = 4096, message = "描述最长 4096 字符") String description,
            String img,
            Long parentId,
            @Min(value = 1, message = "模板类型只能为 1 或 2")
            @Max(value = 2, message = "模板类型只能为 1 或 2") Integer templateType,
            String templatePath,
            String articleTemplatePath,
            String link,
            Integer publishStatus,
            Integer showStatus,
            Integer topStatus,
            String content,
            String resourceUrl,
            Integer sort,
            String remark
    ) {

        public Section toEntity() {
            Section section = new Section();
            section.setName(name);
            section.setCode(code);
            section.setDescription(description);
            section.setImg(img);
            section.setParentId(parentId == null ? Section.ROOT_ID : parentId);
            section.setTemplateType(templateType);
            section.setTemplatePath(templatePath);
            section.setArticleTemplatePath(articleTemplatePath);
            section.setLink(link);
            section.setPublishStatus(publishStatus);
            section.setShowStatus(showStatus);
            section.setTopStatus(topStatus);
            section.setContent(content);
            section.setResourceUrl(resourceUrl);
            section.setSort(sort);
            section.setRemark(remark);
            return section;
        }
    }

    public record Update(
            @Size(max = 256, message = "名称最长 256 字符") String name,
            @Size(max = 256, message = "编码最长 256 字符") String code,
            @Size(max = 4096, message = "描述最长 4096 字符") String description,
            String img,
            Long parentId,
            @Min(value = 1, message = "模板类型只能为 1 或 2")
            @Max(value = 2, message = "模板类型只能为 1 或 2") Integer templateType,
            String templatePath,
            String articleTemplatePath,
            String link,
            Integer publishStatus,
            Integer showStatus,
            Integer topStatus,
            String content,
            String resourceUrl,
            Integer sort,
            String remark
    ) {

        public Section toEntity() {
            Section section = new Section();
            section.setName(name);
            section.setCode(code);
            section.setDescription(description);
            section.setImg(img);
            section.setParentId(parentId);
            section.setTemplateType(templateType);
            section.setTemplatePath(templatePath);
            section.setArticleTemplatePath(articleTemplatePath);
            section.setLink(link);
            section.setPublishStatus(publishStatus);
            section.setShowStatus(showStatus);
            section.setTopStatus(topStatus);
            section.setContent(content);
            section.setResourceUrl(resourceUrl);
            section.setSort(sort);
            section.setRemark(remark);
            return section;
        }
    }
}
