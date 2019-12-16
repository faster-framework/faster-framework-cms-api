package cn.org.faster.framework.admin;

import cn.org.faster.framework.shiro.ShiroFilter;
import cn.org.faster.framework.shiro.spring.boot.autoconfigure.ShiroProperties;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangbowen
 */
@Configuration
@ComponentScan
public class AdminScanConfiguration {
    private static final String adminPrefix = "/admin";

    /**
     * 过滤器
     *
     * @param securityManager 权限管理器
     * @param shiroProperties shiro配置
     * @return 过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroProperties shiroProperties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = shiroProperties.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put(adminPrefix + "/login", "anon");
        filterChainDefinitionMap.put(adminPrefix + "/media/**", "anon");
        filterChainDefinitionMap.put(adminPrefix + "/captcha/**", "anon");
        filterChainDefinitionMap.put(adminPrefix + "/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }
}