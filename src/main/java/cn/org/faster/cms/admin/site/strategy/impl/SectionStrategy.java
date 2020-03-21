package cn.org.faster.cms.admin.site.strategy.impl;

import cn.org.faster.cms.admin.article.entity.Article;
import cn.org.faster.cms.admin.article.service.ArticleService;
import cn.org.faster.cms.admin.section.entity.Section;
import cn.org.faster.cms.admin.section.service.SectionService;
import cn.org.faster.cms.admin.site.strategy.BaseStrategy;
import cn.org.faster.cms.common.utils.CmsUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.assertj.core.util.Strings;
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

    public static final String S_VIEW_PREFIX = "/s"; //栏目模板路由前缀
    public static final String ART_VIEW_PREFIX = "/a"; //文章模板路由前缀
    public static final String S_PAGE_TEMPLATE_FORMATTER = S_VIEW_PREFIX + "/%s/%s.html";
    public static final String S_PAGE_NORMAL_FORMATTER = S_VIEW_PREFIX + "/%s.html";
    public static final String ART_FORMATTER = ART_VIEW_PREFIX + "/%s.html";

    @Override
    public void execute() throws IOException, TemplateException {
        List<Section> sectionList = sectionService.treeList().stream().map(item -> (Section) item).collect(Collectors.toList());
        processRecursive(new ArrayList<>(), sectionList);
    }
    /**
     * 递归处理
     *
     * @param parentList  父级栏目列表
     * @param sectionList 栏目列表
     */
    private void processRecursive(List<Section> parentList, List<Section> sectionList) throws IOException, TemplateException {
        //过滤掉空模板
        for (Section section : sectionList) {
            //生成当前栏目
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

    /**
     * 生成栏目
     *
     * @param parentList 父栏目列表
     * @param section    栏目
     */
    private void generateSection(List<Section> parentList, Section section) throws IOException, TemplateException {
        //模板为空，结束
        if(StringUtils.isEmpty(section.getTemplatePath())){
            return;
        }
        Template template = configuration.getTemplate(section.getTemplatePath());
        switch (section.getTemplateType()) {
            case 1://普通
                generateNormalSection(parentList, section, template);
                break;
            case 2://分页
                generatePageSection(parentList, section, template);
                break;
        }
    }
    /**
     * 生成普通栏目页面
     *
     * @param parentList 父栏目列表
     * @param section 父栏目
     * @param template 模板
     * @throws IOException 异常
     * @throws TemplateException 异常
     */
    private void generateNormalSection(List<Section> parentList, Section section, Template template) throws IOException, TemplateException {
        Section parentSection = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
        String fileName = Strings.formatIfArgs(super.cmsProperties.getSiteDir() + S_PAGE_NORMAL_FORMATTER, section.getCode());
        Map<String, Object> context = new HashMap<>();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("s", section);
        dataModel.put("p", parentSection);
        dataModel.put("plist", parentList);
        context.put(super.cmsProperties.getContextPrefix(), dataModel);
        CmsUtils.generateHtml(template, fileName, context);
    }

    /**
     * 生成分页栏目页面
     *
     * @param parentList 父栏目列表
     * @param section 父栏目
     * @param template 模板
     * @throws IOException 异常
     * @throws TemplateException 异常
     */
    private void generatePageSection(List<Section> parentList, Section section, Template template) throws IOException, TemplateException {
        int pageSize = cmsProperties.getPageSize();
        //查询栏目下的文章共多少页
        Article query = new Article();
        query.setPublishStatus(1);
        query.setCurrent(1);
        query.setSize(pageSize);
        query.setSectionId(section.getId());
        IPage<Article> page = articleService.list(query);
        long totalPages = page.getPages();
        Section parentSection = CollectionUtils.isEmpty(parentList) ? null : parentList.get(parentList.size() - 1);
        //生成分页页面
        for (int current = 1; current <= totalPages; current++) {
            String fileName = Strings.formatIfArgs(super.cmsProperties.getSiteDir() + S_PAGE_TEMPLATE_FORMATTER, section.getCode(), current);
            Map<String, Object> context = new HashMap<>();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("s", section);
            dataModel.put("p", parentSection);
            dataModel.put("plist", parentList);
            dataModel.put("current", current);
            dataModel.put("size", pageSize);
            dataModel.put("pages", totalPages);
            dataModel.put("total", page.getTotal());
            dataModel.put("hasn", current < totalPages);
            dataModel.put("hasp", current > 1);
            dataModel.put("nnum", current + 1);
            dataModel.put("pnum", current - 1);
            context.put(super.cmsProperties.getContextPrefix(), dataModel);
            CmsUtils.generateHtml(template, fileName, context);
            //如果为第一页，生成一个以当前code为名称的页面。
            if (current == 1) {
                fileName = Strings.formatIfArgs(super.cmsProperties.getSiteDir() + S_PAGE_NORMAL_FORMATTER, section.getCode());
                CmsUtils.generateHtml(template, fileName, context);
            }
            generateArticle(parentList, section);
        }
    }


    /**
     * 生成文章
     *
     * @param sectionParentList 父级栏目列表
     * @param section           父级栏目
     * @throws IOException       io
     * @throws TemplateException template
     */
    private void generateArticle(List<Section> sectionParentList, Section section) throws IOException, TemplateException {
        if(StringUtils.isEmpty(section.getArticleTemplatePath())){
            return;
        }
        List<Section> parentList = new ArrayList<>(sectionParentList);
        parentList.add(section);
        //生成文章
        Article query = new Article();
        query.setPublishStatus(1);
        query.setSectionId(section.getId());
        List<Article> articleList = articleService.listAll(query);
        for (Article article : articleList) {
            Template template = configuration.getTemplate(section.getArticleTemplatePath());
            Map<String, Object> context = new HashMap<>();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("art", article);
            dataModel.put("ps", section);
            dataModel.put("pslist", parentList);
            context.put(super.cmsProperties.getContextPrefix(), dataModel);
            String fileName = Strings.formatIfArgs(super.cmsProperties.getSiteDir() + ART_FORMATTER, article.getId());
            CmsUtils.generateHtml(template, fileName, context);
        }
    }
}
