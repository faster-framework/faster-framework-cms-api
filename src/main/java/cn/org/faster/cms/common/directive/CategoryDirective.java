package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.category.entity.Category;
import cn.org.faster.cms.api.category.service.CategoryService;
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
@Component("Category")
public class CategoryDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        Category categoryQuery = new Category();
        categoryQuery.setCode(MapUtil.getStr(map, "code"));
        categoryQuery.setId(MapUtil.getLong(map, "id"));

        Category category = categoryService.query(categoryQuery);

        environment.setVariable(variableName(map), super.buildModel(category == null ? new Category() : category));

        templateDirectiveBody.render(environment.getOut());
    }
}
