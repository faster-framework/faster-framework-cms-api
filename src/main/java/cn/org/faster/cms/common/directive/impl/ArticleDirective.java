package cn.org.faster.cms.common.directive.impl;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.directive.BaseDirective;
import cn.org.faster.cms.common.directive.context.TagContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zhangbowen
 * @since 2019-12-04
 */
@Component("article")
public class ArticleDirective extends BaseDirective {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SectionService sectionService;

    @Override
    public Object buildData(TagContext tagContext) {
        Article articleQuery = new Article();
        articleQuery.setCode(tagContext.getCode());
        articleQuery.setId(tagContext.getId());
        articleQuery.setShowStatus(tagContext.isShow() ? 1 : 0);
        articleQuery.setTopStatus(tagContext.getTop() == null ? null : tagContext.getTop() ? 1 : 0);
        articleQuery.setPublishStatus(1);
        articleQuery.setSize(tagContext.getSize());
        articleQuery.setCurrent(tagContext.getCurrent());
        articleQuery.setSectionId(tagContext.getSid());

        //如果是根据栏目code查询，需要栏目id为空
        if (!StringUtils.isEmpty(tagContext.getScode()) && StringUtils.isEmpty(tagContext.getSid())) {
            Section sectionQuery = new Section();
            sectionQuery.setCode(tagContext.getScode());
            Section section = sectionService.query(sectionQuery);
            if (section != null) {
                articleQuery.setSectionId(section.getId());
            }
        }

        if (tagContext.isList()) {
            //如果是列表，默认情况下获取全部
            return articleService.page(articleQuery).getRecords();
        } else if (tagContext.isPage()) {
            return articleService.page(articleQuery).getRecords();
        } else {
            return articleService.query(articleQuery);
        }
    }
}
