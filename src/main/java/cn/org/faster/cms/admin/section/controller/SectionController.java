package cn.org.faster.cms.admin.section.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.org.faster.cms.admin.section.service.SectionService;
import cn.org.faster.cms.admin.section.model.request.SectionAddRequest;
import cn.org.faster.cms.admin.section.model.request.SectionQueryRequest;
import cn.org.faster.cms.admin.section.model.request.SectionUpdateRequest;
import cn.org.faster.cms.admin.section.entity.Section;

/**
 * @author faster-builder
 * 栏目 Controller
 */
@RestController
@RequestMapping("/admin/section")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    /**
     * @return 栏目树列表
     */
    @GetMapping("/tree")
    @RequiresPermissions("section:tree")
    public ResponseEntity tree() {
        return ResponseEntity.ok(sectionService.treeList());
    }

    /**
     * 栏目分页列表
     *
     * @return ResponseEntity
     */
    @GetMapping
    @RequiresPermissions("section:list")
    public ResponseEntity list() {
        return ResponseEntity.ok(sectionService.list(new LambdaQueryWrapper<Section>().select(Section.class, item -> !item.getProperty().equals("content"))));
    }

    /**
     * 栏目根据id查询详情
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    @RequiresPermissions("section:info")
    public ResponseEntity queryById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.getById(id));
    }

    /**
     * 栏目根据条件查询详情
     *
     * @return ResponseEntity
     */
    @GetMapping("/query")
    @RequiresPermissions("section:info")
    public ResponseEntity query(SectionQueryRequest request) {
        Section section = new Section();
        BeanUtils.copyProperties(request, section);
        return ResponseEntity.ok(sectionService.query(section));
    }

    /**
     * 新增栏目
     *
     * @param request 请求参数
     * @return ResponseEntity
     */
    @PostMapping
    @RequiresPermissions("section:add")
    public ResponseEntity add(@Validated @RequestBody SectionAddRequest request) {
        Section section = new Section();
        BeanUtils.copyProperties(request, section);
        return sectionService.add(section);
    }

    /**
     * 更新栏目
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    @RequiresPermissions("section:modify")
    public ResponseEntity update(@PathVariable Long id,@RequestBody SectionUpdateRequest request) {
        Section section = new Section();
        BeanUtils.copyProperties(request, section);
        section.setId(id);
        return sectionService.update(section);
    }

    /**
     * 删除栏目
     *
     * @param ids 主键id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("section:delete")
    public ResponseEntity delete(@RequestBody List<Long>  ids) {
        ids.forEach(item -> {
            if (item == 0L) {
                return;
            }
            sectionService.delete(item);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}