package cn.org.faster.cms.directive;

import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.service.SectionService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * {@code <@section ...>} 栏目标签。
 *
 * <pre>
 * &lt;@section list=true pid=0&gt;...&lt;/@section&gt;
 * &lt;@section page=true pcode='news' size=5&gt;...&lt;/@section&gt;
 * &lt;@section code='about' as='s'&gt;${s.name}&lt;/@section&gt;
 * </pre>
 */
@Component("section")
public class SectionDirective extends BaseDirective {

    private final SectionService sectionService;
    private final CmsProperties properties;

    public SectionDirective(SectionService sectionService, CmsProperties properties) {
        this.sectionService = sectionService;
        this.properties = properties;
    }

    @Override
    protected Object buildData(TagContext context) {
        Long parentId = context.pid();
        if (StringUtils.hasText(context.pcode())) {
            parentId = sectionService.findByCode(context.pcode())
                    .map(Section::getId)
                    .orElse(-1L);
        }
        SectionService.Query query = new SectionService.Query(
                context.id(),
                parentId,
                context.code(),
                1,
                context.show() ? 1 : 0,
                toStatus(context.top()),
                context.excludeId());

        if (context.list()) {
            return sectionService.list(query, listMax(context));
        }
        if (context.page()) {
            return sectionService.page(query, current(context), size(context)).records();
        }
        return sectionService.one(query);
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
