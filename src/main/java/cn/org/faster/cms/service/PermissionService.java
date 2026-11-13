package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.TreeUtils;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.domain.SysPermission;
import cn.org.faster.cms.mapper.SysPermissionMapper;
import cn.org.faster.cms.mapper.SysRolePermissionMapper;
import cn.org.faster.cms.domain.SysRolePermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限服务。
 */
@Service
@Transactional
public class PermissionService {

    private final SysPermissionMapper permissionMapper;
    private final SysRolePermissionMapper rolePermissionMapper;

    public PermissionService(SysPermissionMapper permissionMapper,
                             SysRolePermissionMapper rolePermissionMapper) {
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Transactional(readOnly = true)
    public List<SysPermission> list() {
        return permissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .orderByAsc(SysPermission::getParentIds)
                .orderByAsc(SysPermission::getSort)
                .orderByAsc(SysPermission::getId));
    }

    @Transactional(readOnly = true)
    public List<SysPermission> tree() {
        List<SysPermission> flat = list();
        return TreeUtils.build(
                flat, SysPermission::getId, SysPermission::getParentId, SysPermission::setChildren);
    }

    @Transactional(readOnly = true)
    public SysPermission getRequired(Long id) {
        SysPermission permission = id == null ? null : permissionMapper.selectById(id);
        if (permission == null) {
            throw BizException.of(ErrorCode.PERMISSION_NOT_FOUND);
        }
        return permission;
    }

    @Transactional(readOnly = true)
    public List<String> allCodes() {
        return permissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                        .select(SysPermission::getCode))
                .stream()
                .map(SysPermission::getCode)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Long> idsByRoleIds(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        return rolePermissionMapper.selectList(Wrappers.<SysRolePermission>lambdaQuery()
                        .in(SysRolePermission::getRoleId, roleIds))
                .stream()
                .map(SysRolePermission::getPermissionId)
                .distinct()
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> codesByRoleIds(Collection<Long> roleIds) {
        List<Long> ids = idsByRoleIds(roleIds);
        if (ids.isEmpty()) {
            return List.of();
        }
        return permissionMapper.selectByIds(ids).stream()
                .map(SysPermission::getCode)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();
    }

    public SysPermission create(SysPermission permission) {
        SysPermission parent = resolveParent(permission.getParentId());
        if (StringUtils.hasText(permission.getCode())) {
            if (permissionMapper.selectCount(Wrappers.<SysPermission>lambdaQuery()
                    .eq(SysPermission::getCode, permission.getCode())) > 0) {
                throw BizException.of(ErrorCode.PERMISSION_CODE_EXISTS);
            }
        }
        permission.setParentId(parent == null ? 0L : parent.getId());
        permission.setParentIds(parent == null ? "[0]" : parent.getParentIds() + ",[" + parent.getId() + "]");
        permissionMapper.insert(permission);
        return permission;
    }

    public SysPermission update(Long id, SysPermission patch) {
        SysPermission existing = getRequired(id);
        if (StringUtils.hasText(patch.getCode())
                && !Objects.equals(patch.getCode(), existing.getCode())
                && permissionMapper.selectCount(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getCode, patch.getCode())) > 0) {
            throw BizException.of(ErrorCode.PERMISSION_CODE_EXISTS);
        }
        patch.setId(id);
        patch.setParentId(null);
        patch.setParentIds(null);
        permissionMapper.updateById(patch);
        return permissionMapper.selectById(id);
    }

    public void delete(Long id) {
        getRequired(id);
        Set<Long> ids = permissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                        .like(SysPermission::getParentIds, "[" + id + "]"))
                .stream()
                .map(SysPermission::getId)
                .collect(Collectors.toSet());
        ids.add(id);
        permissionMapper.deleteByIds(ids);
        rolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery()
                .in(SysRolePermission::getPermissionId, ids));
    }

    private SysPermission resolveParent(Long parentId) {
        if (parentId == null || parentId == 0L) {
            return null;
        }
        SysPermission parent = permissionMapper.selectById(parentId);
        if (parent == null) {
            throw BizException.of(ErrorCode.PERMISSION_PARENT_NOT_FOUND);
        }
        return parent;
    }
}
