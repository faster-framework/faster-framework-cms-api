package cn.org.faster.cms.view.article;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
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

    /**
     * 详情页
     *
     * @return 模板地址
     */
    @GetMapping("/{id}")
    public String info(@PathVariable String id, Model model) {
        Article article = articleService.getById(id);
        Section section = sectionService.getById(article.getSectionId());
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        model.addAttribute("page", params);
        return section.getArticleTemplatePath();
    }
}
