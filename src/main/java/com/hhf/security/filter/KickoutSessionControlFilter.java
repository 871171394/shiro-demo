package com.hhf.security.filter;

import com.hhf.security.constant.Shiro;
import com.hhf.security.filter.mgt.RestPathMatchingFilterChainResolver;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/14 17:08
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    /**
     * 是否允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }
        // 如果登录了则执行
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getRequestURI().startsWith(Shiro.API_LOGOUT)) {
            // 如果是注销则继续执行
            Deque<Serializable> deque = cache.get(username);
            deque.remove(sessionId);
            if (deque.isEmpty()) {
                cache.remove(username);
            } else {
                cache.put(username, deque);
            }
            return true;
        }
        Deque<Serializable> deque = Optional.ofNullable(cache.get(username)).orElseGet(LinkedList::new);
        if (deque.isEmpty()) {
            cache.put(username, deque);
        }
        //如果队列里没有此sessionId ，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute(Shiro.KICKOUT_TAG) == null) {
            deque.push(sessionId);
        }
        // 如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            // 会话数量判断
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                // 禁止重复登录
                kickoutSessionId = deque.removeFirst();
            } else {
                // 重新登录，移除之前的登录信息
                kickoutSessionId = deque.removeLast();
            }
            Session kickOutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
            if (kickOutSession != null) {
                kickOutSession.setAttribute(Shiro.KICKOUT_TAG, true);
            }
        }
        return !(boolean) Optional.ofNullable(session.getAttribute(Shiro.KICKOUT_TAG)).orElse(false);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        subject.logout();
        WebUtils.getSavedRequest(request);
        saveRequest(request);
        HttpServletResponse resp = (HttpServletResponse) response;
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setStatus(302);
        response.getWriter().print("账号已从其他设备登录!");
        return Boolean.FALSE;
    }

    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestUrl = this.getPathWithinApplication(request);

        String[] strings = path.split(RestPathMatchingFilterChainResolver.API_SEPARATOR);

        if (strings.length <= 1) {
            // 普通的 URL, 正常处理
            return this.pathsMatch(strings[0], requestUrl);
        } else {
            // 获取当前请求的 http method.
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();

            // 匹配当前请求的 http method 与 过滤器链中的的是否一致
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestUrl);
        }
    }
}
