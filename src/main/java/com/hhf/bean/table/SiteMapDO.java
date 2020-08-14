package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/11 10:26
 */
@Data
@TableName("site_map")
public class SiteMapDO {
    /**Pk */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**上级节点id */
    private Long pid;
    /**名称 */
    private String name;
    /**字体颜色 */
    private String textColor;
    /**背景色 */
    private String backgroundColor;
    /**是否隐藏 */
    @TableField(value = "is_hide")
    private Boolean hide;
    /**图标 */
    private String icon;
    /**地址 */
    private String uri;
    /**是否锁 */
    @TableField(value = "is_locked")
    private Boolean locked;
    /**排序 */
    private Integer sort;
}
