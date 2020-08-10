package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hhf.bean.constant.ApiFilterEnum;
import com.hhf.bean.constant.HttpMethodEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 15:46
 */
@Data
@TableName("api")
public class ApiDO implements Serializable {
    private static final long serialVersionUID = -3012703728460441616L;
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**名称 */
    private String name;
    /**请求地址 */
    private String uri;
    /**请求方式 */
    @EnumValue
    private HttpMethodEnum method;
    /**是否过滤策略*/
    @EnumValue
    private ApiFilterEnum filter;
    /**状态: true-正常 false不开放/锁*/
    @TableField(value = "is_locked")
    private Boolean locked;
    /**描述信息*/
    private String description;
    /**创建时间*/
    private LocalDateTime gmtCreate;
    /**最近一次更新时间*/
    private LocalDateTime gmtModified;
}
