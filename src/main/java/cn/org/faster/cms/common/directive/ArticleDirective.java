package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
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
@Component("Article")
public class ArticleDirective extends BaseDirective {
    @Autowired
    private ArticleService articleService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        Article articleQuery = new Article();
        articleQuery.setCode(MapUtil.getStr(map, "code"));
        articleQuery.setId(MapUtil.getLong(map, "id"));


        Article article = articleService.query(articleQuery);

        environment.setVariable(variableName(map), super.buildModel(article == null ? new Article() : article));

        templateDirectiveBody.render(environment.getOut());
    }
}
