package cn.org.faster.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.nio.file.Path;

/**
 * CMS 站点配置。
 *
 * @param siteDir       静态页输出目录
 * @param pageSize      栏目分页默认每页条数
 * @param contextPrefix 模板中数据模型的根变量名
 * @param listMax       模板标签 {@code list=true} 时单次最大返回条数
 */
@ConfigurationProperties(prefix = "app.cms")
public record CmsProperties(
        @DefaultValue("site") Path siteDir,
        @DefaultValue("20") int pageSize,
        @DefaultValue("cms") String contextPrefix,
        @DefaultValue("500") int listMax
) {

    public Path siteDir() {
        return siteDir.toAbsolutePath().normalize();
    }
}
