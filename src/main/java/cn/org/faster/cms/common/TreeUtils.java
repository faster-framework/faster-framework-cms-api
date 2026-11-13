package cn.org.faster.cms.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 平铺列表转树。
 */
public final class TreeUtils {

    private TreeUtils() {
    }

    /**
     * @param nodes          平铺节点（需已按期望顺序排列）
     * @param idGetter       主键读取器
     * @param parentIdGetter 父键读取器
     * @param childrenSetter 子节点写回器
     * @return 根节点列表
     */
    public static <T, I> List<T> build(List<T> nodes,
                                       Function<T, I> idGetter,
                                       Function<T, I> parentIdGetter,
                                       BiConsumer<T, List<T>> childrenSetter) {
        Map<I, T> byId = new LinkedHashMap<>();
        nodes.forEach(node -> byId.put(idGetter.apply(node), node));

        Map<I, List<T>> childrenByParent = new LinkedHashMap<>();
        List<T> roots = new ArrayList<>();
        for (T node : nodes) {
            I parentId = parentIdGetter.apply(node);
            T parent = parentId == null ? null : byId.get(parentId);
            if (parent == null || parent == node) {
                roots.add(node);
            } else {
                childrenByParent.computeIfAbsent(parentId, key -> new ArrayList<>()).add(node);
            }
        }
        childrenByParent.forEach((parentId, children) ->
                childrenSetter.accept(byId.get(parentId), List.copyOf(children)));
        return roots;
    }
}
