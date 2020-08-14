package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/11 10:35
 */
@Data
@TableName("site_map_api")
public class SiteMapApiDO {
    /**PK */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**地图id */
    private Long siteMapId;
    /**Api Id */
    private Long apiId;
}
