package cn.org.faster.cms.admin.site.strategy;

import cn.org.faster.cms.admin.article.entity.Article;
import cn.org.faster.cms.admin.article.service.ArticleService;
import cn.org.faster.cms.admin.section.entity.Section;
import cn.org.faster.cms.admin.section.service.SectionService;
import cn.org.faster.cms.common.utils.CmsUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
@Service
public class SectionStrategy extends BaseStrategy {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private ArticleService articleService;

    @Override
    public void execute() throws IOException, TemplateException {
        List<Section> sectionList = sectionService.list();

        //获取list类型的栏目
        List<Section> listSection = sectionList.stream()
                .filter(item -> !StringUtils.isEmpty(item.getTemplatePath()))
                .collect(Collectors.toList());
        //生成栏目页面
        for (Section item : listSection) {
            //查询共多少页，每页20条
            Article query = new Article();
            query.setPublishStatus(1);
            query.setCurrent(1);
            query.setSize(cmsProperties.getPageSize());
            query.setSectionId(item.getId());
            IPage<Article> page = articleService.list(query);
            long totalPages = page.getPages();
            //生成分页页面
            for (int i = 1; i <= totalPages; i++) {
                Template template = configuration.getTemplate(item.getTemplatePath());
                String fileName = super.cmsProperties.getSiteDir() + "/s/" + item.getCode() + "/" + i + ".html";
                Map<String, Object> param = new HashMap<>();
                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("sectionCode", item.getCode());
                dataModel.put("current", i);
                dataModel.put("size", cmsProperties.getPageSize());
                param.put("page", dataModel);
                CmsUtils.generateHtml(template, fileName, param);
                //如果为第一页，生成一个以当前code为名称的页面。
                if(i == 1){
                    fileName = super.cmsProperties.getSiteDir() + "/s/" + item.getCode() + ".html";
                    CmsUtils.generateHtml(template, fileName, param);
                }
            }
        }
        //生成文章
        for (Section item : sectionList) {
            Article query = new Article();
            query.setPublishStatus(1);
            query.setSectionId(item.getId());
            List<Article> articleList = articleService.listAll(query);
            for (Article article : articleList) {
                Template template = configuration.getTemplate(item.getContentTemplatePath());
                Map<String, Object> param = new HashMap<>();
                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("id", article.getId());
                param.put("page", dataModel);
                String fileName = super.cmsProperties.getSiteDir() + "/art/" + article.getId() +  ".html";
                CmsUtils.generateHtml(template, fileName, param);
            }
        }
    }
}
