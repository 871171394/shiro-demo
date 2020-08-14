package com.hhf.security.filter;

import com.hhf.security.filter.mgt.RestPathMatchingFilterChainResolver;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/14 14:08
 */
@Component
public class RequestPermissionsFilter extends PermissionsAuthorizationFilter {
    /**
     * 没有登录的情况下会走此方法
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        // 设置制服编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;
        // 设置跨域
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        // 客户端允许携带验证信息头
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(401);
        response.getWriter().print("无权限!");
        return false;
    }

    /**
     * 将当前请求的 url 与所有配置的 perms 过滤器链进行匹配，是则进行权限检查，不是则接着与下一个过滤器链进行匹配，源码如下：
     * @param path
     * @param request
     * @return
     */
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        // 当前请求地址
        String requestUrl = this.getPathWithinApplication(request);
        String[] strings = requestUrl.split(RestPathMatchingFilterChainResolver.API_SEPARATOR);
        if (strings.length<=1){
            // 普通的URL ，正常处理
            return this.pathsMatch(strings[0],requestUrl);
        } else {
            // 获取当前请求的 http method
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();

            // 匹配当前请求的 http method 与过滤器链中的是否一致
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestUrl);
        }

    }
}
