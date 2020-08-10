package com.hhf;

import com.hhf.bean.constant.ApiFilterEnum;
import com.hhf.bean.constant.HttpMethodEnum;
import com.hhf.bean.table.ApiDO;
import com.hhf.dao.ApiMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroDemoApplicationTests {
    @Autowired
    private ApiMapper apiMapper;
    @Test
    public void contextLoads() {
        String url="/login?password=1234567&username=zhangsan";
        String s = url.replaceAll("\\{[^}]*\\}", "*");
        System.out.println(url);
        System.out.println(s);
    }

    @Test
    public void test1() {
        ApiDO apiDO=new ApiDO();
        apiDO.setName("登录");
        apiDO.setUri("/login");
        apiDO.setMethod(HttpMethodEnum.GET);
        apiDO.setFilter(ApiFilterEnum.ANON);
        apiDO.setLocked(true);
        apiDO.setGmtCreate(LocalDateTime.now());
        apiDO.setGmtModified(LocalDateTime.now());
        apiMapper.insert(apiDO);
    }

    @Test
    public void test2() {
        List<ApiDO> apiDOS = apiMapper.selectList(null);
        System.out.println(apiDOS);
    }

}
