package cn.org.faster.cms.test;

import org.junit.Before;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import cn.org.faster.cms.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class BaseTest {
    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = webAppContextSetup(this.wac);
        AbstractShiroFilter shiroFilter = wac.getBean(AbstractShiroFilter.class);
        builder.addFilter(shiroFilter);
        this.mockMvc = builder.build();
    }


    public ResultActions buildRequest(Supplier<MockHttpServletRequestBuilder> method) throws Exception {
        return this.mockMvc.perform(method.get().characterEncoding(StandardCharsets.UTF_8.name())
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getToken())).andDo(print());
    }

    public abstract String getToken();
}
