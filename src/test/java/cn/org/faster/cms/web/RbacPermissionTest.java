package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RBAC 链路测试：角色授予权限、用户绑定角色、接口按权限放行/拒绝。
 */
class RbacPermissionTest extends ApiTestSupport {

    @Test
    void editorCanOnlyAccessGrantedEndpoints() throws Exception {
        long roleId = createRole("内容编辑");
        grantPermissions(roleId, 41L); // article:list

        long userId = createUser("editor", "editor12345");
        chooseRoles(userId, roleId);

        String editorToken = login("editor", "editor12345");
        String bearer = "Bearer " + editorToken;

        // 已授予 article:list
        mockMvc.perform(get("/admin/article").header(authorization(), bearer))
                .andExpect(status().isOk());

        // 未授予 users:list
        mockMvc.perform(get("/admin/sys/users").header(authorization(), bearer))
                .andExpect(status().isForbidden());

        // 未授予 article:info
        mockMvc.perform(get("/admin/article/1").header(authorization(), bearer))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminRoleCannotBeModified() throws Exception {
        mockMvc.perform(put("/admin/sys/roles/0/permissions/choose")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"permissionIds":[41]}
                                """))
                .andExpect(status().isBadRequest());
    }

    private long createRole(String name) throws Exception {
        String response = mockMvc.perform(post("/admin/sys/roles")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"%s"}
                                """.formatted(name)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(response, "$.id");
    }

    private void grantPermissions(long roleId, Long... permissionIds) throws Exception {
        String ids = java.util.Arrays.stream(permissionIds)
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(","));
        mockMvc.perform(put("/admin/sys/roles/" + roleId + "/permissions/choose")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"permissionIds":[%s]}
                                """.formatted(ids)))
                .andExpect(status().isNoContent());
    }

    private long createUser(String account, String password) throws Exception {
        String response = mockMvc.perform(post("/admin/sys/users")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"account":"%s","password":"%s","name":"编辑"}
                                """.formatted(account, password)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(response, "$.id");
    }

    private void chooseRoles(long userId, long roleId) throws Exception {
        mockMvc.perform(put("/admin/sys/users/" + userId + "/roles/choose")
                        .header(authorization(), bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"roleIds":[%d]}
                                """.formatted(roleId)))
                .andExpect(status().isNoContent());
    }
}
