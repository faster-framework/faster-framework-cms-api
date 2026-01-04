package cn.org.faster.cms.common.directive.context;

import lombok.Builder;
import lombok.Data;

/**
 * 标签上下文
 *
 * @author zhangbowen
 * @since 2020/3/14
 */
@Data
@Builder
public class TagContext {
    /**
     * 列表页
     */
    private boolean list;
    /**
     * 是否分页
     */
    private boolean page;
    /**
     * 每页查询条数
     */
    private Integer size;
    /**
     * 页数
     */
    private Integer current;
    /**
     * 父id
     */
    private Long pid;
    /**
     * 编码
     */
    private String code;
    /**
     *
     */
    private Long id;
    /**
     * 是否展示
     */
    private boolean show;
    /**
     * 是否置顶
     */
    private Boolean top;
    /**
     * 别名
     */
    private String as;
    /**
     * 栏目编码
     */
    private String scode;
    /**
     * 栏目id
     */
    private Long sid;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 父编码
     */
    private String pcode;
    /**
     * 排除某个id
     */
    private Long excludeId;
}
