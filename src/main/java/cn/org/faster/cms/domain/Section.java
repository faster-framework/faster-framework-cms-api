package cn.org.faster.cms.domain;

import cn.org.faster.cms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 栏目（树形）。
 */
@Getter
@Setter
@TableName("tb_section")
public class Section extends BaseEntity {

    public static final long ROOT_ID = 0L;
    public static final String ROOT_PATH = "[0]";
    public static final int TEMPLATE_NORMAL = 1;
    public static final int TEMPLATE_PAGE = 2;

    private String name;
    private String code;
    private String description;
    private String img;
    private Long parentId;

    /** 祖先链，形如 {@code [0],[123]} */
    private String parentIds;

    /** 1 普通页 / 2 分页 */
    private Integer templateType;

    private String templatePath;
    private String link;
    private Integer publishStatus;
    private Integer showStatus;
    private Integer topStatus;
    private String articleTemplatePath;
    private String content;
    private String resourceUrl;

    @TableField(exist = false)
    private List<Section> children;

    @JsonIgnore
    public boolean isPaged() {
        return Integer.valueOf(TEMPLATE_PAGE).equals(templateType);
    }

    public boolean published() {
        return Integer.valueOf(1).equals(publishStatus);
    }
}
