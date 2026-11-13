package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysDict;
import cn.org.faster.cms.service.DictService;
import cn.org.faster.cms.web.dto.DictDtos;
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
 * 字典管理接口。
 */
@RestController
@RequestMapping("/admin/sys/dict")
public class AdminDictController {

    private final DictService dictService;

    public AdminDictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/all")
    public List<SysDict> all() {
        return dictService.listAll();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('dict:list')")
    public PageResult<SysDict> list(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String type,
                                    @RequestParam(defaultValue = "1") long current,
                                    @RequestParam(defaultValue = "20") long size) {
        return dictService.page(new DictService.Query(null, name, type, null, null), current, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('dict:info')")
    public SysDict detail(@PathVariable Long id) {
        return dictService.getRequired(id);
    }

    @GetMapping("/query")
    @PreAuthorize("hasAuthority('dict:info')")
    public ResponseEntity<SysDict> query(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String type) {
        List<SysDict> list = dictService.list(new DictService.Query(null, name, type, null, null), 1);
        return ResponseEntity.ok(list.isEmpty() ? null : list.get(0));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dict:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public SysDict create(@Valid @RequestBody DictDtos.Create request) {
        return dictService.create(request.toEntity());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('dict:modify')")
    public SysDict update(@PathVariable Long id, @Valid @RequestBody DictDtos.Update request) {
        return dictService.update(id, request.toEntity());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('dict:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的字典");
        }
        ids.forEach(dictService::delete);
    }
}
