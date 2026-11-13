package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.common.TreeUtils;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.mapper.ArticleMapper;
import cn.org.faster.cms.mapper.SectionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 栏目领域服务。
 */
@Service
@Transactional
public class SectionService {

    private final SectionMapper sectionMapper;
    private final ArticleMapper articleMapper;
    private final StaticSiteService staticSite;

    public SectionService(SectionMapper sectionMapper,
                          ArticleMapper articleMapper,
                          StaticSiteService staticSite) {
        this.sectionMapper = sectionMapper;
        this.articleMapper = articleMapper;
        this.staticSite = staticSite;
    }

    /**
     * 查询条件，字段为 {@code null} 表示不参与过滤。
     */
    public record Query(Long id, Long parentId, String code,
                        Integer publishStatus, Integer showStatus, Integer topStatus,
                        Long excludeId) {

        public static Query published() {
            return new Query(null, null, null, 1, null, null, null);
        }
    }

    @Transactional(readOnly = true)
    public List<Section> tree() {
        List<Section> flat = listAll();
        return TreeUtils.build(flat, Section::getId, Section::getParentId, Section::setChildren);
    }

    @Transactional(readOnly = true)
    public List<Section> listAll() {
        return sectionMapper.selectList(Wrappers.<Section>lambdaQuery()
                .orderByAsc(Section::getParentIds)
                .orderByAsc(Section::getSort)
                .orderByAsc(Section::getId));
    }

    @Transactional(readOnly = true)
    public PageResult<Section> page(Query query, long current, long size) {
        Page<Section> page = Page.of(current < 1 ? 1 : current, size < 1 ? 20 : size);
        return PageResult.of(sectionMapper.selectPage(page, wrapper(query)));
    }

    @Transactional(readOnly = true)
    public List<Section> list(Query query, int max) {
        return sectionMapper.selectList(wrapper(query).last("limit " + Math.max(1, max)));
    }

