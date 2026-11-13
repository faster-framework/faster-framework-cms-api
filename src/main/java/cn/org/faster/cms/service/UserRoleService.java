package cn.org.faster.cms.service;

import cn.org.faster.cms.domain.SysUserRole;
import cn.org.faster.cms.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户-角色关系服务。
 */
@Service
@Transactional
public class UserRoleService {

    private final SysUserRoleMapper mapper;

    public UserRoleService(SysUserRoleMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<Long> rolesOfUser(Long userId) {
        return mapper.selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId))
                .stream()
                .map(SysUserRole::getRoleId)
                .distinct()
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Long> usersOfRole(Long roleId) {
        return mapper.selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, roleId))
                .stream()
                .map(SysUserRole::getUserId)
                .distinct()
                .toList();
    }

    public void choose(Long userId, Collection<Long> roleIds) {
        deleteByUser(userId);
        if (roleIds == null) {
            return;
        }
        roleIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .forEach(roleId -> {
                    SysUserRole relation = new SysUserRole();
                    relation.setUserId(userId);
                    relation.setRoleId(roleId);
                    mapper.insert(relation);
                });
    }

    public void deleteByUser(Long userId) {
        mapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
    }

    public void deleteByRole(Long roleId) {
        mapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, roleId));
    }
}
