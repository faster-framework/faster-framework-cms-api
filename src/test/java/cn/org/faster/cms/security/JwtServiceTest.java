package cn.org.faster.cms.security;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.config.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET = "0123456789abcdef0123456789abcdef";

    private final JwtService jwtService = new JwtService(
            new JwtProperties(SECRET, Duration.ofHours(1), "faster-cms-test"));

    @Test
    void issueAndParseToken() {
        JwtService.IssuedToken issued = jwtService.issue(42L, "alice");

        Jwt jwt = jwtService.parse(issued.value());

        assertThat(jwt.getSubject()).isEqualTo("42");
        assertThat(jwt.<String>getClaim("account")).isEqualTo("alice");
        assertThat(jwt.getId()).isNotBlank();
        assertThat(jwt.getExpiresAt()).isAfter(Instant.now());
        assertThat(issued.expiresAt()).isAfter(Instant.now());
    }

    @Test
    void rejectsMalformedToken() {
        assertThatThrownBy(() -> jwtService.parse("not-a-token"))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("登录凭证无效");
    }

    @Test
    void rejectsTokenSignedWithAnotherKey() {
        JwtService other = new JwtService(
                new JwtProperties("ffffffffffffffffffffffffffffffff", Duration.ofHours(1), "other"));
        String token = other.issue(1L, "bob").value();

        assertThatThrownBy(() -> jwtService.parse(token))
                .isInstanceOf(BizException.class);
    }

    @Test
    void requiresAtLeast256BitSecret() {
        assertThatThrownBy(() -> new JwtService(
                new JwtProperties("too-short", Duration.ofHours(1), "x")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("at least 32 bytes");
    }
}
