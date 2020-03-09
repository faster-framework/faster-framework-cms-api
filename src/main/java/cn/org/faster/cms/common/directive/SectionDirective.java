package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("Section")
public class SectionDirective extends BaseDirective {
    @Autowired
    private SectionService sectionService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        Section sectionQuery = new Section();
        sectionQuery.setCode(MapUtil.getStr(map, "code"));
        sectionQuery.setId(MapUtil.getLong(map, "id"));

        Section section = sectionService.query(sectionQuery);

        environment.setVariable(variableName(map), super.buildModel(section == null ? new Section() : section));

        templateDirectiveBody.render(environment.getOut());
    }
}
