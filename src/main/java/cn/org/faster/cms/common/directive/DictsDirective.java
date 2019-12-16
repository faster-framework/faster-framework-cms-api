package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.dict.service.DictService;
import cn.org.faster.framework.dict.entity.SysDict;
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
@Component("Dicts")
public class DictsDirective extends BaseDirective {
    @Autowired
    private DictService dictService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        SysDict query = new SysDict();
        query.setName(MapUtil.getStr(map, "name"));
        query.setType(MapUtil.getStr(map, "type"));
        query.setSize(MapUtil.getInt(map, "size"));

        List<SysDict> sysDictList = query.getSize() == null ? dictService.listAll(query) : dictService.list(query).getRecords();

        environment.setVariable(variableName(map), super.buildModel(sysDictList));

        templateDirectiveBody.render(environment.getOut());
    }
}
