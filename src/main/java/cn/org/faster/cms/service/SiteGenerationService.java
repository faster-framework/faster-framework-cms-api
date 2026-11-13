package cn.org.faster.cms.service;

import cn.org.faster.cms.common.TemplateNames;
import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 全站静态化。
 *
 * <p>逐栏目渲染并生成文章页；单个页面失败不会中断整体任务，失败明细随报告返回。
 * 每页的写入都使用 try-with-resources，避免半截文件与句柄泄漏。</p>
 */
@Slf4j
@Service
public class SiteGenerationService {

    private final Configuration freemarker;
    private final CmsProperties properties;
    private final SectionService sectionService;
    private final ArticleService articleService;
    private final SiteRenderService renderService;
    private final StaticSiteService staticSite;

    public SiteGenerationService(Configuration freemarker,
                                 CmsProperties properties,
                                 SectionService sectionService,
                                 ArticleService articleService,
                                 SiteRenderService renderService,
                                 StaticSiteService staticSite) {
        this.freemarker = freemarker;
        this.properties = properties;
        this.sectionService = sectionService;
        this.articleService = articleService;
        this.renderService = renderService;
        this.staticSite = staticSite;
    }

    /**
     * 生成报告。
     *
     * @param pages    成功页面数
     * @param failed   失败页面数
     * @param duration 耗时（毫秒）
     * @param errors   失败明细
     */
    public record GenerationReport(int pages, int failed, long duration, List<String> errors) {
    }

    public GenerationReport generate() {
        long start = System.nanoTime();
        int[] counter = {0};
        List<String> errors = new ArrayList<>();

        render("index.ftl", "index.html", renderService.indexModel(), counter, errors);

        for (Section section : sectionService.listAll()) {
            if (!StringUtils.hasText(section.getTemplatePath())) {
                continue;
            }
            // 先清理旧页面，避免分页数量变化后残留
            staticSite.deleteSectionPages(section.getCode());
            generateSection(section, counter, errors);
            generateArticles(section, counter, errors);
        }

        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        log.info("Site generated: pages={}, failed={}, duration={}ms",
                counter[0], errors.size(), duration);
        return new GenerationReport(counter[0], errors.size(), duration, List.copyOf(errors));
    }

    private void generateSection(Section section, int[] counter, List<String> errors) {
        if (section.isPaged()) {
            long total = articleService.countBySection(section.getId());
            int size = properties.pageSize();
            long pages = total == 0 ? 1 : (total + size - 1) / size;
            for (int page = 1; page <= pages; page++) {
                Map<String, Object> model = renderService.sectionModel(section, page);
                render(TemplateNames.templateName(section.getTemplatePath()), "s/" + section.getCode() + "/" + page + ".html",
                        model, counter, errors);
                if (page == 1) {
                    render(TemplateNames.templateName(section.getTemplatePath()), "s/" + section.getCode() + ".html",
                            model, counter, errors);
                }
            }
        } else {
            Map<String, Object> model = renderService.sectionModel(section, null);
            render(TemplateNames.templateName(section.getTemplatePath()), "s/" + section.getCode() + ".html",
                    model, counter, errors);
        }
    }

    private void generateArticles(Section section, int[] counter, List<String> errors) {
        if (!StringUtils.hasText(section.getArticleTemplatePath())) {
            return;
        }
        List<Article> articles = articleService.list(
                ArticleService.Query.site().withSectionId(section.getId()), properties.listMax());
        for (Article article : articles) {
            Map<String, Object> model = renderService.articleModel(article, section);
            render(TemplateNames.templateName(section.getArticleTemplatePath()), "a/" + article.getId() + ".html",
                    model, counter, errors);
        }
    }

    private void render(String templateName, String relativePath, Map<String, Object> model,
                        int[] counter, List<String> errors) {
        Path target = staticSite.resolve(relativePath);
        Map<String, Object> context = new HashMap<>();
        context.put(properties.contextPrefix(), model);
        try {
            Template template = freemarker.getTemplate(templateName);
            if (target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }
            try (Writer writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                template.process(context, writer);
            }
            counter[0]++;
        } catch (IOException | TemplateException | RuntimeException ex) {
            errors.add(relativePath + ": " + ex.getMessage());
            log.warn("Failed to generate {} with template {}", relativePath, templateName, ex);
        }
    }
}
