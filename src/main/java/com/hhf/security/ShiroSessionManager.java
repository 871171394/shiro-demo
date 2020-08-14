package com.hhf.security;

import com.hhf.security.constant.Shiro;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/14 17:51
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 先从header中获取token
        String accessToken = WebUtils.toHttp(request).getHeader(Shiro.AUTHORIZATION);
        if (StringUtils.hasText(accessToken)){
            // 从httpHeader中获得凭证
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, accessToken);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return accessToken;
        }
        // 若取不到则尝试默认支持方式获取
        return super.getSessionId(request,response);
    }
}
