package com.hhf.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.UserRoleDO;
import com.hhf.dao.UserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 17:32
 */
@Component
@AllArgsConstructor
public class UserRoleManger {
    private final UserRoleMapper userRoleMapper;

    public List<UserRoleDO> query(long id){
        QueryWrapper<UserRoleDO> qw=new QueryWrapper<>();
        qw.eq("user_id",id);
       return userRoleMapper.selectList(qw);
    }


}
