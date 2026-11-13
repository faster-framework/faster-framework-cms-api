package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.domain.Section;
import cn.org.faster.cms.service.SectionService;
import cn.org.faster.cms.web.dto.SectionDtos;
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
 * 栏目管理接口。
 */
@RestController
@RequestMapping("/admin/section")
public class AdminSectionController {

    private final SectionService sectionService;

    public AdminSectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('section:tree')")
    public List<Section> tree() {
        return sectionService.tree();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('section:list')")
    public List<Section> list(@RequestParam(required = false) Long parentId) {
        return sectionService.list(new SectionService.Query(
                null, parentId, null, null, null, null, null), Integer.MAX_VALUE);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('section:info')")
    public Section detail(@PathVariable Long id) {
        return sectionService.getRequired(id);
    }

    @GetMapping("/query")
    @PreAuthorize("hasAuthority('section:info')")
    public ResponseEntity<Section> query(@RequestParam(required = false) String code) {
        return ResponseEntity.ok(sectionService.one(new SectionService.Query(
                null, null, code, null, null, null, null)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('section:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public Section create(@Valid @RequestBody SectionDtos.Create request) {
        return sectionService.create(request.toEntity());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('section:modify')")
    public Section update(@PathVariable Long id, @Valid @RequestBody SectionDtos.Update request) {
        return sectionService.update(id, request.toEntity());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('section:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的栏目");
        }
        ids.forEach(sectionService::delete);
    }
}
