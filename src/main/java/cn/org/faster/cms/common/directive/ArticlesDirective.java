package cn.org.faster.cms.common.directive;

import cn.hutool.core.map.MapUtil;
import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
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
    private SectionService sectionService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        //查询文章
        Article articleQuery = new Article();

        String sectionCode = MapUtil.getStr(map, "sCode");
        if (!StringUtils.isEmpty(sectionCode)) {
            //根据sectionCode获取sectionId
            Section sectionQuery = new Section();
            sectionQuery.setCode(MapUtil.getStr(map, "sCode"));
            Section section = sectionService.query(sectionQuery);
            if (section != null) {
                articleQuery.setSectionId(section.getId());
            } else {
                articleQuery.setSectionId(-1L);
            }
        }

        articleQuery.setShowStatus(MapUtil.getInt(map, "showStatus"));

        if (articleQuery.getShowStatus() == null) {
            articleQuery.setShowStatus(1);
        }

        articleQuery.setTopStatus(MapUtil.getBool(map, "topStatus") ? 1 : 0);
        articleQuery.setSize(MapUtil.getInt(map, "size"));

        List<Article> articleList = articleQuery.getSize() == null ? articleService.listAll(articleQuery) : articleService.list(articleQuery).getRecords();

        environment.setVariable(variableName(map), super.buildModel(articleList));

        templateDirectiveBody.render(environment.getOut());
    }
}
