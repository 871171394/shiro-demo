package com.hhf.security.config;

import com.hhf.manager.ApiManager;
import com.hhf.security.filter.KickoutSessionControlFilter;
import com.hhf.security.filter.RequestFormAuthenticationFilter;
import com.hhf.security.filter.RequestPermissionsFilter;
import com.hhf.security.filter.RetryLimitHashedCredentialsMatcher;
import com.hhf.security.filter.mgt.RestShiroFilterFactoryBean;
import com.hhf.security.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 13:49
 */
@Configuration
public class ShiroConfig {

    /**
     * 自定义缓存功能
     *
     * @return
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        // 设置用于初始化包装的EhCache CacheManager实例的配置文件的资源位置。
        em.setCacheManagerConfigFile("classpath:ehcache.xml");
        return em;
    }

    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator uuid(){
        return new JavaUuidSessionIdGenerator();
    }
    @Bean(name = "sessionDAO")
    public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
        EnterpriseCacheSessionDAO dao=new EnterpriseCacheSessionDAO();
        dao.setActiveSessionsCacheName("shiro-activeSessionCache");
        dao.setSessionIdGenerator(sessionIdGenerator);
        return  dao;
    }



    /**
     * 创建 ShiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                            @Qualifier("apiManager") ApiManager apiManager,
                                                            @Qualifier("requestPermissionsFilter") RequestPermissionsFilter requestPermissionsFilter,
                                                            @Qualifier("requestFormAuthenticationFilter") RequestFormAuthenticationFilter requestFormAuthenticationFilter,
                                                            @Qualifier("kickoutSessionControlFilter") KickoutSessionControlFilter kickoutSessionControlFilter) {
        ShiroFilterFactoryBean factoryBean = new RestShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.putAll(apiManager.load());
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new LinkedHashMap();
        filters.put("authc",requestFormAuthenticationFilter);
        filters.put("kickout", kickoutSessionControlFilter);
        filters.put("perms", requestPermissionsFilter);
        factoryBean.setFilters(filters);
        return factoryBean;
    }

    @Bean("kickoutSessionControlFilter")
    public KickoutSessionControlFilter kickoutSessionControlFilter(SessionManager sessionManager){
        KickoutSessionControlFilter controlFilter = new KickoutSessionControlFilter();
        controlFilter.setCacheManager(getEhCacheManager());
        controlFilter.setSessionManager(sessionManager);
        controlFilter.setKickoutAfter(Boolean.FALSE);
        controlFilter.setMaxSession(1);
        return controlFilter;
    }


    /**
     * 凭证匹配器
     */
    @Bean(name = "credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(getEhCacheManager());
        matcher.setRetryLimit(5);
        return matcher;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联Realm
        securityManager.setRealm(userRealm);
        // 设置缓存
        securityManager.setCacheManager(getEhCacheManager());
        return securityManager;
    }

    @Bean(name = "getRealm")
    public UserRealm getRealm() {
        UserRealm realm = new UserRealm();
        // 设置凭证匹配器
        realm.setCredentialsMatcher(credentialsMatcher());
        // 启用缓存，默认false（若启用缓存可以提高性能，但在权限更变的时候需要同时变更缓存）
        realm.setCachingEnabled(true);
        // 身份验证缓存(但是不支持，开启后，同时登陆异常（数据不一致）)
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthenticationCacheName("authenticationCache");
        // 设置缓存
        realm.setCacheManager(getEhCacheManager());
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName("authorizationCache");
        realm.setCacheManager(getEhCacheManager());
        return realm;
    }

    /**
     * SpringBoot过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
}
