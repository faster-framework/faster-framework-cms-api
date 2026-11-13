package cn.org.faster.cms.service;

import cn.org.faster.cms.config.CmsProperties;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.mapper.ArticleMapper;
import cn.org.faster.cms.mapper.SectionMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点模板数据模型装配。
 *
 * <p>动态渲染与静态生成共用同一套模型，确保两种输出完全一致。</p>
 */
@Service
public class SiteRenderService {

    private final SectionMapper sectionMapper;
    private final ArticleMapper articleMapper;
    private final SectionService sectionService;
    private final CmsProperties properties;

    public SiteRenderService(SectionMapper sectionMapper,
                             ArticleMapper articleMapper,
                             SectionService sectionService,
                             CmsProperties properties) {
        this.sectionMapper = sectionMapper;
        this.articleMapper = articleMapper;
        this.sectionService = sectionService;
        this.properties = properties;
    }

    public Map<String, Object> indexModel() {
        return Map.of();
    }

    public Map<String, Object> sectionModel(Section section, Integer current) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("s", section);
        model.put("p", section.getParentId() == null
                ? null
                : sectionMapper.selectById(section.getParentId()));
        model.put("plist", sectionService.ancestors(section));

        if (section.isPaged()) {
            long total = articleMapper.selectCount(Wrappers.<Article>lambdaQuery()
                    .eq(Article::getSectionId, section.getId())
                    .eq(Article::getPublishStatus, 1));
            int size = properties.pageSize();
            long pages = total == 0 ? 1 : (total + size - 1) / size;
            int page = current == null || current < 1 ? 1 : current;
            model.put("current", page);
            model.put("size", size);
            model.put("pages", pages);
            model.put("total", total);
            model.put("hasn", page < pages);
            model.put("hasp", page > 1);
            model.put("nnum", page + 1);
            model.put("pnum", page - 1);
        }
        return model;
    }

    public Map<String, Object> articleModel(Article article, Section section) {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("art", article);
        model.put("ps", section);
        List<Section> trail = new java.util.ArrayList<>(sectionService.ancestors(section));
        trail.add(section);
        model.put("pslist", List.copyOf(trail));
        return model;
    }
}
