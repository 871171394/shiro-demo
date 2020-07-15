package com.hhf.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.Service.UserService;
import com.hhf.bean.table.UserDO;
import com.hhf.bean.dto.UserDTO;
import com.hhf.bean.vo.UserVO;
import com.hhf.dao.UserMapper;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 14:39
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public void add(UserDTO dto) {
        QueryWrapper<UserDO> sql=new QueryWrapper<>();
        sql.eq("username",dto.getUsername());
        List<UserDO> userDOS = userMapper.selectList(sql);
        if (!userDOS.isEmpty()){
            throw new RuntimeException("帐号已存在!");
        }
        UserDO userDO = new UserDO();
        userDO.setName(dto.getName());
        userDO.setUsername(dto.getUsername());
        userDO.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));

        userMapper.insert(userDO);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);

    }

    @Override
    public List<UserVO> get() {
        List<UserDO> userDOS = userMapper.selectList(null);
        return userDOS.stream().map(u -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(u, userVO);
            return userVO;
        }).collect(Collectors.toList());
    }

    @Override
    public void update(long id, UserDTO dto) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(dto, userDO);
        userDO.setId(id);
        userMapper.updateById(userDO);
    }

}
