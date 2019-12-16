package cn.org.faster.cms.test.api.v1.article;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.Test;
import cn.org.faster.cms.test.BaseTest;
import com.alibaba.fastjson.JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import cn.org.faster.cms.api.article.model.request.ArticleAddRequest;
import cn.org.faster.cms.api.article.model.request.ArticleUpdateRequest;

/**
 * @author faster-builder
 * 文章 Test
 */
public class ArticleTest extends BaseTest{
    /**
     * 生成的为local环境的token。
     * 通过jwtService.createToken创建。其中audience设为0L，expSecond不超时，base64Security使用ProjectProperties中的默认值，env为local。
     */
    @Override
    public String getToken() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwIiwiaWF0IjoxNTc1MDA2MTU3LCJlbnYiOiJsb2NhbCJ9.XEk5C2Ga8u2ftdYSgMHeeUAF2PfktL5nma2WCyoaRvY";
    }

    /**
     * 列表
     */
    @Test
    public void list() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =  MockMvcRequestBuilders.get("/api/v1/article");
        mockHttpServletRequestBuilder.param("pageSize","10");
        mockHttpServletRequestBuilder.param("pageNumber","1");
        mockHttpServletRequestBuilder.param("id","");
        mockHttpServletRequestBuilder.param("title","");
        mockHttpServletRequestBuilder.param("description","");
        mockHttpServletRequestBuilder.param("img","");
        mockHttpServletRequestBuilder.param("categoryId","");
        mockHttpServletRequestBuilder.param("publishStatus","");
        mockHttpServletRequestBuilder.param("publishDate","");
        mockHttpServletRequestBuilder.param("showStatus","");
        mockHttpServletRequestBuilder.param("createBy","");
        mockHttpServletRequestBuilder.param("updateBy","");
        mockHttpServletRequestBuilder.param("createDate","");
        mockHttpServletRequestBuilder.param("updateDate","");
        mockHttpServletRequestBuilder.param("sort","");
        mockHttpServletRequestBuilder.param("remark","");
        mockHttpServletRequestBuilder.param("deleted","");
        this.buildRequest(() -> mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
    }

    /**
     * 根据id查询
     */
    @Test
    public void queryById() throws Exception {
        this.buildRequest(() -> MockMvcRequestBuilders.get("/api/v1/article/{id}", "")).andExpect(status().is2xxSuccessful());
    }

    /**
     * 根据参数查询
     */
    @Test
    public void query() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =  MockMvcRequestBuilders.get("/api/v1/article/query");
        mockHttpServletRequestBuilder.param("id","");
        mockHttpServletRequestBuilder.param("title","");
        mockHttpServletRequestBuilder.param("description","");
        mockHttpServletRequestBuilder.param("img","");
        mockHttpServletRequestBuilder.param("categoryId","");
        mockHttpServletRequestBuilder.param("publishStatus","");
        mockHttpServletRequestBuilder.param("publishDate","");
        mockHttpServletRequestBuilder.param("showStatus","");
        mockHttpServletRequestBuilder.param("createBy","");
        mockHttpServletRequestBuilder.param("updateBy","");
        mockHttpServletRequestBuilder.param("createDate","");
        mockHttpServletRequestBuilder.param("updateDate","");
        mockHttpServletRequestBuilder.param("sort","");
        mockHttpServletRequestBuilder.param("remark","");
        mockHttpServletRequestBuilder.param("deleted","");
        this.buildRequest(() -> mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
    }

    /**
     * 添加
     */
    @Test
    public void add() throws Exception {
        ArticleAddRequest request = new ArticleAddRequest();
        request.setId(null);
        request.setTitle(null);
        request.setDescription(null);
        request.setImg(null);
        request.setCategoryId(null);
        request.setPublishStatus(null);
        request.setPublishDate(null);
        request.setCreateBy(null);
        request.setUpdateBy(null);
        request.setCreateDate(null);
        request.setUpdateDate(null);
        request.setSort(null);
        request.setRemark(null);
        request.setDeleted(null);
        this.buildRequest(() -> MockMvcRequestBuilders.post("/api/v1/article").content(JSON.toJSONString(request))).andExpect(status().is2xxSuccessful());
    }

    /**
     * 更新
     */
    @Test
    public void update() throws Exception {
        ArticleUpdateRequest request = new ArticleUpdateRequest();
        request.setId(null);
        request.setTitle(null);
        request.setDescription(null);
        request.setImg(null);
        request.setCategoryId(null);
        request.setPublishStatus(null);
        request.setPublishDate(null);
        request.setCreateBy(null);
        request.setUpdateBy(null);
        request.setCreateDate(null);
        request.setUpdateDate(null);
        request.setSort(null);
        request.setRemark(null);
        request.setDeleted(null);
        this.buildRequest(()-> MockMvcRequestBuilders.put("/api/v1/article/{id}","").content(JSON.toJSONString(request))).andExpect(status().is2xxSuccessful());
    }

    /**
     * 删除
     */
    @Test
    public void delete() throws Exception {
        this.buildRequest(()-> MockMvcRequestBuilders.delete("/api/v1/article/{id}","")).andExpect(status().is2xxSuccessful());
    }

}