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
import java.util.List;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("Categories")
public class CategoriesDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        Category categoryQuery = new Category();
        categoryQuery.setParentId(MapUtil.getLong(map, "parentId"));
        categoryQuery.setPosition(MapUtil.getStr(map, "position"));
        categoryQuery.setShowStatus(MapUtil.getInt(map, "showStatus"));
        categoryQuery.setSize(MapUtil.getInt(map, "size"));
        categoryQuery.setPublishStatus(1);

        if (categoryQuery.getShowStatus() == null) {
            categoryQuery.setShowStatus(1);
        }

        List<Category> categoryList = categoryQuery.getSize() == null ? categoryService.listAll(categoryQuery) : categoryService.list(categoryQuery).getRecords();

        environment.setVariable(variableName(map), super.buildModel(categoryList));

        templateDirectiveBody.render(environment.getOut());
    }
}
