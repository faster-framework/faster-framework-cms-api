package cn.org.faster.cms.admin.article.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.org.faster.cms.admin.article.service.ArticleService;
import cn.org.faster.cms.admin.article.model.request.ArticleAddRequest;
import cn.org.faster.cms.admin.article.model.request.ArticleQueryRequest;
import cn.org.faster.cms.admin.article.model.request.ArticleListRequest;
import cn.org.faster.cms.admin.article.model.request.ArticleUpdateRequest;
import cn.org.faster.cms.admin.article.entity.Article;

/**
 * @author faster-builder
 * 文章 Controller
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 文章分页列表
     *
     * @param request 文章实体
     * @return ResponseEntity
     */
    @GetMapping
    @RequiresPermissions("article:list")
    public ResponseEntity list(ArticleListRequest request) {
        Article article = new Article();
        BeanUtils.copyProperties(request, article);
        return ResponseEntity.ok(articleService.list(article));
    }

    /**
     * 文章根据id查询详情
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    @RequiresPermissions("article:info")
    public ResponseEntity queryById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    /**
     * 文章根据条件查询详情
     *
     * @return ResponseEntity
     */
    @GetMapping("/query")
    @RequiresPermissions("article:info")
    public ResponseEntity query(ArticleQueryRequest request) {
        Article article = new Article();
        BeanUtils.copyProperties(request, article);
        return ResponseEntity.ok(articleService.query(article));
    }

    /**
     * 新增文章
     *
     * @param request 请求参数
     * @return ResponseEntity
     */
    @PostMapping
    @RequiresPermissions("article:add")
    public ResponseEntity add(@Validated @RequestBody ArticleAddRequest request) {
        Article article = new Article();
        BeanUtils.copyProperties(request, article);
        return articleService.add(article);
    }

    /**
     * 更新文章
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    @RequiresPermissions("article:modify")
    public ResponseEntity update(@PathVariable Long id,@RequestBody ArticleUpdateRequest request) {
        Article article = new Article();
        BeanUtils.copyProperties(request, article);
        article.setId(id);
        return articleService.update(article);
    }

    /**
     * 删除文章
     *
     * @param ids 主键id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("article:delete")
    public ResponseEntity delete(@RequestBody List<Long>  ids) {
        ids.forEach(item -> {
            if (item == 0L) {
                return;
            }
            articleService.delete(item);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}