package com.hhf.security.filter.mgt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Iterator;

/**
 * @author Cheng.Wei
 * @date 2020-03-23 02:04
 */
@Slf4j
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    public static final int LENGTH_LIMIT = 2;
    public static final String API_SEPARATOR= "==";
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        } else {
            String requestUrl = this.getPathWithinApplication(request);
            Iterator var6 = filterChainManager.getChainNames().iterator();
            String pathPattern;
            boolean flag = true;
            String[] strings = null;
            do {
                if (!var6.hasNext()) {
                    return null;
                }

                pathPattern = (String)var6.next();

                strings = pathPattern.split(API_SEPARATOR);
                if (strings.length == LENGTH_LIMIT) {
                    // 分割出url+httpMethod,判断httpMethod和request请求的method是否一致,不一致直接false
                    if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(strings[1].toUpperCase())) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                } else {
                    flag = false;
                }
                pathPattern = strings[0];
            } while(!this.pathMatches(pathPattern, requestUrl) || flag);

            if (log.isTraceEnabled()) {
                log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestUrl + "].  Utilizing corresponding filter chain...");
            }
            if (strings.length == LENGTH_LIMIT) {
                pathPattern = pathPattern.concat("==").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
            }
            return filterChainManager.proxy(originalChain, pathPattern);
        }
    }
}
