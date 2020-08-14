package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/11 9:32
 */
@Data
@TableName("role_site_map")
public class RoleSiteMapDO {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**角色id */
    private Long roleId;
    /**地图id */
    private Long siteMapId;
}
