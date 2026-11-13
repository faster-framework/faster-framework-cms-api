package cn.org.faster.cms.security;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 已失效 token 记录。
 *
 * <p>无状态 JWT 的登出依赖该黑名单；当前实现为单机内存版，
 * 多实例部署时应替换为 Redis 等共享存储（见 README）。</p>
 */
@Component
public class TokenDenylist {

    private final Map<String, Instant> revoked = new ConcurrentHashMap<>();
    private final Map<Long, Instant> userInvalidBefore = new ConcurrentHashMap<>();

    /**
     * 注销单个 token。
     */
    public void revoke(String jwtId, Instant expiresAt) {
        if (jwtId != null && expiresAt != null) {
            revoked.put(jwtId, expiresAt);
        }
        purge();
    }

    public boolean isRevoked(String jwtId) {
        Instant expiresAt = jwtId == null ? null : revoked.get(jwtId);
        return expiresAt != null && expiresAt.isAfter(Instant.now());
    }

    /**
     * 使某用户此前签发的所有 token 失效（改密、删除、强制下线）。
     */
    public void invalidateUser(Long userId) {
        if (userId != null) {
            userInvalidBefore.put(userId, Instant.now());
        }
    }

    public boolean isStale(Long userId, Instant issuedAt) {
        Instant threshold = userId == null ? null : userInvalidBefore.get(userId);
        return threshold != null && (issuedAt == null || issuedAt.isBefore(threshold));
    }

    private void purge() {
        Instant now = Instant.now();
        revoked.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }
}
