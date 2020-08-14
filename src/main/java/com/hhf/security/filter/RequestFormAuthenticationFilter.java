package com.hhf.security.filter;

import com.hhf.security.filter.mgt.RestPathMatchingFilterChainResolver;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/14 16:21
 */
@Component
public class RequestFormAuthenticationFilter  extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print("请登录后重试");
        ((HttpServletResponse)response).setStatus(302);
        return false;
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
