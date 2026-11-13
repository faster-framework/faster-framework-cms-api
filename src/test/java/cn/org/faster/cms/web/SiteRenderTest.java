package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.service.ArticleService;
import cn.org.faster.cms.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 站点动态渲染测试。
 */
class SiteRenderTest extends ApiTestSupport {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private ArticleService articleService;

    private Section section;
    private Article article;

    @BeforeEach
    void prepareContent() {
        Section draft = new Section();
        draft.setName("动态栏目");
        draft.setCode("render-" + UUID.randomUUID());
        draft.setDescription("用于渲染测试");
        draft.setTemplateType(Section.TEMPLATE_NORMAL);
        draft.setTemplatePath("section/normal.ftl");
        draft.setArticleTemplatePath("article/article.ftl");
        draft.setPublishStatus(1);
        draft.setShowStatus(1);
        section = sectionService.create(draft);

        Article draftArticle = new Article();
        draftArticle.setTitle("动态文章标题");
        draftArticle.setContent("<p>正文内容</p>");
        draftArticle.setSectionId(section.getId());
        draftArticle.setPublishStatus(1);
        draftArticle.setShowStatus(1);
        article = articleService.create(draftArticle);
    }

    @Test
    void sectionPageRendersArticles() throws Exception {
        mockMvc.perform(get("/s/" + section.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("动态栏目")))
                .andExpect(content().string(containsString("动态文章标题")));
    }

    @Test
    void articlePageRendersContent() throws Exception {
        mockMvc.perform(get("/a/" + article.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("动态文章标题")))
                .andExpect(content().string(containsString("正文内容")));
    }

    @Test
    void unknownSectionReturns404() throws Exception {
        mockMvc.perform(get("/s/not-exists"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unpublishedArticleIsNotVisible() throws Exception {
        Article draft = new Article();
        draft.setTitle("未发布文章");
        draft.setSectionId(section.getId());
        draft.setPublishStatus(0);
        draft.setShowStatus(1);
        Article saved = articleService.create(draft);

        mockMvc.perform(get("/a/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
}
