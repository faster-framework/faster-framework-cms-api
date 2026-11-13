package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.Article;
import cn.org.faster.cms.service.ArticleService;
import cn.org.faster.cms.web.dto.ArticleDtos;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章管理接口。
 */
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    private final ArticleService articleService;

    public AdminArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('article:list')")
    public PageResult<Article> list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Integer publishStatus,
            @RequestParam(required = false) Integer showStatus,
            @RequestParam(required = false) Integer topStatus,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "20") long size) {
        ArticleService.Query query = new ArticleService.Query(
                null, title, code, sectionId, publishStatus, showStatus, topStatus, null);
        return articleService.page(query, current, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('article:info')")
    public Article detail(@PathVariable Long id) {
        return articleService.getRequired(id);
    }

    /**
     * 条件查询单条，便于按 code 取文章。
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('article:info')")
    public ResponseEntity<Article> query(@RequestParam(required = false) String code,
                                         @RequestParam(required = false) Long sectionId) {
        Article article = articleService.one(new ArticleService.Query(
                null, null, code, sectionId, null, null, null, null));
        return ResponseEntity.ok(article);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('article:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public Article create(@Valid @RequestBody ArticleDtos.Create request) {
        return articleService.create(request.toEntity());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('article:modify')")
    public Article update(@PathVariable Long id, @Valid @RequestBody ArticleDtos.Update request) {
        return articleService.update(id, request.toEntity());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('article:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的文章");
        }
        articleService.deleteAll(ids);
    }
}
