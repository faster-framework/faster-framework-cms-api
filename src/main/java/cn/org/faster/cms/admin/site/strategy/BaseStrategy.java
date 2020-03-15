package cn.org.faster.cms.admin.site.strategy;

import cn.org.faster.cms.common.properties.CmsProperties;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
public abstract class BaseStrategy {
    @Autowired
    protected CmsProperties cmsProperties;
    @Autowired
    protected FreeMarkerConfigurer freeMarkerConfigurer;

    protected Configuration configuration;

    @PostConstruct
    protected void init(){
        configuration = freeMarkerConfigurer.getConfiguration();
    }


    /**
     * 开始生成逻辑
     */
    public void start() {
        try {
            execute();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行生成代码
     */
    protected abstract void execute() throws IOException, TemplateException;
}
