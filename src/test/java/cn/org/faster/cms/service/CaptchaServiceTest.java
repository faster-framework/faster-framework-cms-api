package cn.org.faster.cms.service;

import cn.org.faster.cms.config.CaptchaProperties;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class CaptchaServiceTest {

    @Test
    void issuesBase64ImageChallenge() {
        CaptchaService service = new CaptchaService(new CaptchaProperties(true, Duration.ofMinutes(2), 4));

        CaptchaService.Challenge challenge = service.issue();

        assertThat(challenge.token()).isNotBlank();
        assertThat(challenge.image()).startsWith("data:image/png;base64,");
        assertThat(challenge.image().length()).isGreaterThan(100);
    }

    @Test
    void wrongCodeIsRejected() {
        CaptchaService service = new CaptchaService(new CaptchaProperties(true, Duration.ofMinutes(2), 4));
        CaptchaService.Challenge challenge = service.issue();

        assertThat(service.verify(challenge.token(), "0000")).isFalse();
    }

    @Test
    void challengeIsSingleUse() {
        CaptchaService service = new CaptchaService(new CaptchaProperties(true, Duration.ofMinutes(2), 4));
        CaptchaService.Challenge challenge = service.issue();

        assertThat(service.verify(challenge.token(), "0000")).isFalse();
        assertThat(service.verify(challenge.token(), "0000")).isFalse();
    }

    @Test
    void expiredChallengeIsRejected() {
        CaptchaService service = new CaptchaService(new CaptchaProperties(true, Duration.ZERO, 4));
        CaptchaService.Challenge challenge = service.issue();

        assertThat(service.verify(challenge.token(), "0000")).isFalse();
    }

    @Test
    void unknownTokenIsRejected() {
        CaptchaService service = new CaptchaService(new CaptchaProperties(true, Duration.ofMinutes(2), 4));

        assertThat(service.verify("unknown", "ABCD")).isFalse();
        assertThat(service.verify(null, "ABCD")).isFalse();
    }
}
