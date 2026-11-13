package cn.org.faster.cms.directive;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * 模板标签基类：统一解析参数、包装数据、渲染标签体。
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

    private static final DefaultObjectWrapper WRAPPER =
            new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_35).build();

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment environment, Map params, TemplateModel[] loopVariables,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        if (body == null) {
            throw new TemplateException("标签必须包含标签体", environment);
        }
        TagContext context = TagContext.parse(params, environment);
        Object data = buildData(context);
        if (data == null) {
            return;
        }
        environment.setVariable(context.alias(), WRAPPER.wrap(data));
        body.render(environment.getOut());
    }

    /**
     * 构建标签数据。
     *
     * @return {@code null} 表示无数据，标签体不渲染
     */
    protected abstract Object buildData(TagContext context);
}
