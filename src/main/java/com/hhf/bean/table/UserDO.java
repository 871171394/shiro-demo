package com.hhf.bean.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 14:17
 */
@Data
@TableName("user")
public class UserDO {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

}
