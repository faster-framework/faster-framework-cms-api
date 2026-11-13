package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 栏目接口测试：树形结构、移动栏目、删除保护。
 */
class SectionApiTest extends ApiTestSupport {

    @Test
    void createBuildsParentPath() throws Exception {
        long rootId = create("产品", "product");
        long childId = create("产品子类", "product-child", rootId);
        long grandChildId = create("产品孙类", "product-grand", childId);

        mockMvc.perform(get("/admin/section/" + childId).header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentId").value(rootId))
                .andExpect(jsonPath("$.parentIds").value("[0],[" + rootId + "]"));

        mockMvc.perform(get("/admin/section/" + grandChildId).header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentIds").value("[0],[" + rootId + "],[" + childId + "]"));
    }

    @Test
    void movingSectionRewritesDescendantPaths() throws Exception {
        long sourceRoot = create("源栏目", "source");
        long targetRoot = create("目标栏目", "target");
        long childId = create("子栏目", "child", sourceRoot);
        long grandChildId = create("孙栏目", "grand", childId);

        mockMvc.perform(put("/admin/section/" + childId)
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"parentId":%d}
                                """.formatted(targetRoot)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentIds").value("[0],[" + targetRoot + "]"));

        mockMvc.perform(get("/admin/section/" + grandChildId).header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentIds")
                        .value("[0],[" + targetRoot + "],[" + childId + "]"));
    }

    @Test
    void cannotMoveSectionUnderItsOwnChild() throws Exception {
        long rootId = create("父栏目", "parent");
        long childId = create("子栏目", "child-of-parent", rootId);

        mockMvc.perform(put("/admin/section/" + rootId)
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"parentId":%d}
                                """.formatted(childId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(1162));
    }

    @Test
    void cannotDeleteSectionWithChildren() throws Exception {
        long rootId = create("待删除栏目", "to-delete");
        create("子栏目", "to-delete-child", rootId);

        mockMvc.perform(delete("/admin/section/delete")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" + rootId + "]"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(1163));
    }

    @Test
    void treeReturnsNestedStructure() throws Exception {
        long rootId = create("树根", "tree-root");
        create("树子", "tree-child", rootId);

        mockMvc.perform(get("/admin/section/tree").header(authorization(), bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == %d)].children".formatted(rootId)).exists());
    }

    private long create(String name, String code) throws Exception {
        return create(name, code, null);
    }

    private long create(String name, String code, Long parentId) throws Exception {
        String parentField = parentId == null ? "" : ",\"parentId\":" + parentId;
        String response = mockMvc.perform(post("/admin/section")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"%s","code":"%s","templateType":1,
                                 "templatePath":"section/normal.ftl","publishStatus":1,"showStatus":1%s}
                                """.formatted(name, code, parentField)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(response, "$.id");
    }
}
