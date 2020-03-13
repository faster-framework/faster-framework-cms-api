package cn.org.faster.cms.common.directive;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.utils.MapUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("ArticlePage")
public class ArticlePageDirective extends BaseDirective {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SectionService sectionService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        //查询文章
        Article articleQuery = new Article();

        String sectionCode = MapUtils.getStr(map, "sCode");
        if (!StringUtils.isEmpty(sectionCode)) {
            //根据sectionCode获取sectionId
            Section sectionQuery = new Section();
            sectionQuery.setCode(MapUtils.getStr(map, "sCode"));
            Section section = sectionService.query(sectionQuery);
            if (section != null) {
                articleQuery.setSectionId(section.getId());
            } else {
                articleQuery.setSectionId(-1L);
            }
        }
        articleQuery.setShowStatus(MapUtils.getIntOrDefault(map, "show", 1));
        articleQuery.setTopStatus(MapUtils.getInt(map, "top"));

        Integer size = MapUtils.getInt(map, "size");
        if (size != null) {
            articleQuery.setSize(size);
        }
        Integer current = MapUtils.getInt(map, "current");
        if (current != null) {
            articleQuery.setCurrent(current);
        }

        IPage<Article> articleIPage = articleService.list(articleQuery);

        environment.setVariable(variableName(map), super.buildModel(articleIPage));

        templateDirectiveBody.render(environment.getOut());
    }
}
