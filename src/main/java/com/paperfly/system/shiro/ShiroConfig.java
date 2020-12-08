package com.paperfly.system.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.validation.Validator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){

        return new ShiroDialect();

    }
    @Bean//防止记住我功能，因为重启解密失败
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200000);
        cookieRememberMeManager.setCookie(simpleCookie);
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;
    }
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    @Bean
    public DefaultWebSecurityManager getManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean//这个是Shiro的过滤工厂
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("getManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理
        shiroFilterFactoryBean.setSecurityManager(manager);
        //设置登录失败的url登录失败后会重定向到这个路径
        shiroFilterFactoryBean.setLoginUrl("/");

        //设置未授权请求的url Authorization
        shiroFilterFactoryBean.setUnauthorizedUrl("error/unauthorized");
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/static/**","anon");//释放静态资源可以访问
        filterMap.put("/sign-up", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/index", "anon");
        filterMap.put("/index.html", "anon");
        filterMap.put("/regist", "anon");
        filterMap.put("/logout", "logout");//访问  /logout  这个路径，就代表退出
        filterMap.put("/login","anon");
        filterMap.put("/send","anon");
        filterMap.put("/**","authc");//设置所有访问都不行
        //访问这个路径就是退出
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean//开启shiro注解功能
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean//开启shiro注解功能
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean//这个是thymeleaf实现的
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        /*未授权处理页thymeleaf会解析*/
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/error/unauthorized");
        /*身份没有验证*/
        //设置为登录时候访问失败的页面，thymeleaf会解析
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/sign");
        resolver.setExceptionMappings(properties);
        return resolver;
    }


}
