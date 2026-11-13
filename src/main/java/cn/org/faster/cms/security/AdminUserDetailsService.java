package cn.org.faster.cms.security;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.domain.SysRole;
import cn.org.faster.cms.domain.SysUser;
import cn.org.faster.cms.service.PermissionService;
import cn.org.faster.cms.service.UserRoleService;
import cn.org.faster.cms.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户认证信息装配。
 *
 * <p>权限在每次请求时从数据库加载，配合 {@code TokenDenylist} 实现改密/删除后立即失效。</p>
 */
@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final PermissionService permissionService;

    public AdminUserDetailsService(UserService userService,
                                   UserRoleService userRoleService,
                                   PermissionService permissionService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.permissionService = permissionService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByAccount(username)
                .map(this::toDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
    }

    @Transactional(readOnly = true)
    public AdminUserDetails loadById(Long id) {
        SysUser user;
        try {
            user = userService.getRequired(id);
        } catch (BizException ex) {
            throw new UsernameNotFoundException("用户不存在: " + id, ex);
        }
        return toDetails(user);
    }

    private AdminUserDetails toDetails(SysUser user) {
        List<Long> roleIds = userRoleService.rolesOfUser(user.getId());
        List<String> permissions = roleIds.contains(SysRole.ADMIN_ROLE_ID)
                ? permissionService.allCodes()
                : permissionService.codesByRoleIds(roleIds);
        return new AdminUserDetails(user.getId(), user.getAccount(), user.getName(), permissions);
    }
}
