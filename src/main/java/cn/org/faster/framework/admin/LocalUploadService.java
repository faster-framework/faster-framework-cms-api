package cn.org.faster.framework.admin;

import cn.org.faster.framework.core.utils.Utils;
import cn.org.faster.framework.web.exception.TokenValidException;
import cn.org.faster.framework.web.upload.error.UploadError;
import cn.org.faster.framework.web.upload.model.UploadRequest;
import cn.org.faster.framework.web.upload.model.UploadSuccess;
import cn.org.faster.framework.web.upload.model.UploadToken;
import cn.org.faster.framework.web.upload.service.IUploadService;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * @author zhangbowen
 */
public class LocalUploadService extends IUploadService {
    private String fileDir;
    private String urlPrefix;
    private String secretKey;

    public LocalUploadService(String fileDir, String urlPrefix, String secretKey) {
        this.fileDir = fileDir;
        this.urlPrefix = urlPrefix;
        this.secretKey = secretKey;
    }

    @Override
    public UploadToken preload(UploadRequest uploadRequest) {
        long timestamp = System.currentTimeMillis();
        uploadRequest.setTimestamp(timestamp);
        return new UploadToken(Utils.signWithSort(uploadRequest, secretKey), timestamp);
    }

    /**
     * 验证签名
     *
     * @param uploadRequest 上传请求
     * @param token         token
     */
    private void signValid(UploadRequest uploadRequest, String token) {
        //如果token为null，不验证签名。
        if (token == null) {
            return;
        }
        //如果上传token超出30分钟，则认为token失效，需重新获取。
        if (System.currentTimeMillis() - uploadRequest.getTimestamp() > 1000 * 60 * 30) {
            throw new TokenValidException(UploadError.SIGN_TIME_OUT);
        }
        //验证签名
        boolean success = Utils.signWithSort(uploadRequest, secretKey).equals(token);
        if (!success) {
            throw new TokenValidException(UploadError.SIGN_ERROR);
        }
    }

    /**
     * 验证父目录是否存在
     */
    private boolean parentDirValid(File file) {
        if (file.getParentFile().exists()) {
            return true;
        } else {
            return file.getParentFile().mkdirs();
        }
    }

    private UploadSuccess uploadSuccess(String fileName) {
        UploadSuccess uploadSuccess = new UploadSuccess();
        uploadSuccess.setUrl(urlPrefix + "/media/" + fileName);
        return uploadSuccess;
    }

    /**
     * 根据上传参数获取文件名称
     *
     * @param uploadRequest 上传参数
     * @return 文件名
     */
    protected String getFileName(UploadRequest uploadRequest, MultipartFile multipartFile) {
        String fileName = StringUtils.isEmpty(uploadRequest.getFileName()) ? LocalDateTime.now().format(FILE_NAME_FORMATTER) + Utils.fileSuffixWithPoint(multipartFile.getOriginalFilename()) : uploadRequest.getFileName();
        //如果不覆盖,在文件后增加时间后缀
        if (uploadRequest.getIsCover() == 0) {
            fileName = fileName.concat(LocalDateTime.now().format(FILE_NAME_FORMATTER));
        }
        return fileName;
    }

    @Override
    public UploadSuccess upload(MultipartFile multipartFile, UploadRequest uploadRequest, String token) throws IOException {
        //验证签名
        signValid(uploadRequest, token);
        String fileName = getFileName(uploadRequest, multipartFile);
        File file = new File(fileDir, fileName);
        //验证父目录是否存在
        if (!parentDirValid(file)) {
            return new UploadSuccess();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }
        return uploadSuccess(fileName);
    }

    @Override
    public UploadSuccess upload(InputStream uploadStream, UploadRequest uploadRequest, String token) throws IOException {
        //验证签名
        signValid(uploadRequest, token);
        String fileName = getFileName(uploadRequest);
        File file = new File(fileDir, fileName);
        //验证父目录是否存在
        if (!parentDirValid(file)) {
            return new UploadSuccess();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(StreamUtils.copyToByteArray(uploadStream));
        }
        return uploadSuccess(fileName);
    }

    @Override
    public UploadSuccess upload(byte[] uploadByte, UploadRequest uploadRequest, String token) throws IOException {
        //验证签名
        signValid(uploadRequest, token);
        String fileName = getFileName(uploadRequest);
        File file = new File(fileDir, fileName);
        //验证父目录是否存在
        if (!parentDirValid(file)) {
            return new UploadSuccess();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(uploadByte);
        }
        return uploadSuccess(fileName);
    }

    @Override
    public byte[] files(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(fileDir, fileName));
        } catch (IOException e) {
            return new byte[]{};
        }
    }
}
