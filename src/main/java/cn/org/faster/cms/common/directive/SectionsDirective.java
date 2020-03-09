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
import java.util.List;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("Sections")
public class SectionsDirective extends BaseDirective {
    @Autowired
    private SectionService sectionService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        Section sectionQuery = new Section();
        sectionQuery.setParentId(MapUtil.getLong(map, "parentId"));
        sectionQuery.setPosition(MapUtil.getStr(map, "position"));
        sectionQuery.setShowStatus(MapUtil.getInt(map, "showStatus"));
        sectionQuery.setSize(MapUtil.getInt(map, "size"));
        sectionQuery.setPublishStatus(1);

        if (sectionQuery.getShowStatus() == null) {
            sectionQuery.setShowStatus(1);
        }

        List<Section> sectionList = sectionQuery.getSize() == null ? sectionService.listAll(sectionQuery) : sectionService.list(sectionQuery).getRecords();

        environment.setVariable(variableName(map), super.buildModel(sectionList));

        templateDirectiveBody.render(environment.getOut());
    }
}
