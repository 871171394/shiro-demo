package com.hhf.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.SiteMapApiDO;
import com.hhf.dao.SiteMapApiMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/12 11:36
 */
@Component
@AllArgsConstructor
public class SiteMapApiManager {
    private final SiteMapApiMapper mapper;
    public List<SiteMapApiDO> query(long siteMapId){
        QueryWrapper<SiteMapApiDO> qw=new QueryWrapper<>();
        qw.eq("site_map_id",siteMapId);
       return   mapper.selectList(qw);
    }
}
