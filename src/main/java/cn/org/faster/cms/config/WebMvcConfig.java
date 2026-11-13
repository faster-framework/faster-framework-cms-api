package cn.org.faster.cms.config;

import cn.org.faster.cms.web.StaticPageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置：静态页拦截、上传目录映射。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final StaticPageInterceptor staticPageInterceptor;
    private final UploadProperties uploadProperties;

    public WebMvcConfig(StaticPageInterceptor staticPageInterceptor,
                        UploadProperties uploadProperties) {
        this.staticPageInterceptor = staticPageInterceptor;
        this.uploadProperties = uploadProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(staticPageInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**", "/media/**", "/error", "/favicon.ico");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String pattern = uploadProperties.urlPrefix().replaceAll("/+$", "") + "/**";
        String location = uploadProperties.dir().toUri().toString();
        if (!location.endsWith("/")) {
            location = location + "/";
        }
        registry.addResourceHandler(pattern).addResourceLocations(location);
    }
}
