package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.common.PageResult;
import cn.org.faster.cms.domain.SysRole;
import cn.org.faster.cms.domain.SysRolePermission;
import cn.org.faster.cms.mapper.SysRoleMapper;
import cn.org.faster.cms.mapper.SysRolePermissionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 角色服务。
 */
@Service
@Transactional
public class RoleService {

    private final SysRoleMapper roleMapper;
    private final SysRolePermissionMapper rolePermissionMapper;
    private final UserRoleService userRoleService;
    private final PermissionService permissionService;

    public RoleService(SysRoleMapper roleMapper,
                       SysRolePermissionMapper rolePermissionMapper,
                       UserRoleService userRoleService,
                       PermissionService permissionService) {
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleService = userRoleService;
        this.permissionService = permissionService;
    }

    public record Query(String name) {
    }

    @Transactional(readOnly = true)
    public PageResult<SysRole> page(Query query, long current, long size) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(query.name())) {
            wrapper.like(SysRole::getName, query.name());
        }
        wrapper.orderByDesc(SysRole::getCreateDate);
        return PageResult.of(roleMapper.selectPage(
                Page.of(current < 1 ? 1 : current, size < 1 ? 20 : size), wrapper));
    }

    @Transactional(readOnly = true)
    public List<SysRole> listAll() {
        return roleMapper.selectList(Wrappers.<SysRole>lambdaQuery().orderByAsc(SysRole::getId));
    }

    @Transactional(readOnly = true)
    public SysRole getRequired(Long id) {
        SysRole role = id == null ? null : roleMapper.selectById(id);
        if (role == null) {
            throw BizException.of(ErrorCode.ROLE_NOT_FOUND);
        }
        return role;
    }

    public SysRole create(SysRole role) {
        roleMapper.insert(role);
        return role;
    }

    public SysRole update(Long id, SysRole patch) {
        requireMutable(id);
        patch.setId(id);
        roleMapper.updateById(patch);
        return roleMapper.selectById(id);
    }

    public void delete(Long id) {
        requireMutable(id);
        if (!userRoleService.usersOfRole(id).isEmpty()) {
            throw BizException.of(ErrorCode.ROLE_IN_USE);
        }
        roleMapper.deleteById(id);
        rolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, id));
    }

    /**
     * 角色拥有的权限 id 列表；超级管理员返回全部。
     */
    @Transactional(readOnly = true)
    public List<Long> permissionIds(Long roleId) {
        getRequired(roleId);
        if (roleId == SysRole.ADMIN_ROLE_ID) {
            return permissionService.list().stream().map(p -> p.getId()).toList();
        }
        return rolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery()
                        .eq(SysRolePermission::getRoleId, roleId))
                .stream()
                .map(SysRolePermission::getPermissionId)
                .distinct()
                .toList();
    }

    public void choosePermissions(Long roleId, Collection<Long> permissionIds) {
        getRequired(roleId);
        if (roleId == SysRole.ADMIN_ROLE_ID) {
            throw BizException.of(ErrorCode.ROLE_PERMISSION_PROTECTED);
        }
        rolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, roleId));
        if (permissionIds == null) {
            return;
        }
        permissionIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .forEach(permissionId -> {
                    SysRolePermission relation = new SysRolePermission();
                    relation.setRoleId(roleId);
                    relation.setPermissionId(permissionId);
                    rolePermissionMapper.insert(relation);
                });
    }

    private void requireMutable(Long id) {
        getRequired(id);
        if (id == SysRole.ADMIN_ROLE_ID) {
            throw BizException.of(ErrorCode.ROLE_PROTECTED);
        }
    }
}
