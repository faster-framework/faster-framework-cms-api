package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysUser;
import cn.org.faster.cms.service.UserRoleService;
import cn.org.faster.cms.service.UserService;
import cn.org.faster.cms.web.dto.AuthDtos;
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
 * 用户管理接口。
 */
@RestController
@RequestMapping("/admin/sys/users")
public class AdminUserController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public AdminUserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:list')")
    public PageResult<SysUser> list(@RequestParam(required = false) String account,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(defaultValue = "1") long current,
                                    @RequestParam(defaultValue = "20") long size) {
        return userService.page(new UserService.Query(account, name), current, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:info')")
    public SysUser detail(@PathVariable Long id) {
        return userService.getRequired(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:add')")
    @ResponseStatus(HttpStatus.CREATED)
    public SysUser create(@Valid @RequestBody RbacDtos.CreateUser request) {
        SysUser user = new SysUser();
        user.setAccount(request.account());
        user.setName(request.name());
        return userService.create(user, request.password());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('users:modify')")
    public SysUser update(@PathVariable Long id, @Valid @RequestBody RbacDtos.UpdateUser request) {
        SysUser patch = new SysUser();
        patch.setName(request.name());
        return userService.update(id, patch);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('users:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "请选择要删除的用户");
        }
        ids.forEach(userService::delete);
    }

    @PutMapping("/{id}/password/change")
    @PreAuthorize("hasAuthority('users:password:change')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id,
                               @Valid @RequestBody AuthDtos.ChangePassword request) {
        userService.changePassword(id, request.oldPassword(), request.newPassword());
    }

    /**
     * 重置为随机密码，明文仅在本次响应中返回一次。
     */
    @PutMapping("/{id}/password/reset")
    @PreAuthorize("hasAuthority('users:password:reset')")
    public AuthDtos.ResetPassword resetPassword(@PathVariable Long id) {
        return new AuthDtos.ResetPassword(userService.resetPassword(id));
    }

    @PutMapping("/{id}/roles/choose")
    @PreAuthorize("hasAuthority('users:roles:choose')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chooseRoles(@PathVariable Long id, @Valid @RequestBody RbacDtos.ChooseRoles request) {
        userService.getRequired(id);
        userRoleService.choose(id, request.roleIds());
    }

    @GetMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('users:roles:list')")
    public List<Long> roles(@PathVariable Long id) {
        userService.getRequired(id);
        return userRoleService.rolesOfUser(id);
    }
}
