package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 17:05
 */
@Data
@TableName("user_role")
public class UserRoleDO {
    /**pk */
    private Long id;
    /**用户id */
    private Long userId;
    /**角色id */
    private Long roleId;
}
