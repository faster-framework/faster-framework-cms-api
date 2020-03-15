package cn.org.faster.cms.admin.site.strategy.impl;

import cn.org.faster.cms.admin.site.strategy.BaseStrategy;
import cn.org.faster.cms.common.utils.CmsUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author zhangbowen
 * @since 2019-12-13
 */
@Service
public class IndexStrategy extends BaseStrategy {
    @Override
    public void execute() throws IOException, TemplateException {
        Template template = configuration.getTemplate("index.ftl");
        CmsUtils.generateHtml(template, super.cmsProperties.getSiteDir() + "/index.html", null);
    }
}
