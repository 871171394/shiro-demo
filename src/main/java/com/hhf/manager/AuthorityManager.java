package com.hhf.manager;

import com.hhf.bean.table.ApiDO;
import com.hhf.bean.table.RoleSiteMapDO;
import com.hhf.bean.table.SiteMapApiDO;
import com.hhf.bean.table.UserDO;
import com.hhf.bean.table.UserRoleDO;
import com.hhf.dao.ApiMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 15:44
 */
@Component
@AllArgsConstructor
public class AuthorityManager {
    private final UserManger userManger;
    private final UserRoleManger userRoleManger;
    private final RoleSiteMapManager roleSiteMapManager;
    private final SiteMapApiManager siteMapApiManager;
    private final ApiMapper apiMapper;

    public List<ApiDO> get(String username) {
        UserDO userDO = userManger.get(username);
        List<ApiDO> list = new ArrayList<>();
        userRoleManger.query(userDO.getId()).stream().map(UserRoleDO::getRoleId)
                .forEach(roleId ->
                        roleSiteMapManager.query(roleId).stream().map(RoleSiteMapDO::getSiteMapId)
                                .forEach(siteMapId ->
                                    siteMapApiManager.query(siteMapId).stream().map(SiteMapApiDO::getApiId)
                                            .map(apiMapper::selectById).filter(Objects::nonNull)
                                            .forEach(list::add)));
        return list;
    }
}
