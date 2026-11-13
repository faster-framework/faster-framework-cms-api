package cn.org.faster.cms.common;

/**
 * 模板名转换。
 *
 * <p>数据库中的 {@code template_path} 允许带或不带 {@code .ftl} 后缀：
 * <ul>
 *   <li>Spring MVC 视图名需要去掉后缀（由 {@code spring.freemarker.suffix} 统一补齐）；</li>
 *   <li>FreeMarker {@code Configuration#getTemplate} 需要带后缀。</li>
 * </ul>
 * 两种写法都能兼容历史数据。</p>
 */
public final class TemplateNames {

    private static final String SUFFIX = ".ftl";

    private TemplateNames() {
    }

    /**
     * 转为 Spring MVC 视图名。
     */
    public static String viewName(String path) {
        String name = sanitize(path);
        return name.endsWith(SUFFIX) ? name.substring(0, name.length() - SUFFIX.length()) : name;
    }

    /**
     * 转为 FreeMarker 模板名。
     */
    public static String templateName(String path) {
        String name = sanitize(path);
        return name.endsWith(SUFFIX) ? name : name + SUFFIX;
    }

    private static String sanitize(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("模板路径不能为空");
        }
        String name = path.replace('\\', '/').replaceFirst("^/+", "");
        if (name.contains("..")) {
            throw new IllegalArgumentException("非法的模板路径: " + path);
        }
        return name;
    }
}
