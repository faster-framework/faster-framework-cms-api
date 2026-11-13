package cn.org.faster.cms.config;

import cn.org.faster.cms.directive.BaseDirective;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 把容器中的所有 {@link BaseDirective} 按 Bean 名注册为 FreeMarker 共享变量。
 *
 * <p>新增标签只需实现 {@link BaseDirective} 并声明 Bean 名，例如 {@code @Component("video")}。</p>
 */
@Component
public class DirectiveRegistrar {

    private static final Logger log = LoggerFactory.getLogger(DirectiveRegistrar.class);

    private final ApplicationContext applicationContext;
    private final Configuration configuration;

    public DirectiveRegistrar(ApplicationContext applicationContext, Configuration configuration) {
        this.applicationContext = applicationContext;
        this.configuration = configuration;
    }

    @PostConstruct
    public void register() throws TemplateModelException {
        var directives = applicationContext.getBeansOfType(BaseDirective.class);
        for (var entry : directives.entrySet()) {
            configuration.setSharedVariable(entry.getKey(), entry.getValue());
        }
        log.info("Registered FreeMarker directives: {}", directives.keySet());
    }
}
