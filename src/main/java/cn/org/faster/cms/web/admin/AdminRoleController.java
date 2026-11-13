package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysRole;
import cn.org.faster.cms.service.RoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理接口。
 */
@RestController
@RequestMapping("/admin/sys/roles")
public class AdminRoleController {

    private final RoleService roleService;

    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('roles:list')")
    public PageResult<SysRole> list(@RequestParam(required = false) String name,
                                    @RequestParam(defaultValue = "1") long current,
                                    @RequestParam(defaultValue = "20") long size) {
        return roleService.page(new RoleService.Query(name), current, size);
    }

    @GetMapping("/all")
    public List<SysRole> all() {
        return roleService.listAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('roles:info')")
    public SysRole detail(@PathVariable Long id) {
        return roleService.getRequired(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('roles:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public SysRole create(@Valid @RequestBody RbacDtos.CreateRole request) {
        SysRole role = new SysRole();
        role.setName(request.name());
        return roleService.create(role);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('roles:modify')")
    public SysRole update(@PathVariable Long id, @Valid @RequestBody RbacDtos.UpdateRole request) {
        SysRole patch = new SysRole();
        patch.setName(request.name());
        return roleService.update(id, patch);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('roles:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的角色");
        }
        ids.forEach(roleService::delete);
    }

    @GetMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('roles:permissions:list')")
    public List<Long> permissions(@PathVariable Long id) {
        return roleService.permissionIds(id);
    }

    @PutMapping("/{id}/permissions/choose")
    @PreAuthorize("hasAuthority('roles:permissions:choose')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void choosePermissions(@PathVariable Long id,
                                  @RequestBody RbacDtos.ChoosePermissions request) {
        roleService.choosePermissions(id, request.permissionIds());
    }
}
