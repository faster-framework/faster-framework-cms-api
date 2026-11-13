package cn.org.faster.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

/**
 * JWT 配置。
 *
 * @param secret HS256 密钥，至少 32 字节
 * @param ttl    token 有效期
 * @param issuer 签发者
 */
@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        @DefaultValue("change-me-faster-cms-jwt-secret-please-override-32bytes") String secret,
        @DefaultValue("PT2H") Duration ttl,
        @DefaultValue("faster-cms") String issuer
) {
}
