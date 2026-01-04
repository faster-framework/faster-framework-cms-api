package cn.org.faster.cms.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;

import java.util.Date;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2020/3/13
 */
public class TemplateMapUtils extends MapUtil {
    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param <T>  目标值类型
     * @param map  Map
     * @param key  键
     * @param type 值类型
     * @return 值
     * @since 4.0.6
     */
    public static <T> T getOrDefault(Map<?, ?> map, Object key, Class<T> type, Object defaultValue) {
        return null == map ? Convert.convert(type, defaultValue) : Convert.convert(type, map.get(key) == null ? defaultValue : map.get(key));
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static String getStrOrDefault(Map<?, ?> map, Object key, String defaultValue) {
        return getOrDefault(map, key, String.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Integer getIntOrDefault(Map<?, ?> map, Object key, int defaultValue) {
        return getOrDefault(map, key, Integer.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Double getDoubleOrDefault(Map<?, ?> map, Object key, double defaultValue) {
        return getOrDefault(map, key, Double.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Float getFloatOrDefault(Map<?, ?> map, Object key, float defaultValue) {
        return getOrDefault(map, key, Float.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Short getShortOrDefault(Map<?, ?> map, Object key, short defaultValue) {
        return getOrDefault(map, key, Short.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Boolean getBoolOrDefault(Map<?, ?> map, Object key, boolean defaultValue) {
        return getOrDefault(map, key, Boolean.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Boolean getBoolOrDefaultByTemplate(Map<?, ?> map, Object key, Boolean defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        TemplateBooleanModel booleanModel = (TemplateBooleanModel) map.get(key);
        if (booleanModel == null) {
            return defaultValue;
        }
        try {
            return booleanModel.getAsBoolean();
        } catch (TemplateModelException e) {
            return defaultValue;
        }
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Character getCharOrDefault(Map<?, ?> map, Object key, char defaultValue) {
        return getOrDefault(map, key, Character.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.0.6
     */
    public static Long getLongOrDefault(Map<?, ?> map, Object key, Long defaultValue) {
        return getOrDefault(map, key, Long.class, defaultValue);
    }

    /**
     * 获取Map指定key的值，并转换为{@link Date}
     *
     * @param map Map
     * @param key 键
     * @return 值
     * @since 4.1.2
     */
    public static Date getDateOrDefault(Map<?, ?> map, Object key, Date defaultValue) {
        return getOrDefault(map, key, Date.class, defaultValue);
    }

}
