package com.hhf.security.filter;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/13 16:06
 */
@RestControllerAdvice
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private Cache<String, AtomicInteger> passwordRetryCache;
    /**
     * 登录限制次数 默认5次
     */
    private int retryLimit = 5;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 自定义认证
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        AtomicInteger retryCont = passwordRetryCache.get(username);
        // 如果缓存中没有则创建一个新的初始化为0并且存入缓存中
        if (retryCont == null) {
            retryCont = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCont);
        }
        // 如果重试次数大于等于重试限制就报出异常
        if (retryCont.incrementAndGet() >= retryLimit) {
            throw new ExcessiveAttemptsException("连续登陆失败已经达到" + retryLimit + "次，10分钟内禁止登陆");
        }
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 获取密码
        String plaintext = new String(upToken.getPassword());
        // 获取原始密码
        String hashed =info.getCredentials().toString();
        // 参数1.明文密码  2.加密密码
        boolean checkpw = BCrypt.checkpw(plaintext, hashed);
        if (checkpw) {
            // 登陆成功计数器清零
            passwordRetryCache.remove(username);
        }
        return checkpw;
    }

    public void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }
}
