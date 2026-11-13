package cn.org.faster.cms.directive;

import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.service.ArticleService;
import cn.org.faster.cms.service.SectionService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * {@code <@article ...>} 文章标签。
 *
 * <pre>
 * &lt;@article list=true sid=1 size=10&gt;...&lt;/@article&gt;
 * &lt;@article page=true scode='news'&gt;...&lt;/@article&gt;
 * &lt;@article code='about-us' as='art'&gt;${art.title}&lt;/@article&gt;
 * </pre>
 */
@Component("article")
public class ArticleDirective extends BaseDirective {

    private final ArticleService articleService;
    private final SectionService sectionService;
    private final CmsProperties properties;

    public ArticleDirective(ArticleService articleService,
                            SectionService sectionService,
                            CmsProperties properties) {
        this.articleService = articleService;
        this.sectionService = sectionService;
        this.properties = properties;
    }

    @Override
    protected Object buildData(TagContext context) {
        Long sectionId = context.sid();
        if (sectionId == null && StringUtils.hasText(context.scode())) {
            sectionId = sectionService.findByCode(context.scode())
                    .map(Section::getId)
                    .orElse(-1L);
        }
        ArticleService.Query query = new ArticleService.Query(
                context.id(),
                null,
                context.code(),
                sectionId,
                1,
                context.show() ? 1 : 0,
                toStatus(context.top()),
                context.excludeId());

        if (context.list()) {
            return articleService.list(query, listMax(context));
        }
        if (context.page()) {
            return articleService.page(query, current(context), size(context)).records();
        }
        return articleService.one(query);
    }

    private int listMax(TagContext context) {
        return context.size() > 0
                ? Math.min(context.size(), properties.listMax())
                : properties.listMax();
    }

    private long current(TagContext context) {
        return context.current() < 1 ? 1 : context.current();
    }

    private long size(TagContext context) {
        return context.size() < 1 ? properties.pageSize() : Math.min(context.size(), properties.listMax());
    }

    private Integer toStatus(Boolean value) {
        return value == null ? null : (value ? 1 : 0);
    }
}
