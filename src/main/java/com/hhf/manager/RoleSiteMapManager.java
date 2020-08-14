package com.hhf.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.RoleSiteMapDO;
import com.hhf.dao.RoleSiteMapMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/11 9:42
 */
@Component
@AllArgsConstructor
public class RoleSiteMapManager {
    private final RoleSiteMapMapper roleSiteMapMapper;

    public List<RoleSiteMapDO> query(long roleId) {
        QueryWrapper<RoleSiteMapDO> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        return roleSiteMapMapper.selectList(qw);
    }
}
