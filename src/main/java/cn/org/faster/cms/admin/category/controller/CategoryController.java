package cn.org.faster.cms.admin.category.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.org.faster.cms.admin.category.service.CategoryService;
import cn.org.faster.cms.admin.category.model.request.CategoryAddRequest;
import cn.org.faster.cms.admin.category.model.request.CategoryQueryRequest;
import cn.org.faster.cms.admin.category.model.request.CategoryListRequest;
import cn.org.faster.cms.admin.category.model.request.CategoryUpdateRequest;
import cn.org.faster.cms.admin.category.entity.Category;

/**
 * @author faster-builder
 * 分类 Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * @return 分类树列表
     */
    @GetMapping("/tree")
    @RequiresPermissions("category:tree")
    public ResponseEntity tree() {
        return ResponseEntity.ok(categoryService.treeList());
    }

    /**
     * 分类分页列表
     *
     * @return ResponseEntity
     */
    @GetMapping
    @RequiresPermissions("category:list")
    public ResponseEntity list() {
        return ResponseEntity.ok(categoryService.list());
    }

    /**
     * 分类根据id查询详情
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    @RequiresPermissions("category:info")
    public ResponseEntity queryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    /**
     * 分类根据条件查询详情
     *
     * @return ResponseEntity
     */
    @GetMapping("/query")
    @RequiresPermissions("category:info")
    public ResponseEntity query(CategoryQueryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        return ResponseEntity.ok(categoryService.query(category));
    }

    /**
     * 新增分类
     *
     * @param request 请求参数
     * @return ResponseEntity
     */
    @PostMapping
    @RequiresPermissions("category:add")
    public ResponseEntity add(@Validated @RequestBody CategoryAddRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        return categoryService.add(category);
    }

    /**
     * 更新分类
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    @RequiresPermissions("category:modify")
    public ResponseEntity update(@PathVariable Long id,@RequestBody CategoryUpdateRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        category.setId(id);
        return categoryService.update(category);
    }

    /**
     * 删除分类
     *
     * @param ids 主键id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("category:delete")
    public ResponseEntity delete(@RequestBody List<Long>  ids) {
        ids.forEach(item -> {
            if (item == 0L) {
                return;
            }
            categoryService.delete(item);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}