    @Transactional(readOnly = true)
    public Section one(Query query) {
        List<Section> list = sectionMapper.selectList(wrapper(query).last("limit 1"));
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional(readOnly = true)
    public Section getRequired(Long id) {
        Section section = id == null ? null : sectionMapper.selectById(id);
        if (section == null) {
            throw BizException.of(ErrorCode.SECTION_NOT_FOUND);
        }
        return section;
    }

    @Transactional(readOnly = true)
    public Optional<Section> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }
        return Optional.ofNullable(sectionMapper.selectOne(
                Wrappers.<Section>lambdaQuery().eq(Section::getCode, code).last("limit 1")));
    }

    /**
     * 祖先链（由根到直接父级，顺序稳定）。
     */
    @Transactional(readOnly = true)
    public List<Section> ancestors(Section section) {
        List<Long> ids = parseParentIds(section.getParentIds());
        if (ids.isEmpty()) {
            return List.of();
        }
        Map<Long, Section> byId = sectionMapper.selectByIds(ids).stream()
                .collect(Collectors.toMap(Section::getId, Function.identity()));
        return ids.stream().map(byId::get).filter(Objects::nonNull).toList();
    }

    public Section create(Section section) {
        Section parent = resolveParent(section.getParentId());
        if (StringUtils.hasText(section.getCode()) && codeExists(section.getCode(), null)) {
            throw BizException.of(ErrorCode.SECTION_CODE_EXISTS);
        }
        section.setParentId(parent == null ? Section.ROOT_ID : parent.getId());
        section.setParentIds(pathOf(parent));
        if (section.getTemplateType() == null) {
            section.setTemplateType(Section.TEMPLATE_NORMAL);
        }
        if (section.getPublishStatus() == null) {
            section.setPublishStatus(0);
        }
        if (section.getShowStatus() == null) {
            section.setShowStatus(1);
        }
        if (section.getTopStatus() == null) {
            section.setTopStatus(0);
        }
        inheritTemplates(section, parent);
        try {
            sectionMapper.insert(section);
        } catch (DuplicateKeyException ex) {
            throw BizException.of(ErrorCode.SECTION_CODE_EXISTS);
        }
        return section;
    }

    public Section update(Long id, Section patch) {
        Section existing = getRequired(id);
        if (StringUtils.hasText(patch.getCode())
                && !Objects.equals(patch.getCode(), existing.getCode())
                && codeExists(patch.getCode(), id)) {
            throw BizException.of(ErrorCode.SECTION_CODE_EXISTS);
        }
        Long targetParentId = patch.getParentId() == null ? existing.getParentId() : patch.getParentId();
        if (!Objects.equals(targetParentId, existing.getParentId())) {
            moveTo(existing, targetParentId, patch);
        }
        patch.setId(id);
        sectionMapper.updateById(patch);
        return sectionMapper.selectById(id);
    }

    public void delete(Long id) {
        Section existing = getRequired(id);
        long children = sectionMapper.selectCount(
                Wrappers.<Section>lambdaQuery().eq(Section::getParentId, id));
        if (children > 0) {
            throw BizException.of(ErrorCode.SECTION_HAS_CHILDREN);
        }
        long articles = articleMapper.selectCount(
                Wrappers.<Article>lambdaQuery().eq(Article::getSectionId, id));
        if (articles > 0) {
            throw BizException.of(ErrorCode.SECTION_HAS_ARTICLES);
        }
        sectionMapper.deleteById(id);
        staticSite.deleteSectionPages(existing.getCode());
    }

    private void moveTo(Section existing, Long targetParentId, Section patch) {
        Section parent = resolveParent(targetParentId);
        if (parent != null) {
            if (Objects.equals(parent.getId(), existing.getId())) {
                throw BizException.of(ErrorCode.SECTION_PARENT_NOT_FOUND, "栏目不能作为自身的父级");
            }
            if (parent.getParentIds() != null
                    && parent.getParentIds().contains("[" + existing.getId() + "]")) {
                throw BizException.of(ErrorCode.SECTION_PARENT_NOT_FOUND, "不能把栏目移动到自己的子栏目下");
            }
        }
        String oldPrefix = existing.getParentIds() + ",[" + existing.getId() + "]";
        String newPrefix = pathOf(parent) + ",[" + existing.getId() + "]";
        patch.setParentId(parent == null ? Section.ROOT_ID : parent.getId());
        patch.setParentIds(pathOf(parent));
        if (Objects.equals(oldPrefix, newPrefix)) {
            return;
        }
        List<Section> descendants = sectionMapper.selectList(Wrappers.<Section>lambdaQuery()
                .like(Section::getParentIds, "[" + existing.getId() + "]"));
        for (Section child : descendants) {
            String childPath = child.getParentIds();
            if (childPath == null || !childPath.startsWith(oldPrefix)) {
                continue;
            }
            Section update = new Section();
            update.setId(child.getId());
            update.setParentIds(newPrefix + childPath.substring(oldPrefix.length()));
            sectionMapper.updateById(update);
        }
    }

    private Section resolveParent(Long parentId) {
        if (parentId == null || parentId == Section.ROOT_ID) {
            return null;
        }
        Section parent = sectionMapper.selectById(parentId);
        if (parent == null) {
            throw BizException.of(ErrorCode.SECTION_PARENT_NOT_FOUND);
        }
        return parent;
    }

    private void inheritTemplates(Section section, Section parent) {
        if (parent == null) {
            return;
        }
        if (!StringUtils.hasText(section.getTemplatePath())) {
            section.setTemplatePath(parent.getTemplatePath());
        }
        if (!StringUtils.hasText(section.getArticleTemplatePath())) {
            section.setArticleTemplatePath(parent.getArticleTemplatePath());
        }
    }

    /**
     * 祖先路径：根为 {@code [0]}，其余为「父级路径 + [父级id]」。
     */
    private String pathOf(Section parent) {
        if (parent == null || parent.getId() == null || parent.getId() == Section.ROOT_ID) {
            return Section.ROOT_PATH;
        }
        return parent.getParentIds() + ",[" + parent.getId() + "]";
    }

    private List<Long> parseParentIds(String parentIds) {
        if (!StringUtils.hasText(parentIds)) {
            return List.of();
        }
        Collection<Long> ids = Arrays.stream(parentIds.split(","))
                .map(String::trim)
                .map(token -> token.replace("[", "").replace("]", ""))
                .filter(StringUtils::hasText)
                .map(Long::valueOf)
                .filter(id -> id != Section.ROOT_ID)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return List.copyOf(ids);
    }

    private LambdaQueryWrapper<Section> wrapper(Query query) {
        LambdaQueryWrapper<Section> wrapper = Wrappers.lambdaQuery();
        if (query.id() != null) {
            wrapper.eq(Section::getId, query.id());
        }
        if (query.parentId() != null) {
            wrapper.eq(Section::getParentId, query.parentId());
        }
        if (StringUtils.hasText(query.code())) {
            wrapper.eq(Section::getCode, query.code());
        }
        if (query.publishStatus() != null) {
            wrapper.eq(Section::getPublishStatus, query.publishStatus());
        }
        if (query.showStatus() != null) {
            wrapper.eq(Section::getShowStatus, query.showStatus());
        }
        if (query.topStatus() != null) {
            wrapper.eq(Section::getTopStatus, query.topStatus());
        }
        if (query.excludeId() != null) {
            wrapper.ne(Section::getId, query.excludeId());
        }
        return wrapper.orderByDesc(Section::getTopStatus)
                .orderByAsc(Section::getSort)
                .orderByAsc(Section::getId);
    }

    private boolean codeExists(String code, Long excludeId) {
        LambdaQueryWrapper<Section> wrapper = Wrappers.<Section>lambdaQuery().eq(Section::getCode, code);
        if (excludeId != null) {
            wrapper.ne(Section::getId, excludeId);
        }
        return sectionMapper.selectCount(wrapper) > 0;
    }
}
