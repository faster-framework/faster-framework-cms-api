package cn.org.faster.cms.web.admin;

import cn.org.faster.cms.service.CaptchaService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图形验证码。
 */
@RestController
@RequestMapping("/admin/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping
    public ResponseEntity<CaptchaService.Challenge> issue() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(captchaService.issue());
    }
}
