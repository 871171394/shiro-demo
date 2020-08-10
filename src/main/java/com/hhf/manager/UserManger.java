package com.hhf.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.UserDO;
import com.hhf.dao.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 15:39
 */
@Component
@AllArgsConstructor
public class UserManger {
    private final UserMapper userMapper;

    public UserDO get(String username) {
        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.eq(true, "username", username);
        List<UserDO> users = userMapper.selectList(qw);
        return users.isEmpty() ? null : users.get(0);
    }
}
