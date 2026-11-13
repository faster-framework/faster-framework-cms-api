package cn.org.faster.cms;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMvc 集成测试基类：H2 + 事务回滚 + 管理员登录。
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public abstract class ApiTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    protected String adminToken;

    @BeforeEach
    void loginAsAdmin() throws Exception {
        adminToken = login("admin", "admin");
    }

    protected String login(String account, String password) throws Exception {
        String body = """
                {"account":"%s","password":"%s"}
                """.formatted(account, password);
        String response = mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(response, "$.token");
    }

    protected String bearer() {
        return "Bearer " + adminToken;
    }

    protected String authorization() {
        return HttpHeaders.AUTHORIZATION;
    }
}
