package cn.org.faster.cms.common.directive;

import cn.org.faster.cms.common.directive.context.TagContext;
import cn.org.faster.cms.common.utils.TemplateMapUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
public abstract class BaseDirective implements TemplateDirectiveModel {
    protected static final DefaultObjectWrapperBuilder wrapperBuilder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);


    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        SimpleHash cmsModel = (SimpleHash) environment.getGlobalVariable("cms");
        boolean list = TemplateMapUtils.getBoolOrDefaultByTemplate(map, "list", false);
        //判断当前请求标签是否为分页，并且当前是否为分页模板，如果是设置上下文中的分页信息，否则设置为-1，由用户传递
        int defaultCurrent = !list && cmsModel.containsKey("current") ? ((SimpleNumber) cmsModel.get("current")).getAsNumber().intValue() : -1;
        int defaultSize = !list && cmsModel.containsKey("size") ? ((SimpleNumber) cmsModel.get("size")).getAsNumber().intValue() : -1;
        boolean page = TemplateMapUtils.getBoolOrDefaultByTemplate(map, "page", false);
        int size = TemplateMapUtils.getIntOrDefault(map, "size", defaultSize);
        int current = TemplateMapUtils.getIntOrDefault(map, "current", defaultCurrent);
        Long pid = TemplateMapUtils.getLongOrDefault(map, "pid", null);
        Long id = TemplateMapUtils.getLongOrDefault(map, "id", null);
        boolean show = TemplateMapUtils.getBoolOrDefaultByTemplate(map, "show", true);
        Boolean top = TemplateMapUtils.getBoolOrDefaultByTemplate(map, "top", null);
        String as = TemplateMapUtils.getStrOrDefault(map, "alias", "data");
        String code = TemplateMapUtils.getStrOrDefault(map, "code", null);
        String scode = TemplateMapUtils.getStrOrDefault(map, "scode", null);
        Long sid = TemplateMapUtils.getLongOrDefault(map, "sid", null);
        String type = TemplateMapUtils.getStrOrDefault(map, "type", null);
        String name = TemplateMapUtils.getStrOrDefault(map, "name", null);
        String pcode = TemplateMapUtils.getStrOrDefault(map, "pcode", null);
        Long excludeId = TemplateMapUtils.getLongOrDefault(map, "exclude", null);
        //根据pcode获取pid
        //判断当前页面是否为分页模板，如果为分页模板，自动填充size与current
        TagContext tagContext = TagContext.builder()
                .list(list)
                .page(page)
                .size(size)
                .current(current)
                .pid(pid)
                .id(id)
                .show(show)
                .top(top)
                .as(as)
                .code(code)
                .scode(scode)
                .sid(sid)
                .type(type)
                .name(name)
                .pcode(pcode)
                .excludeId(excludeId)
                .build();

        Object data = buildData(tagContext);
        if (data == null) {
            return;
        }
        TemplateModel templateModel = wrapperBuilder.build().wrap(data);
        environment.setVariable(StringUtils.isEmpty(as) ? "data" : as, templateModel);
        templateDirectiveBody.render(environment.getOut());
    }

    public abstract Object buildData(TagContext tagContext);
}
