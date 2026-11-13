package cn.org.faster.cms.directive;

import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

import java.util.Map;

/**
 * 模板标签参数容器。
 *
 * @param list      是否列表模式
 * @param page      是否分页模式
 * @param size      每页条数 / 列表上限
 * @param current   页码
 * @param id        主键
 * @param pid       栏目父 id
 * @param sid       栏目 id（文章标签）
 * @param excludeId 排除的 id
 * @param code      编码
 * @param scode     栏目编码（文章标签）
 * @param pcode     父栏目编码（栏目标签）
 * @param type      字典类型
 * @param name      字典名称
 * @param alias     输出变量名
 * @param show      是否只取展示中的数据
 * @param top       是否置顶
 */
public record TagContext(
        boolean list,
        boolean page,
        int size,
        int current,
        Long id,
        Long pid,
        Long sid,
        Long excludeId,
        String code,
        String scode,
        String pcode,
        String type,
        String name,
        String alias,
        boolean show,
        Boolean top
) {

    public static final String DEFAULT_ALIAS = "data";

    /**
     * 从标签参数与全局 {@code cms} 数据模型中解析上下文。
     *
     * <p>未显式指定 {@code current}/{@code size} 时，沿用全局分页信息，与页面模型保持一致。</p>
     */
    public static TagContext parse(Map<?, ?> params, Environment env) throws TemplateModelException {
        Arguments arguments = new Arguments(params);
        boolean list = arguments.bool("list", false);
        boolean page = arguments.bool("page", false);

        int defaultCurrent = -1;
        int defaultSize = -1;
        if (!list) {
            TemplateModel cms = cmsModel(env);
            if (cms instanceof SimpleHash hash) {
                defaultCurrent = Arguments.intValue(hash.get("current"), -1);
                defaultSize = Arguments.intValue(hash.get("size"), -1);
            }
        }

        String alias = arguments.string("alias", null);
        if (alias == null) {
            alias = arguments.string("as", DEFAULT_ALIAS);
        }

        return new TagContext(
                list,
                page,
                arguments.intValue("size", defaultSize),
                arguments.intValue("current", defaultCurrent),
                arguments.longValue("id"),
                arguments.longValue("pid"),
                arguments.longValue("sid"),
                arguments.longValue("exclude"),
                arguments.string("code", null),
                arguments.string("scode", null),
                arguments.string("pcode", null),
                arguments.string("type", null),
                arguments.string("name", null),
                alias,
                arguments.bool("show", true),
                arguments.boolOrNull("top")
        );
    }

    private static TemplateModel cmsModel(Environment env) {
        try {
            return env.getVariable("cms");
        } catch (TemplateModelException ex) {
            return null;
        }
    }

    /**
     * 标签参数读取与类型转换。
     */
    private static final class Arguments {

        private final Map<?, ?> params;

        private Arguments(Map<?, ?> params) {
            this.params = params;
        }

        private TemplateModel raw(String key) {
            return params.get(key) instanceof TemplateModel model ? model : null;
        }

        String string(String key, String defaultValue) throws TemplateModelException {
            return stringOrDefault(raw(key), defaultValue);
        }

        boolean bool(String key, boolean defaultValue) throws TemplateModelException {
            Boolean value = toBoolean(raw(key));
            return value == null ? defaultValue : value;
        }

        Boolean boolOrNull(String key) throws TemplateModelException {
            return toBoolean(raw(key));
        }

        int intValue(String key, int defaultValue) throws TemplateModelException {
            return intValue(raw(key), defaultValue);
        }

        Long longValue(String key) throws TemplateModelException {
            if (raw(key) == null) {
                return null;
            }
            String text = toText(raw(key));
            if (text == null || text.isBlank()) {
                return null;
            }
            try {
                return Long.valueOf(text.trim());
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        static String stringOrDefault(TemplateModel model, String defaultValue) throws TemplateModelException {
            String value = toText(model);
            return value == null || value.isBlank() ? defaultValue : value;
        }

        static String toText(TemplateModel model) throws TemplateModelException {
            if (model == null) {
                return null;
            }
            if (model instanceof TemplateScalarModel scalar) {
                return scalar.getAsString();
            }
            if (model instanceof TemplateNumberModel number) {
                return number.getAsNumber().toString();
            }
            return model.toString();
        }

        static Boolean toBoolean(TemplateModel model) throws TemplateModelException {
            if (model == null) {
                return null;
            }
            if (model instanceof TemplateBooleanModel booleanModel) {
                return booleanModel.getAsBoolean();
            }
            String text = toText(model);
            if (text == null || text.isBlank()) {
                return null;
            }
            return Boolean.valueOf(text.trim());
        }

        static int intValue(TemplateModel model, int defaultValue) throws TemplateModelException {
            if (model == null) {
                return defaultValue;
            }
            if (model instanceof TemplateNumberModel number) {
                return number.getAsNumber().intValue();
            }
            String text = toText(model);
            if (text == null || text.isBlank()) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }
    }
}
