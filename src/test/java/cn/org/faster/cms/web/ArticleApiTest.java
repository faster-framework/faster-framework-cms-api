package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.mapper.ArticleMapper;
import cn.org.faster.cms.service.ArticleService;
import cn.org.faster.cms.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 文章接口测试。
 */
class ArticleApiTest extends ApiTestSupport {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    private Section section;

    @BeforeEach
    void createSection() {
        Section draft = new Section();
        draft.setName("新闻中心");
        draft.setCode("news-" + java.util.UUID.randomUUID());
        draft.setPublishStatus(1);
        draft.setShowStatus(1);
        draft.setTemplatePath("section/normal.ftl");
        draft.setArticleTemplatePath("article/article.ftl");
        section = sectionService.create(draft);
    }

    @Test
    void createArticleSetsPublishDate() throws Exception {
        mockMvc.perform(post("/admin/article")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"第一条新闻","code":"a1","sectionId":%d,
                                 "publishStatus":1,"showStatus":1,"content":"<p>hello</p>"}
                                """.formatted(section.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.publishDate").isNotEmpty());
    }

    @Test
    void updatingPublishedArticleKeepsOriginalPublishDate() throws Exception {
        Article article = new Article();
        article.setTitle("旧标题");
        article.setCode("fixed-code");
        article.setSectionId(section.getId());
        article.setPublishStatus(1);
        article.setShowStatus(1);
        article = articleService.create(article);

        Article patch = new Article();
        patch.setId(article.getId());
        patch.setPublishDate(LocalDateTime.of(2020, 1, 1, 8, 0));
        articleMapper.updateById(patch);

        mockMvc.perform(put("/admin/article/" + article.getId())
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"新标题","publishStatus":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("新标题"))
                .andExpect(jsonPath("$.publishDate").value("2020-01-01 08:00:00"));
    }

    @Test
    void duplicateCodeIsRejected() throws Exception {
        Article article = new Article();
        article.setTitle("已有文章");
        article.setCode("dup-code");
        article.setSectionId(section.getId());
        article.setPublishStatus(1);
        article.setShowStatus(1);
        articleService.create(article);

        mockMvc.perform(post("/admin/article")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"重复编码","code":"dup-code","sectionId":%d}
                                """.formatted(section.getId())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(1151));
    }

    @Test
    void listSupportsFuzzyTitleSearch() throws Exception {
        for (String title : new String[]{"Spring 实战", "Vue 入门", "Spring 进阶"}) {
            Article article = new Article();
            article.setTitle(title);
            article.setSectionId(section.getId());
            article.setPublishStatus(1);
            article.setShowStatus(1);
            articleService.create(article);
        }

        mockMvc.perform(get("/admin/article").param("title", "Spring")
                        .header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.records.length()").value(2));
    }

    @Test
    void deleteRemovesArticle() throws Exception {
        Article article = new Article();
        article.setTitle("待删除");
        article.setSectionId(section.getId());
        article.setPublishStatus(1);
        article.setShowStatus(1);
        Article created = articleService.create(article);

        mockMvc.perform(delete("/admin/article/delete")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" + created.getId() + "]"))
                .andExpect(status().isNoContent());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> articleService.getRequired(created.getId()))
                .isInstanceOf(cn.org.faster.cms.common.BizException.class);
    }
}
