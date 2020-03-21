package cn.org.faster.cms.view.article;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import cn.org.faster.cms.common.properties.CmsProperties;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author zhangbowen
 * @since 2019-12-06
 */
@Controller
@RequestMapping("/a")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private CmsProperties cmsProperties;

    /**
     * 详情页
     *
     * @return 模板地址
     */
    @GetMapping("/{id}")
    public String info(@PathVariable String id, Model model) {
        Article article = articleService.getById(id);
        if (article == null || article.getPublishStatus() == 0) {
            BasicErrorCode.ERROR.throwException();
        }
        Section section = sectionService.getById(article.getSectionId());
        if (section == null || StringUtils.isEmpty(section.getArticleTemplatePath())) {
            BasicErrorCode.ERROR.throwException();
        }
        Map<String, Object> params = articleService.renderTemplate(article, section);
        model.addAttribute(cmsProperties.getContextPrefix(), params);
        return section.getTemplatePath();
    }
}
