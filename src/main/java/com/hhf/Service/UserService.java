package com.hhf.Service;

import com.hhf.bean.dto.UserDTO;
import com.hhf.bean.vo.UserVO;

import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 14:34
 */
public interface UserService {
    /**
     * 注册用户
     * @param user 用户信息
     */
    void add (UserDTO user);

    /**
     * 注销用户
     * @param id 用户id
     */
    void delete(Long id);

    /**
     * 查询所有用户
     * @return  用户信息
     */
    List<UserVO> get();

    /**
     * 修改用户信息
     * @param id 用户id
     * @param userDTO 用户信息
     */
    void update(long id,UserDTO userDTO);


}
