package cn.org.faster.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.util.unit.DataSize;

import java.nio.file.Path;
import java.util.List;

/**
 * 文件上传配置。
 *
 * @param dir            本地存储根目录
 * @param urlPrefix      对外访问前缀，例如 {@code /media}
 * @param maxSize        单文件大小上限
 * @param allowedTypes   允许的扩展名（小写，不含点）
 */
@ConfigurationProperties(prefix = "app.upload")
public record UploadProperties(
        @DefaultValue("data/upload") Path dir,
        @DefaultValue("/media") String urlPrefix,
        @DefaultValue("10MB") DataSize maxSize,
        @DefaultValue({"png", "jpg", "jpeg", "gif", "webp", "pdf", "zip"}) List<String> allowedTypes
) {

    public Path dir() {
        return dir.toAbsolutePath().normalize();
    }
}
