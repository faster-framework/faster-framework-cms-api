package cn.org.faster.cms.common;

import cn.org.faster.cms.security.AdminUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 当前登录用户访问门面。
 */
public final class CurrentUser {

    private CurrentUser() {
    }

    public static Long id() {
        return principal().map(AdminUserDetails::id).orElse(null);
    }

    public static Optional<AdminUserDetails> principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof AdminUserDetails details) {
            return Optional.of(details);
        }
        return Optional.empty();
    }
}
