package cn.org.faster.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

/**
 * 图形验证码配置。
 *
 * @param enabled 是否启用（测试环境可关闭）
 * @param ttl     验证码有效期
 * @param length  验证码字符数
 */
@ConfigurationProperties(prefix = "app.captcha")
public record CaptchaProperties(
        @DefaultValue("true") boolean enabled,
        @DefaultValue("PT2M") Duration ttl,
        @DefaultValue("4") int length
) {
}
