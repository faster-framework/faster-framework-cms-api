package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.config.UploadProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * 本地文件上传。
 *
 * <p>校验扩展名白名单与大小，落盘文件名随机化，避免覆盖与可执行文件风险。</p>
 */
@Service
public class UploadService {

    private static final DateTimeFormatter DATE_DIR = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final UploadProperties properties;

    public UploadService(UploadProperties properties) {
        this.properties = properties;
    }

    public record UploadResult(String url, String name, long size, String contentType) {
    }

    public UploadResult store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "上传文件为空");
        }
        if (file.getSize() > properties.maxSize().toBytes()) {
            throw BizException.of(ErrorCode.PAYLOAD_TOO_LARGE,
                    "文件超过 " + properties.maxSize().toMegabytes() + "MB");
        }
        String extension = extensionOf(file.getOriginalFilename());
        if (!properties.allowedTypes().contains(extension)) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "不支持的文件类型: " + extension);
        }
        String relative = LocalDate.now().format(DATE_DIR) + "/" + UUID.randomUUID() + "." + extension;
        Path target = properties.dir().resolve(relative).normalize();
        if (!target.startsWith(properties.dir())) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "非法的文件名");
        }
        try {
            Files.createDirectories(target.getParent());
            try (InputStream input = file.getInputStream()) {
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw BizException.of(ErrorCode.INTERNAL, "文件写入失败");
        }
        return new UploadResult(publicUrl(relative), displayName(file.getOriginalFilename()),
                file.getSize(), file.getContentType());
    }

    private String publicUrl(String relative) {
        return properties.urlPrefix().replaceAll("/+$", "") + "/" + relative;
    }

    private String displayName(String original) {
        if (!StringUtils.hasText(original)) {
            return "file";
        }
        String name = original.replace('\\', '/');
        int slash = name.lastIndexOf('/');
        return slash >= 0 ? name.substring(slash + 1) : name;
    }

    private String extensionOf(String filename) {
        String name = displayName(filename);
        int dot = name.lastIndexOf('.');
        if (dot < 0 || dot == name.length() - 1) {
            throw BizException.of(ErrorCode.BAD_REQUEST, "文件缺少扩展名");
        }
        return name.substring(dot + 1).toLowerCase(Locale.ROOT);
    }
}
