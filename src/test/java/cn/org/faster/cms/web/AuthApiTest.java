package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 认证接口测试。
 */
class AuthApiTest extends ApiTestSupport {

    @Test
    void loginReturnsTokenAndUserInfo() throws Exception {
        mockMvc.perform(get("/admin/userInfo").header(HttpHeaders.AUTHORIZATION, bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("超级管理员"))
                .andExpect(jsonPath("$.permissions").isArray())
                .andExpect(jsonPath("$.permissions", org.hamcrest.Matchers.hasItem("article:list")));
    }

    @Test
    void captchaIsPubliclyAvailable() throws Exception {
        mockMvc.perform(get("/admin/captcha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.image").value(org.hamcrest.Matchers.startsWith("data:image/png;base64,")));
    }

    @Test
    void loginWithWrongPasswordIsRejected() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"account":"admin","password":"wrong-password"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(1101));
    }

    @Test
    void protectedEndpointWithoutTokenReturns401() throws Exception {
        mockMvc.perform(get("/admin/article"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(40100));
    }

    @Test
    void tokenIsRejectedAfterLogout() throws Exception {
        mockMvc.perform(delete("/admin/logout").header(HttpHeaders.AUTHORIZATION, bearer()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/admin/userInfo").header(HttpHeaders.AUTHORIZATION, bearer()))
                .andExpect(status().isUnauthorized());
    }
}
