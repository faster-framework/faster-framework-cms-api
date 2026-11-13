package cn.org.faster.cms.service;

import cn.org.faster.cms.common.BizException;
import cn.org.faster.cms.common.ErrorCode;
import cn.org.faster.cms.config.CmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 静态站点文件访问：把 URL 映射为磁盘文件，并提供页面失效能力。
 *
 * <p>所有路径都会先 {@code normalize} 再校验是否仍位于站点根目录内，避免路径穿越。</p>
 */
@Slf4j
@Service
public class StaticSiteService {

    private final Path root;

    public StaticSiteService(CmsProperties properties) {
        this.root = properties.siteDir();
    }

    public Path root() {
        return root;
    }

    /**
     * 将请求 URI 解析为已存在的静态页文件。
     *
     * @param uri 形如 {@code /}、{@code /s/news}、{@code /s/news/2}、{@code /a/42}
     */
    public Optional<Path> resolveUri(String uri) {
        if (uri == null || uri.isBlank() || uri.contains("..")) {
            return Optional.empty();
        }
        String relative = uri.equals("/") ? "index" : uri.replaceFirst("^/+", "");
        Path target = resolve(relative + ".html");
        return Files.isRegularFile(target) ? Optional.of(target) : Optional.empty();
    }

    /**
     * 解析站点内相对路径，越界时抛出异常。
     */
    public Path resolve(String relative) {
        Path target = root.resolve(relative).normalize();
        if (!target.startsWith(root)) {
            throw new IllegalArgumentException("Illegal static path: " + relative);
        }
        return target;
    }

    /**
     * 删除文章静态页。
     */
    public void deleteArticlePage(long articleId) {
        deleteQuietly(resolve("a/" + articleId + ".html"));
    }

    /**
     * 删除栏目静态页（含分页目录）。
     */
    public void deleteSectionPages(String code) {
        if (code == null || code.isBlank()) {
            return;
        }
        deleteQuietly(resolve("s/" + code + ".html"));
        deleteTree(resolve("s/" + code));
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ex) {
            log.warn("Failed to delete static file {}", path, ex);
        }
    }

    private void deleteTree(Path dir) {
        if (!Files.isDirectory(dir)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(dir)) {
            paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
        } catch (IOException ex) {
            log.warn("Failed to delete static directory {}", dir, ex);
        }
    }
}
