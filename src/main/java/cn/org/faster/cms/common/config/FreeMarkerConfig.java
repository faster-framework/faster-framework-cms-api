package cn.org.faster.cms.common.config;

import cn.org.faster.cms.common.directive.BaseDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangbowen
 * @since 2019-12-03
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    private WebApplicationContext applicationContext;

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        //注册所有自定义标签
        Map<String, BaseDirective> tagsMap = applicationContext.getBeansOfType(BaseDirective.class);
        Set<Map.Entry<String, BaseDirective>> entries = tagsMap.entrySet();
        entries.forEach(entry ->
                configuration.setSharedVariable(entry.getKey(), entry.getValue())
        );
    }
}
