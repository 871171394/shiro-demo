package com.hhf.bean.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 15:53
 */
public enum  HttpMethodEnum implements IEnum<Integer> {
    /**RequestMethod GET 请求*/
    GET(1, "GET"),
    /**RequestMethod POST 新增*/
    POST(2, "POST"),
    /**RequestMethod PUT更新*/
    PUT(3, "PUT"),
    /**RequestMethod DELETE 删除*/
    DELETE(4, "DELETE"),
    /**RequestMethod PATCH*/
    PATCH(5, "PATCH");
    HttpMethodEnum(int label, String desc){
        this.value = label;
        this.desc = desc;
    };
    public final int value;
    public final String desc;
    @Override
    public Integer getValue() {
        return this.value;
    }
}
