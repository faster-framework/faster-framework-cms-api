package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.domain.SysPermission;
import cn.org.faster.cms.service.PermissionService;
import cn.org.faster.cms.web.dto.RbacDtos;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理接口。
 */
@RestController
@RequestMapping("/admin/sys/permissions")
public class AdminPermissionController {

    private final PermissionService permissionService;

    public AdminPermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('permissions:tree')")
    public List<SysPermission> tree() {
        return permissionService.tree();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('permissions:list')")
    public List<SysPermission> list() {
        return permissionService.list();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('permissions:info')")
    public SysPermission detail(@PathVariable Long id) {
        return permissionService.getRequired(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('permissions:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public SysPermission create(@Valid @RequestBody RbacDtos.CreatePermission request) {
        SysPermission permission = new SysPermission();
        permission.setName(request.name());
        permission.setCode(request.code());
        permission.setParentId(request.parentId() == null ? 0L : request.parentId());
        permission.setSort(request.sort());
        return permissionService.create(permission);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('permissions:modify')")
    public SysPermission update(@PathVariable Long id,
                                @Valid @RequestBody RbacDtos.UpdatePermission request) {
        SysPermission patch = new SysPermission();
        patch.setName(request.name());
        patch.setCode(request.code());
        return permissionService.update(id, patch);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('permissions:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的权限");
        }
        ids.forEach(permissionService::delete);
    }
}
