package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.category.entity.Category;
import cn.org.faster.cms.api.category.service.CategoryService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("Articles")
public class ArticlesDirective extends BaseDirective {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        //查询文章
        Article query = new Article();

        String categoryCode = MapUtil.getStr(map, "categoryCode");
        if(!StringUtils.isEmpty(categoryCode)){
            //根据categoryCode获取categoryId
            Category categoryQuery = new Category();
            categoryQuery.setCode(MapUtil.getStr(map, "categoryCode"));
            Category category = categoryService.query(categoryQuery);
            if(category!=null){
                query.setCategoryId(category.getId());
            } else {
                query.setCategoryId(-1L);
            }
        }

        query.setPublishStatus(1);

        Integer size = MapUtil.getInt(map, "size");
        if(size!=null){
            query.setSize(size);
        }
        Integer current = MapUtil.getInt(map, "current");
        if(current!=null){
            query.setCurrent(current);
        }

        List<Article> articleList = articleService.list(query).getRecords();

        environment.setVariable(variableName(map), super.buildModel(articleList));

        templateDirectiveBody.render(environment.getOut());
    }
}
