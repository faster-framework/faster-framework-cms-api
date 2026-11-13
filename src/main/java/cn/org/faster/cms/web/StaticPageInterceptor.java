package cn.org.faster.cms.web;

import cn.org.faster.cms.service.StaticSiteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * 静态页拦截：URL 命中已生成的 HTML 时直接输出，跳过动态渲染。
 *
 * <p>只接受 GET/HEAD；路径经过 normalize 校验，禁止越出站点根目录。</p>
 */
@Component
public class StaticPageInterceptor implements HandlerInterceptor {

    private static final MediaType HTML_UTF8 = MediaType.parseMediaType("text/html;charset=UTF-8");

    private final StaticSiteService staticSite;

    public StaticPageInterceptor(StaticSiteService staticSite) {
        this.staticSite = staticSite;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        if (!HttpMethod.GET.matches(request.getMethod())
                && !HttpMethod.HEAD.matches(request.getMethod())) {
            return true;
        }
        Optional<Path> page = staticSite.resolveUri(request.getRequestURI());
        if (page.isEmpty()) {
            return true;
        }
        Path file = page.get();
        response.setContentType(HTML_UTF8.toString());
        response.setCharacterEncoding("UTF-8");
        response.setContentLengthLong(Files.size(file));
        response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=60");
        if (HttpMethod.HEAD.matches(request.getMethod())) {
            return false;
        }
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(file, out);
            out.flush();
        }
        return false;
    }
}
