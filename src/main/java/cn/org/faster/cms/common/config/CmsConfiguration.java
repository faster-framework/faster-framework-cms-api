package cn.org.faster.cms.common.config;

import cn.org.faster.cms.common.properties.CmsProperties;
import cn.org.faster.cms.common.utils.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @author zhangbowen
 * @since 2019-12-06
 */
@Configuration
public class CmsConfiguration implements WebMvcConfigurer {
    @Autowired
    private CmsProperties cmsProperties;

    @Bean
    public StaticInterceptor staticInterceptor() {
        return new StaticInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(staticInterceptor()).addPathPatterns("/**");
    }


    @PostConstruct
    public void init() {
        //获取当前jar包或target所在位置
        if (StringUtils.isEmpty(cmsProperties.getSiteDir())) {
            cmsProperties.setSiteDir(CmsUtils.getJarRootPath() + "/site");
        }
    }

}
