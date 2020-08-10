package com.hhf.security.config;

import com.hhf.security.filter.RetryLimitHashedCredentialsMatcher;
import com.hhf.security.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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


    /**
     * 创建 ShiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager/*,
                                                            @Qualifier("apiManager") ApiManager apiManager*/) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        return shiroFilterFactoryBean;
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
        return realm;
    }
}
