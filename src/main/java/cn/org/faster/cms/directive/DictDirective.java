package cn.org.faster.cms.directive;

import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.service.DictService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * {@code <@dict ...>} 字典标签。
 *
 * <pre>
 * &lt;@dict list=true type='section-template'&gt;...&lt;/@dict&gt;
 * &lt;@dict name='site-url' as='item'&gt;${item.dictValue}&lt;/@dict&gt;
 * </pre>
 */
@Component("dict")
public class DictDirective extends BaseDirective {

    private final DictService dictService;
    private final CmsProperties properties;

    public DictDirective(DictService dictService, CmsProperties properties) {
        this.dictService = dictService;
        this.properties = properties;
    }

    @Override
    protected Object buildData(TagContext context) {
        DictService.Query query = new DictService.Query(
                context.id(), context.name(), context.type(), null, null);

        if (context.list()) {
            int max = context.size() > 0
                    ? Math.min(context.size(), properties.listMax())
                    : properties.listMax();
            return dictService.list(query, max);
        }
        if (context.page()) {
            long current = context.current() < 1 ? 1 : context.current();
            long size = context.size() < 1 ? properties.pageSize() : context.size();
            return dictService.page(query, current, size).records();
        }
        if (StringUtils.hasText(context.name()) || StringUtils.hasText(context.type())) {
            return dictService.list(query, 1).stream().findFirst().orElse(null);
        }
        return null;
    }
}
