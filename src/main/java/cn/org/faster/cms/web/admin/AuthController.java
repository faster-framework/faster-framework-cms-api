package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.service.AuthService;
import cn.org.faster.cms.web.dto.AuthDtos;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录 / 用户信息 / 登出。
 */
@RestController
@RequestMapping("/admin")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthDtos.Token login(@Valid @RequestBody AuthDtos.Login request) {
        AuthService.LoginResult result = authService.login(new AuthService.LoginCommand(
                request.account(), request.password(), request.captcha(), request.captchaToken()));
        return new AuthDtos.Token(result.token(), result.expiresAt());
    }

    @GetMapping("/userInfo")
    public AuthDtos.UserInfo userInfo() {
        AuthService.UserInfo info = authService.currentUser();
        return new AuthDtos.UserInfo(info.id(), info.account(), info.name(), info.permissions());
    }

    /**
     * 登出后当前 token 立即失效。
     */
    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            authService.logout(authorization.substring("Bearer ".length()).trim());
        }
    }
}
