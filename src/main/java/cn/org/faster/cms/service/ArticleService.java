package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 文章领域服务。
 */
@Service
@Transactional
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final SectionMapper sectionMapper;
    private final StaticSiteService staticSite;

    public ArticleService(ArticleMapper articleMapper,
                          SectionMapper sectionMapper,
                          StaticSiteService staticSite) {
        this.articleMapper = articleMapper;
        this.sectionMapper = sectionMapper;
        this.staticSite = staticSite;
    }

    /**
     * 查询条件。字段为 {@code null} 表示不参与过滤。
     */
    public record Query(Long id, String title, String code, Long sectionId,
                        Integer publishStatus, Integer showStatus, Integer topStatus,
                        Long excludeId) {

        public static Query site() {
            return new Query(null, null, null, null, 1, null, null, null);
        }

        public Query withSectionId(Long sectionId) {
            return new Query(id, title, code, sectionId, publishStatus, showStatus, topStatus, excludeId);
        }

    }

    @Transactional(readOnly = true)
    public PageResult<Article> page(Query query, long current, long size) {
        Page<Article> page = Page.of(normalizeCurrent(current), normalizeSize(size));
        return PageResult.of(articleMapper.selectPage(page, wrapper(query)));
    }

    @Transactional(readOnly = true)
    public List<Article> list(Query query, int max) {
        Page<Article> page = Page.of(1, Math.max(1, max), false);
        return articleMapper.selectPage(page, wrapper(query)).getRecords();
    }

    @Transactional(readOnly = true)
    public Article one(Query query) {
        List<Article> list = articleMapper.selectList(wrapper(query).last("limit 1"));
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional(readOnly = true)
    public Article getRequired(Long id) {
        Article article = id == null ? null : articleMapper.selectById(id);
        if (article == null) {
            throw BizException.of(ErrorCode.ARTICLE_NOT_FOUND);
        }
        return article;
    }

    @Transactional(readOnly = true)
    public Optional<Article> findByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return Optional.empty();
        }
        return Optional.ofNullable(articleMapper.selectOne(
                Wrappers.<Article>lambdaQuery().eq(Article::getCode, code).last("limit 1")));
    }

    @Transactional(readOnly = true)
    public long countBySection(Long sectionId) {
        return articleMapper.selectCount(
                Wrappers.<Article>lambdaQuery().eq(Article::getSectionId, sectionId));
    }

    public Article create(Article article) {
        requireSection(article.getSectionId());
        if (StringUtils.hasText(article.getCode()) && codeExists(article.getCode(), null)) {
            throw BizException.of(ErrorCode.ARTICLE_CODE_EXISTS);
        }
        applyDefaults(article);
        if (article.published() && article.getPublishDate() == null) {
            article.setPublishDate(LocalDateTime.now());
        }
        try {
            articleMapper.insert(article);
        } catch (DuplicateKeyException ex) {
            throw BizException.of(ErrorCode.ARTICLE_CODE_EXISTS);
        }
        return article;
    }

    public Article update(Long id, Article patch) {
        Article existing = getRequired(id);
        if (StringUtils.hasText(patch.getCode())
                && !Objects.equals(patch.getCode(), existing.getCode())
                && codeExists(patch.getCode(), id)) {
            throw BizException.of(ErrorCode.ARTICLE_CODE_EXISTS);
        }
        if (patch.getSectionId() != null) {
            requireSection(patch.getSectionId());
        }
        // 仅在首次发布时写入发布时间，避免编辑覆盖历史时间
        if (Integer.valueOf(1).equals(patch.getPublishStatus())
                && !existing.published()
                && patch.getPublishDate() == null) {
            patch.setPublishDate(LocalDateTime.now());
        }
        patch.setId(id);
        articleMapper.updateById(patch);

        if (Integer.valueOf(0).equals(patch.getPublishStatus())
                || Integer.valueOf(0).equals(patch.getShowStatus())) {
            staticSite.deleteArticlePage(id);
        }
        return articleMapper.selectById(id);
    }

    public void delete(Long id) {
        Article existing = getRequired(id);
        articleMapper.deleteById(id);
        staticSite.deleteArticlePage(id);
        if (existing.getSectionId() != null) {
            // 文章删除后栏目分页页会少一页，主动失效，等待重新生成
            Section section = sectionMapper.selectById(existing.getSectionId());
            if (section != null) {
                staticSite.deleteSectionPages(section.getCode());
            }
        }
    }

    public void deleteAll(Collection<Long> ids) {
        ids.stream().filter(Objects::nonNull).distinct().forEach(this::delete);
    }

    private void requireSection(Long sectionId) {
        if (sectionId == null) {
            throw BizException.of(ErrorCode.SECTION_NOT_FOUND);
        }
        if (sectionMapper.selectById(sectionId) == null) {
            throw BizException.of(ErrorCode.SECTION_NOT_FOUND);
        }
    }

    private void applyDefaults(Article article) {
        if (article.getPublishStatus() == null) {
            article.setPublishStatus(0);
        }
        if (article.getShowStatus() == null) {
            article.setShowStatus(1);
        }
        if (article.getTopStatus() == null) {
            article.setTopStatus(0);
        }
    }

    private boolean codeExists(String code, Long excludeId) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery().eq(Article::getCode, code);
        if (excludeId != null) {
            wrapper.ne(Article::getId, excludeId);
        }
        return articleMapper.selectCount(wrapper) > 0;
    }

    private LambdaQueryWrapper<Article> wrapper(Query q) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery();
        if (q.id() != null) {
            wrapper.eq(Article::getId, q.id());
        }
        if (StringUtils.hasText(q.title())) {
            wrapper.like(Article::getTitle, q.title());
        }
        if (StringUtils.hasText(q.code())) {
            wrapper.eq(Article::getCode, q.code());
        }
        if (q.sectionId() != null) {
            wrapper.eq(Article::getSectionId, q.sectionId());
        }
        if (q.publishStatus() != null) {
            wrapper.eq(Article::getPublishStatus, q.publishStatus());
        }
        if (q.showStatus() != null) {
            wrapper.eq(Article::getShowStatus, q.showStatus());
        }
        if (q.topStatus() != null) {
            wrapper.eq(Article::getTopStatus, q.topStatus());
        }
        if (q.excludeId() != null) {
            wrapper.ne(Article::getId, q.excludeId());
        }
        return wrapper.orderByDesc(Article::getTopStatus)
                .orderByAsc(Article::getSort)
                .orderByDesc(Article::getPublishDate)
                .orderByDesc(Article::getId);
    }

    private long normalizeCurrent(long current) {
        return current < 1 ? 1 : current;
    }

    private long normalizeSize(long size) {
        return size < 1 ? 20 : Math.min(size, 200);
    }
}
