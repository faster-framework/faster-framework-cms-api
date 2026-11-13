package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.service.ArticleService;
import cn.org.faster.cms.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 静态化与静态页拦截测试。
 */
class SiteGenerationTest extends ApiTestSupport {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CmsProperties cmsProperties;

    private Section section;
    private Article first;
    private Article second;

    @BeforeEach
    void prepareContent() throws IOException {
        cleanSite();

        Section draft = new Section();
        draft.setName("静态栏目");
        draft.setCode("static-" + UUID.randomUUID());
        draft.setTemplateType(Section.TEMPLATE_NORMAL);
        draft.setTemplatePath("section/normal.ftl");
        draft.setArticleTemplatePath("article/article.ftl");
        draft.setPublishStatus(1);
        draft.setShowStatus(1);
        section = sectionService.create(draft);

        first = articleService.create(article("静态文章一"));
        second = articleService.create(article("静态文章二"));
    }

    @Test
    void generateWritesHtmlFiles() throws Exception {
        mockMvc.perform(post("/admin/site/generate").header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.failed").value(0))
                .andExpect(jsonPath("$.pages").value(org.hamcrest.Matchers.greaterThanOrEqualTo(4)));

        Path root = cmsProperties.siteDir();
        assertThat(root.resolve("index.html")).exists();
        assertThat(root.resolve("s/" + section.getCode() + ".html")).exists();
        assertThat(root.resolve("a/" + first.getId() + ".html")).exists();
        assertThat(root.resolve("a/" + second.getId() + ".html")).exists();
    }

    @Test
    void staticPageTakesPrecedenceOverDynamicRendering() throws Exception {
        mockMvc.perform(post("/admin/site/generate").header(authorization(), bearer()))
                .andExpect(status().isOk());

        first.setTitle("静态文章一（已改名）");
        first.setSectionId(null);
        articleService.update(first.getId(), first);

        mockMvc.perform(get("/s/" + section.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("静态文章一")))
                .andExpect(content().string(not(containsString("已改名"))));
    }

    @Test
    void deletingArticleRemovesStaticPage() throws Exception {
        mockMvc.perform(post("/admin/site/generate").header(authorization(), bearer()))
                .andExpect(status().isOk());
        Path articlePage = cmsProperties.siteDir().resolve("a/" + first.getId() + ".html");
        assertThat(articlePage).exists();

        articleService.delete(first.getId());

        assertThat(articlePage).doesNotExist();
        mockMvc.perform(get("/a/" + first.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void generateReportsTemplateErrors() throws Exception {
        Section broken = new Section();
        broken.setName("错误栏目");
        broken.setCode("broken-" + UUID.randomUUID());
        broken.setTemplateType(Section.TEMPLATE_NORMAL);
        broken.setTemplatePath("section/not-exists.ftl");
        broken.setPublishStatus(1);
        broken.setShowStatus(1);
        sectionService.create(broken);

        MvcResult result = mockMvc.perform(post("/admin/site/generate").header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        assertThat(body).contains("\"failed\"");
        assertThat(body).doesNotContain("\"failed\":0");
    }

    private Article article(String title) {
        Article draft = new Article();
        draft.setTitle(title);
        draft.setContent("<p>" + title + "</p>");
        draft.setSectionId(section.getId());
        draft.setPublishStatus(1);
        draft.setShowStatus(1);
        return draft;
    }

    private void cleanSite() throws IOException {
        Path root = cmsProperties.siteDir();
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(root)) {
            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    throw new java.io.UncheckedIOException(ex);
                }
            });
        }
    }
}
