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


    private void generateArticle(List<String> sectionParentPathList, List<String> sectionParentPathNameList, Section section) throws IOException, TemplateException {
        List<String> parentPathList = new ArrayList<>(sectionParentPathList);
        parentPathList.add(section.getCode());

        List<String> parentPathNameList = new ArrayList<>(sectionParentPathNameList);
        parentPathNameList.add(section.getName());

        String parentPath = String.join("/", parentPathList);
        //生成文章
        Article query = new Article();
        query.setPublishStatus(1);
        query.setSectionId(section.getId());
        List<Article> articleList = articleService.listAll(query);
        for (Article article : articleList) {
            Template template = configuration.getTemplate(section.getContentTemplatePath());
            Map<String, Object> param = new HashMap<>();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("id", article.getId());
            dataModel.put("parentPath", parentPathList);
            dataModel.put("parentPathName", parentPathNameList);
            param.put("page", dataModel);
            String fileName = super.cmsProperties.getSiteDir() + "/a/" + article.getId() + ".html";
            CmsUtils.generateHtml(template, fileName, param);
        }
    }

    /**
     * 生成栏目
     *
     * @param parentPathList 父路径
     * @param parentNameList 父路径中文
     * @param section        栏目
     */
    private void generateSection(List<String> parentPathList, List<String> parentNameList, Section section) throws IOException, TemplateException {
        String parentPath = String.join("/", parentPathList);
        parentPath = "/s/" + (StringUtils.isEmpty(parentPath) ? "" : parentPath + "/");
        Template template = configuration.getTemplate(section.getTemplatePath());
        String fullPath = parentPath + section.getCode();

        //生成栏目的分页
        //查询栏目下的文章共多少页
        Article query = new Article();
        query.setPublishStatus(1);
        query.setCurrent(1);
        query.setSize(cmsProperties.getPageSize());
        query.setSectionId(section.getId());
        IPage<Article> page = articleService.list(query);
        long totalPages = page.getPages();

        if (totalPages == 0) {
            String fileName = super.cmsProperties.getSiteDir()+ "/s/" + section.getCode() + ".html";
            Map<String, Object> param = new HashMap<>();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("sCode", section.getCode());
            dataModel.put("parentPath", parentPath);
            param.put("page", dataModel);
            CmsUtils.generateHtml(template, fileName, param);
        } else {
            //生成分页页面
            for (int i = 1; i <= totalPages; i++) {
                String fileName = super.cmsProperties.getSiteDir() +  "/s/" + section.getCode() + "/" + i + ".html";
                Map<String, Object> param = new HashMap<>();
                Map<String, Object> dataModel = new HashMap<>();
                dataModel.put("sCode", section.getCode());
                dataModel.put("parentPath", parentPath);
                dataModel.put("current", i);
                dataModel.put("size", cmsProperties.getPageSize());
                param.put("page", dataModel);
                CmsUtils.generateHtml(template, fileName, param);
                //如果为第一页，生成一个以当前code为名称的页面。
                if (i == 1) {
                    fileName = super.cmsProperties.getSiteDir() + "/s/" + section.getCode() + ".html";
                    CmsUtils.generateHtml(template, fileName, param);
                }
                generateArticle(parentPathList, parentNameList, section);
            }
        }
    }

    /**
     * 递归处理
     *
     * @param parentPathList 父级编码列表
     * @param parentNameList 父级名称列表
     * @param sectionList    栏目列表
     */
    private void processRecursive(List<String> parentPathList, List<String> parentNameList, List<Section> sectionList) throws IOException, TemplateException {
        //过滤掉空模板
        sectionList = sectionList.stream()
                .filter(item -> !StringUtils.isEmpty(item.getTemplatePath()))
                .collect(Collectors.toList());
        for (Section section : sectionList) {
            generateSection(parentPathList, parentNameList, section);
            //如果包含子类，继续生成
            if (!CollectionUtils.isEmpty(section.getChildren())) {
                //转化为子类列表
                List<Section> childrenList = section.getChildren().stream().map(item -> (Section) item).collect(Collectors.toList());

                //添加当前code，重置子类父列表
                List<String> childrenParentPathList = new ArrayList<>(parentPathList);
                childrenParentPathList.add(section.getCode());
                //添加当前name，重置子类父列表
                List<String> childrenParentNameList = new ArrayList<>(parentPathList);
                childrenParentNameList.add(section.getName());

                processRecursive(childrenParentPathList, childrenParentNameList, childrenList);
            }
        }

    }

    @Override
    public void execute() throws IOException, TemplateException {
        List<Section> sectionList = sectionService.treeList().stream().map(item -> (Section) item).collect(Collectors.toList());
        processRecursive(new ArrayList<>(), new ArrayList<>(), sectionList);
    }
}
