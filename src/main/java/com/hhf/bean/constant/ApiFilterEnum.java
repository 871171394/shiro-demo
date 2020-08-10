package com.hhf.bean.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 16:16
 */
public enum ApiFilterEnum implements IEnum<Integer> {
    /**请求过滤法分类*/
    ANON(0, "anon","可匿名访问"),
    AUTHC(1, "authc", "认证(登录)后可访问"),
    PERMS(2, "perms", "权限访问");

    public final Integer label;
    public final String val;
    public final String desc;

    ApiFilterEnum(Integer label, String val, String desc){
        this.label = label;
        this.val = val;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.label;
    }
}
