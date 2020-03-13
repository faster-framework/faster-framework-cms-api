package cn.org.faster.framework.admin.basic.controller;

import cn.hutool.core.net.URLEncoder;
import cn.org.faster.framework.web.exception.model.BasicErrorCode;
import cn.org.faster.framework.web.exception.model.ResponseErrorEntity;
import cn.org.faster.framework.web.upload.controller.AbstractUploadController;
import cn.org.faster.framework.web.upload.model.UploadRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping({"/admin", ""})
public class UploadController extends AbstractUploadController {
    /**
     * 上传文件
     *
     * @param uploadFile    文件
     * @param uploadRequest 上传请求
     * @param token         签名字符串
     * @return httpResponse
     */
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile uploadFile, UploadRequest uploadRequest, String token) {
        try {
            return ResponseEntity.ok(uploadService.upload(uploadFile, uploadRequest, token));
        } catch (IOException e) {
            return ResponseErrorEntity.error(BasicErrorCode.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 预览、下载上传的文件
     *
     * @param fileName 文件名称
     * @return 文件流
     */
    @GetMapping("/media/{fileName}")
    public ResponseEntity preview(@PathVariable String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        Charset charset = Charset.defaultCharset();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName, charset).build());
        return new ResponseEntity<>(uploadService.files(fileName), headers, HttpStatus.OK);
    }
}
