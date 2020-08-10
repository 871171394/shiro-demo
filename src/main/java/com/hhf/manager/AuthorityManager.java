package com.hhf.manager;

import com.hhf.bean.table.ApiDO;
import com.hhf.bean.table.UserDO;
import com.hhf.bean.table.UserRoleDO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 15:44
 */
@Component
@AllArgsConstructor
public class AuthorityManager {
    private final UserManger userManger;
    private final UserRoleManger userRoleManger;

    public List<ApiDO> get(String username) {
        UserDO userDO = userManger.get(username);
        List<ApiDO> list = new ArrayList<>();
        userRoleManger.query(userDO.getId()).stream().map(UserRoleDO::getRoleId)
                .forEach(roleId->{

                });
    return null;
    }
}
