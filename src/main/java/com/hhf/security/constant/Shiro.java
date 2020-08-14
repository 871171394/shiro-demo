package com.hhf.security.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Cheng.Wei
 * @date 2018-11-28 19:26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Shiro {
    /**登出（销毁会话）*/
    public static final String API_LOGOUT = "/logout";
    /**会话并发队列键名称*/
    public static final String KICKOUT_TAG = "kickout";
    /**HTTP header 凭证*/
    public static final String AUTHORIZATION = "Authorization";
}
