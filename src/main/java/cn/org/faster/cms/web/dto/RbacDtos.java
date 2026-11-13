package cn.org.faster.cms.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * 用户、角色、权限请求体。
 */
public final class RbacDtos {

    private RbacDtos() {
    }

    public record CreateUser(
            @NotBlank(message = "请输入账号") @Size(max = 64) String account,
            @NotBlank(message = "请输入密码") @Size(min = 8, message = "密码至少 8 位") String password,
            @NotBlank(message = "请输入姓名") @Size(max = 64) String name
    ) {
    }

    public record UpdateUser(@Size(max = 64) String name) {
    }

    public record ChooseRoles(@NotEmpty(message = "请选择角色") List<Long> roleIds) {
    }

    public record CreateRole(@NotBlank(message = "请输入角色名称") @Size(max = 64) String name) {
    }

    public record UpdateRole(@Size(max = 64) String name) {
    }

    public record ChoosePermissions(List<Long> permissionIds) {
    }

    public record CreatePermission(
            @NotBlank(message = "请输入权限名称") @Size(max = 64) String name,
            @NotBlank(message = "请输入权限编码") @Size(max = 64) String code,
            Long parentId,
            Integer sort
    ) {
    }

    public record UpdatePermission(@Size(max = 64) String name, @Size(max = 64) String code) {
    }
}
