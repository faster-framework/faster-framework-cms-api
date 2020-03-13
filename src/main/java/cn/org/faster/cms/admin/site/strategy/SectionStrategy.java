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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
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


    /**
     * 生成文章
     *
     * @param sectionParentList 父级栏目列表
     * @param section           父级栏目
     * @throws IOException       io
     * @throws TemplateException template
     */
    private void generateArticle(List<Section> sectionParentList, Section section) throws IOException, TemplateException {
        List<Section> parentList = new ArrayList<>(sectionParentList);
        sectionParentList.add(section);
        //生成文章
        Article query = new Article();
        query.setPublishStatus(1);
        query.setSectionId(section.getId());
        List<Article> articleList = articleService.listAll(query);
        for (Article article : articleList) {
            Template template = configuration.getTemplate(section.getArticleTemplatePath());
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("id", article.getId());
            dataModel.put("parent", section);
            dataModel.put("parentList", parentList);
            String fileName = super.cmsProperties.getSiteDir() + "/a/" + article.getId() + ".html";
            CmsUtils.generateHtml(template, fileName, dataModel);
        }
    }

    /**
     * 生成栏目
     *
     * @param parentList 父栏目列表
     * @param section    栏目
     */
    private void generateSection(List<Section> parentList, Section section) throws IOException, TemplateException {
        Template template = configuration.getTemplate(section.getTemplatePath());
        //生成栏目的分页
        //查询栏目下的文章共多少页
        Article query = new Article();
        query.setPublishStatus(1);
        query.setCurrent(1);
        query.setSize(cmsProperties.getPageSize());
        query.setSectionId(section.getId());
        IPage<Article> page = articleService.list(query);
        long totalPages = page.getPages();

        Section parentSection = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);

        if (totalPages == 0) {
            String fileName = super.cmsProperties.getSiteDir() + "/s/" + section.getCode() + ".html";
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("sCode", section.getCode());
            dataModel.put("parent", section);
            dataModel.put("parentList", parentSection);
            CmsUtils.generateHtml(template, fileName, dataModel);
        } else {
            //生成分页页面
            for (int i = 1; i <= totalPages; i++) {
                String fileName = super.cmsProperties.getSiteDir() + "/s/" + section.getCode() + "/" + i + ".html";
                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("sCode", section.getCode());
                dataModel.put("parent", parentSection);
                dataModel.put("parentList", parentList);
                dataModel.put("current", i);
                dataModel.put("size", cmsProperties.getPageSize());
                CmsUtils.generateHtml(template, fileName, dataModel);
                //如果为第一页，生成一个以当前code为名称的页面。
                if (i == 1) {
                    fileName = super.cmsProperties.getSiteDir() + "/s/" + section.getCode() + ".html";
                    CmsUtils.generateHtml(template, fileName, dataModel);
                }
                generateArticle(parentList, section);
            }
        }
    }

    /**
     * 递归处理
     *
     * @param parentList  父级栏目列表
     * @param sectionList 栏目列表
     */
    private void processRecursive(List<Section> parentList, List<Section> sectionList) throws IOException, TemplateException {
        //过滤掉空模板
        sectionList = sectionList.stream()
                .filter(item -> !StringUtils.isEmpty(item.getTemplatePath()))
                .collect(Collectors.toList());
        for (Section section : sectionList) {
            generateSection(parentList, section);
            //如果包含子类，继续生成
            if (!CollectionUtils.isEmpty(section.getChildren())) {
                //转化为子类列表
                List<Section> childrenList = section.getChildren().stream().map(item -> (Section) item).collect(Collectors.toList());
                //添加当前section，重置子类父列表
                List<Section> childrenParentList = new ArrayList<>(parentList);
                childrenParentList.add(section);
                //递归生成子类
                processRecursive(childrenParentList, childrenList);
            }
        }

    }

    @Override
    public void execute() throws IOException, TemplateException {
        List<Section> sectionList = sectionService.treeList().stream().map(item -> (Section) item).collect(Collectors.toList());
        processRecursive(new ArrayList<>(), sectionList);
    }
}
