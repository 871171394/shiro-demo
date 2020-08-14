package com.hhf.manager;

import com.hhf.bean.constant.ApiFilterEnum;
import com.hhf.bean.table.ApiDO;
import com.hhf.dao.ApiMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/12 16:24
 */
@Component
@AllArgsConstructor
public class ApiManager {
    private final ApiMapper mapper;

    public Map<String,String> load(){
        List<ApiDO> apis = mapper.selectList(null);
        Map<String, String> map = new LinkedHashMap<>();
        //可匿名访问
        apis.stream().filter(f->ApiFilterEnum.ANON.equals(f.getFilter())).
                forEach(api->map.put(api.getUri().replaceAll("\\{[^}]*\\}","*")+"==" + api.getMethod(),api.getFilter().val));

        //认证后访问
        apis.stream().filter(f->ApiFilterEnum.AUTHC.equals(f.getFilter())).
                forEach(api->map.put(api.getUri().replaceAll("\\{[^}]*\\}","*")+"==" + api.getMethod(),api.getFilter().val));
        //权限访问
        apis.stream().filter(x->ApiFilterEnum.PERMS.equals(x.getFilter()))
                .forEach(api->
                        map.put(api.getUri().replaceAll("\\{[^}]*\\}","*")+ "=="+ api.getMethod(), "authc,perms,kickout[" +api.getUri().replaceAll("\\{[^}]*\\}","*")+ "=="+ api.getMethod() + "]"));
        return map;
    }
}
