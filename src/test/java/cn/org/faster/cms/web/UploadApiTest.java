package cn.org.faster.cms.web;

import cn.org.faster.cms.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 上传接口测试：鉴权、白名单、返回结构。
 */
class UploadApiTest extends ApiTestSupport {

    private static final byte[] PNG = new byte[]{
            (byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A, 0, 0, 0, 0x0D, 'I', 'H', 'D', 'R'
    };

    @Test
    void uploadPngReturnsUrl() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "logo.png", "image/png", PNG);

        mockMvc.perform(multipart("/admin/media/upload").file(file)
                        .header(authorization(), bearer()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value(org.hamcrest.Matchers.startsWith("/media/")))
                .andExpect(jsonPath("$.name").value("logo.png"))
                .andExpect(jsonPath("$.size").value(PNG.length));
    }

    @Test
    void executableExtensionIsRejected() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "shell.exe", "application/octet-stream",
                new byte[]{1, 2, 3});

        mockMvc.perform(multipart("/admin/media/upload").file(file)
                        .header(authorization(), bearer()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(40000));
    }

    @Test
    void anonymousUploadIsRejected() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "logo.png", "image/png", PNG);

        mockMvc.perform(multipart("/admin/media/upload").file(file))
                .andExpect(status().isUnauthorized());
    }
}
