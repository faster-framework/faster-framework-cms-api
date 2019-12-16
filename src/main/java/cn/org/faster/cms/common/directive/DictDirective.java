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
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("Dict")
public class DictDirective extends BaseDirective {
    @Autowired
    private DictService dictService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        SysDict query = new SysDict();
        query.setType(MapUtil.getStr(map, "type"));
        query.setName(MapUtil.getStr(map, "name"));

        SysDict sysDict = dictService.query(query);


        environment.setVariable(variableName(map), super.buildModel(sysDict == null ? new SysDict() : sysDict));

        templateDirectiveBody.render(environment.getOut());
    }
}
