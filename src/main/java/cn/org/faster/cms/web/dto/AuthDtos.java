package cn.org.faster.cms.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 认证相关请求/响应体。
 */
public final class AuthDtos {

    private AuthDtos() {
    }

    public record Login(
            @NotBlank(message = "请输入账号") String account,
            @NotBlank(message = "请输入密码") String password,
            String captcha,
            String captchaToken
    ) {
    }

    public record Token(String token, java.time.Instant expiresAt) {
    }

    public record UserInfo(Long id, String account, String name, java.util.List<String> permissions) {
    }

    public record ChangePassword(
            @NotBlank(message = "请输入旧密码") String oldPassword,
            @NotBlank(message = "请输入新密码") @Size(min = 8, message = "密码至少 8 位") String newPassword
    ) {
    }

    public record ResetPassword(String password) {
    }
}
