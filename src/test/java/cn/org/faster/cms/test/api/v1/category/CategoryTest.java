package cn.org.faster.cms.test.api.v1.category;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.Test;
import cn.org.faster.cms.test.BaseTest;
import com.alibaba.fastjson.JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import cn.org.faster.cms.api.category.model.request.CategoryAddRequest;
import cn.org.faster.cms.api.category.model.request.CategoryUpdateRequest;

/**
 * @author faster-builder
 * 分类 Test
 */
public class CategoryTest extends BaseTest{
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =  MockMvcRequestBuilders.get("/api/v1/category");
        mockHttpServletRequestBuilder.param("pageSize","10");
        mockHttpServletRequestBuilder.param("pageNumber","1");
        mockHttpServletRequestBuilder.param("id","");
        mockHttpServletRequestBuilder.param("name","");
        mockHttpServletRequestBuilder.param("code","");
        mockHttpServletRequestBuilder.param("position","");
        mockHttpServletRequestBuilder.param("description","");
        mockHttpServletRequestBuilder.param("img","");
        mockHttpServletRequestBuilder.param("parentId","");
        mockHttpServletRequestBuilder.param("parentIds","");
        mockHttpServletRequestBuilder.param("reqType","");
        mockHttpServletRequestBuilder.param("reqLocation","");
        mockHttpServletRequestBuilder.param("publishStatus","");
        mockHttpServletRequestBuilder.param("showStatus","");
        mockHttpServletRequestBuilder.param("templatePath","");
        mockHttpServletRequestBuilder.param("contentTemplatePath","");
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
        this.buildRequest(() -> MockMvcRequestBuilders.get("/api/v1/category/{id}", "")).andExpect(status().is2xxSuccessful());
    }

    /**
     * 根据参数查询
     */
    @Test
    public void query() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =  MockMvcRequestBuilders.get("/api/v1/category/query");
        mockHttpServletRequestBuilder.param("id","");
        mockHttpServletRequestBuilder.param("name","");
        mockHttpServletRequestBuilder.param("code","");
        mockHttpServletRequestBuilder.param("position","");
        mockHttpServletRequestBuilder.param("description","");
        mockHttpServletRequestBuilder.param("img","");
        mockHttpServletRequestBuilder.param("parentId","");
        mockHttpServletRequestBuilder.param("parentIds","");
        mockHttpServletRequestBuilder.param("reqType","");
        mockHttpServletRequestBuilder.param("reqLocation","");
        mockHttpServletRequestBuilder.param("publishStatus","");
        mockHttpServletRequestBuilder.param("showStatus","");
        mockHttpServletRequestBuilder.param("templatePath","");
        mockHttpServletRequestBuilder.param("contentTemplatePath","");
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
        CategoryAddRequest request = new CategoryAddRequest();
        request.setId(null);
        request.setName(null);
        request.setCode(null);
        request.setPosition(null);
        request.setDescription(null);
        request.setImg(null);
        request.setParentId(null);
        request.setParentIds(null);
        request.setReqType(null);
        request.setReqLocation(null);
        request.setPublishStatus(null);
        request.setShowStatus(null);
        request.setTemplatePath(null);
        request.setContentTemplatePath(null);
        request.setCreateBy(null);
        request.setUpdateBy(null);
        request.setCreateDate(null);
        request.setUpdateDate(null);
        request.setSort(null);
        request.setRemark(null);
        request.setDeleted(null);
        this.buildRequest(() -> MockMvcRequestBuilders.post("/api/v1/category").content(JSON.toJSONString(request))).andExpect(status().is2xxSuccessful());
    }

    /**
     * 更新
     */
    @Test
    public void update() throws Exception {
        CategoryUpdateRequest request = new CategoryUpdateRequest();
        request.setId(null);
        request.setName(null);
        request.setCode(null);
        request.setPosition(null);
        request.setDescription(null);
        request.setImg(null);
        request.setParentId(null);
        request.setParentIds(null);
        request.setReqType(null);
        request.setReqLocation(null);
        request.setPublishStatus(null);
        request.setShowStatus(null);
        request.setTemplatePath(null);
        request.setContentTemplatePath(null);
        request.setCreateBy(null);
        request.setUpdateBy(null);
        request.setCreateDate(null);
        request.setUpdateDate(null);
        request.setSort(null);
        request.setRemark(null);
        request.setDeleted(null);
        this.buildRequest(()-> MockMvcRequestBuilders.put("/api/v1/category/{id}","").content(JSON.toJSONString(request))).andExpect(status().is2xxSuccessful());
    }

    /**
     * 删除
     */
    @Test
    public void delete() throws Exception {
        this.buildRequest(()-> MockMvcRequestBuilders.delete("/api/v1/category/{id}","")).andExpect(status().is2xxSuccessful());
    }

}