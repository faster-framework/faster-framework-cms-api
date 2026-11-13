package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.CurrentUser;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.config.CaptchaProperties;
import cn.org.faster.cms.domain.SysUser;
import cn.org.faster.cms.security.AdminUserDetails;
import cn.org.faster.cms.security.JwtService;
import cn.org.faster.cms.security.TokenDenylist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

/**
 * 认证服务：图形验证码 + 账号密码 + JWT。
 */
@Service
public class AuthService {

    private final UserService userService;
    private final CaptchaService captchaService;
    private final CaptchaProperties captchaProperties;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenDenylist tokenDenylist;

    public AuthService(UserService userService,
                       CaptchaService captchaService,
                       CaptchaProperties captchaProperties,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       TokenDenylist tokenDenylist) {
        this.userService = userService;
        this.captchaService = captchaService;
        this.captchaProperties = captchaProperties;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenDenylist = tokenDenylist;
    }

    public record LoginCommand(String account, String password, String captcha, String captchaToken) {
    }

    public record LoginResult(String token, Instant expiresAt) {
    }

    public record UserInfo(Long id, String account, String name, List<String> permissions) {
    }

    @Transactional(readOnly = true)
    public LoginResult login(LoginCommand command) {
        if (captchaProperties.enabled()) {
            if (!captchaService.verify(command.captchaToken(), command.captcha())) {
                throw BizException.of(ErrorCode.CAPTCHA_INVALID);
            }
        }
        if (!StringUtils.hasText(command.account()) || !StringUtils.hasText(command.password())) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "账号和密码不能为空");
        }
        SysUser user = userService.findByAccount(command.account())
                .orElseThrow(() -> BizException.of(ErrorCode.PASSWORD_ERROR, "账号或密码错误"));
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw BizException.of(ErrorCode.PASSWORD_ERROR, "账号或密码错误");
        }
        JwtService.IssuedToken token = jwtService.issue(user.getId(), user.getAccount());
        return new LoginResult(token.value(), token.expiresAt());
    }

    public UserInfo currentUser() {
        AdminUserDetails principal = CurrentUser.principal()
                .orElseThrow(() -> BizException.of(ErrorCode.UNAUTHORIZED));
        return new UserInfo(principal.id(), principal.account(), principal.name(), principal.permissions());
    }

    /**
     * 登出：把当前 token 加入黑名单，立即失效。
     */
    public void logout(String token) {
        if (!StringUtils.hasText(token)) {
            return;
        }
        Jwt jwt;
        try {
            jwt = jwtService.parse(token);
        } catch (BizException ex) {
            return;
        }
        tokenDenylist.revoke(jwt.getId(), jwt.getExpiresAt());
    }
}
