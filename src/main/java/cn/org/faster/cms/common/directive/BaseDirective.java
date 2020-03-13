package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import freemarker.template.*;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
public abstract class BaseDirective implements TemplateDirectiveModel {
    protected static final DefaultObjectWrapperBuilder wrapperBuilder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);

    protected TemplateModel buildModel(Object object) throws TemplateModelException {
        TemplateModel templateModel = wrapperBuilder.build().wrap(object);
        return templateModel;
    }

    protected String variableName(Map map) {
        String variableName = MapUtil.getStr(map, "as");
        return StringUtils.isEmpty(variableName) ? "data" : variableName;
    }
}
