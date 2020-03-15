package cn.org.faster.cms.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangbowen
 * @since 2019-12-06
 */
@ConfigurationProperties(prefix = "app.cms")
@Data
@Component
public class CmsProperties {

    /**
     * 静态网站目录（默认为当前jar包所在目录）
     */
    private String siteDir;

    /**
     * 默认分页数量
     */
    private Integer pageSize = 20;
    /**
     * 全局变量前缀
     */
    private String contextPrefix = "cms";
}
