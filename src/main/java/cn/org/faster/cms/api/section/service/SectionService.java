package cn.org.faster.cms.api.section.service;

import cn.org.faster.cms.api.article.entity.Article;
import cn.org.faster.cms.api.article.service.ArticleService;
import cn.org.faster.cms.api.section.entity.Section;
import cn.org.faster.cms.api.section.mapper.SectionMapper;
import cn.org.faster.cms.common.properties.CmsProperties;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author faster-builder
 * 栏目 Service
 */
@Service
@Transactional
public class SectionService extends ServiceImpl<SectionMapper, Section> {
    @Autowired
    private CmsProperties cmsProperties;
    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询
     *
     * @param section 请求参数
     * @return 栏目分页列表
     */
    public IPage<Section> page(Section section) {
        LambdaQueryWrapper<Section> queryWrapper = new LambdaQueryWrapper<>();
        if (section.getId() != null) {
            queryWrapper.eq(Section::getId, section.getId());
        }
        if (!StringUtils.isEmpty(section.getName())) {
            queryWrapper.eq(Section::getName, section.getName());
        }
        if (!StringUtils.isEmpty(section.getCode())) {
            queryWrapper.eq(Section::getCode, section.getCode());
        }
        if (!StringUtils.isEmpty(section.getDescription())) {
            queryWrapper.eq(Section::getDescription, section.getDescription());
        }
        if (!StringUtils.isEmpty(section.getImg())) {
            queryWrapper.eq(Section::getImg, section.getImg());
        }
        if (section.getParentId() != null) {
            queryWrapper.eq(Section::getParentId, section.getParentId());
        }
        if (!StringUtils.isEmpty(section.getParentIds())) {
            queryWrapper.eq(Section::getParentIds, section.getParentIds());
        }
        if (section.getTemplateType() != null) {
            queryWrapper.eq(Section::getTemplateType, section.getTemplateType());
        }
        if (!StringUtils.isEmpty(section.getLink())) {
            queryWrapper.eq(Section::getLink, section.getLink());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (section.getTopStatus() != null) {
            queryWrapper.eq(Section::getTopStatus, section.getTopStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getArticleTemplatePath())) {
            queryWrapper.eq(Section::getArticleTemplatePath, section.getArticleTemplatePath());
        }
        if (section.getCreateBy() != null) {
            queryWrapper.eq(Section::getCreateBy, section.getCreateBy());
        }
        if (section.getUpdateBy() != null) {
            queryWrapper.eq(Section::getUpdateBy, section.getUpdateBy());
        }
        if (section.getCreateDate() != null) {
            queryWrapper.eq(Section::getCreateDate, section.getCreateDate());
        }
        if (section.getUpdateDate() != null) {
            queryWrapper.eq(Section::getUpdateDate, section.getUpdateDate());
        }
        if (section.getSort() != null) {
            queryWrapper.eq(Section::getSort, section.getSort());
        }
        if (!StringUtils.isEmpty(section.getRemark())) {
            queryWrapper.eq(Section::getRemark, section.getRemark());
        }
        if (section.getDeleted() != null) {
            queryWrapper.eq(Section::getDeleted, section.getDeleted());
        }
        if (section.getExcludeId() != null) {
            queryWrapper.ne(Section::getId, section.getExcludeId());
        }
        queryWrapper.orderByDesc(Section::getTopStatus).orderByAsc(Section::getSort);
        return super.baseMapper.selectPage(section.toPage(), queryWrapper);
    }

    /**
     * 根据条件查询详情
     *
     * @param section 请求参数
     * @return 栏目详情
     */
    public Section query(Section section) {
        LambdaQueryWrapper<Section> queryWrapper = new LambdaQueryWrapper<>();
        if (section.getId() != null) {
            queryWrapper.eq(Section::getId, section.getId());
        }
        if (!StringUtils.isEmpty(section.getName())) {
            queryWrapper.eq(Section::getName, section.getName());
        }
        if (!StringUtils.isEmpty(section.getCode())) {
            queryWrapper.eq(Section::getCode, section.getCode());
        }
        if (!StringUtils.isEmpty(section.getDescription())) {
            queryWrapper.eq(Section::getDescription, section.getDescription());
        }
        if (!StringUtils.isEmpty(section.getImg())) {
            queryWrapper.eq(Section::getImg, section.getImg());
        }
        if (section.getParentId() != null) {
            queryWrapper.eq(Section::getParentId, section.getParentId());
        }
        if (!StringUtils.isEmpty(section.getParentIds())) {
            queryWrapper.eq(Section::getParentIds, section.getParentIds());
        }
        if (section.getTemplateType() != null) {
            queryWrapper.eq(Section::getTemplateType, section.getTemplateType());
        }
        if (!StringUtils.isEmpty(section.getLink())) {
            queryWrapper.eq(Section::getLink, section.getLink());
        }
        if (section.getPublishStatus() != null) {
            queryWrapper.eq(Section::getPublishStatus, section.getPublishStatus());
        }
        if (section.getShowStatus() != null) {
            queryWrapper.eq(Section::getShowStatus, section.getShowStatus());
        }
        if (section.getTopStatus() != null) {
            queryWrapper.eq(Section::getTopStatus, section.getTopStatus());
        }
        if (!StringUtils.isEmpty(section.getTemplatePath())) {
            queryWrapper.eq(Section::getTemplatePath, section.getTemplatePath());
        }
        if (!StringUtils.isEmpty(section.getArticleTemplatePath())) {
            queryWrapper.eq(Section::getArticleTemplatePath, section.getArticleTemplatePath());
        }
        if (section.getCreateBy() != null) {
            queryWrapper.eq(Section::getCreateBy, section.getCreateBy());
        }
        if (section.getUpdateBy() != null) {
            queryWrapper.eq(Section::getUpdateBy, section.getUpdateBy());
        }
        if (section.getCreateDate() != null) {
            queryWrapper.eq(Section::getCreateDate, section.getCreateDate());
        }
        if (section.getUpdateDate() != null) {
            queryWrapper.eq(Section::getUpdateDate, section.getUpdateDate());
        }
        if (section.getSort() != null) {
            queryWrapper.eq(Section::getSort, section.getSort());
        }
        if (!StringUtils.isEmpty(section.getRemark())) {
            queryWrapper.eq(Section::getRemark, section.getRemark());
        }
        if (section.getDeleted() != null) {
            queryWrapper.eq(Section::getDeleted, section.getDeleted());
        }
        if (section.getExcludeId() != null) {
            queryWrapper.ne(Section::getId, section.getExcludeId());
        }
        queryWrapper.last("limit 1");
        return super.getOne(queryWrapper);
    }

    /**
     * 根据主键id查询详情
     *
     * @param id 栏目id
     * @return 栏目详情
     */
    public Section queryById(Long id) {
        return super.getById(id);
    }

    /**
     * 添加栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity add(Section section) {
        section.preInsert();
        super.save(section);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改栏目
     *
     * @param section 实体
     * @return ResponseEntity
     */
    public ResponseEntity update(Section section) {
        section.preUpdate();
        super.updateById(section);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除栏目
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseEntity delete(Long id) {
        super.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 渲染模板页面
     *
     * @return
     */
    public Map<String, Object> renderTemplate(Section section, Integer current) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("s", section);
        dataModel.put("p", this.getById(section.getParentId()));
        List<String> parentIdList = Arrays.stream(section.getParentIds().split(","))
                .map(item -> item.replace("[", "").replace("]", ""))
                .collect(Collectors.toList());
        dataModel.put("plist", this.listByIds(parentIdList));
        //判断当前是否为分页
        if (section.getTemplateType() == 2) {
            //如果为分页
            current = Optional.ofNullable(current).orElse(1);
            dataModel.putAll(renderPageTemplate(section, current));
        }
        return dataModel;
    }

    /**
     * 渲染分页模板页面
     *
     * @return
     */
    public Map<String, Object> renderPageTemplate(Section section, Integer current) {
        int pageSize = cmsProperties.getPageSize();
        Article query = new Article();
        query.setPublishStatus(1);
        query.setCurrent(1);
        query.setSize(pageSize);
        query.setSectionId(section.getId());
        //获取共多少页
        IPage<Article> page = articleService.page(query);
        long totalPages = page.getPages();

        Map<String, Object> pageModel = new HashMap<>();
        pageModel.put("current", current);
        pageModel.put("size", pageSize);
        pageModel.put("pages", totalPages);
        pageModel.put("total", page.getTotal());
        pageModel.put("hasn", current < totalPages);
        pageModel.put("hasp", current > 1);
        pageModel.put("nnum", current + 1);
        pageModel.put("pnum", current - 1);
        return pageModel;
    }

}