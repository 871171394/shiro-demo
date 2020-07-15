package com.hhf.security.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.UserDO;
import com.hhf.dao.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 15:59
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("进行授权逻辑");
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        Session session = SecurityUtils.getSubject().getSession();
        QueryWrapper<UserDO> qw = new QueryWrapper<>();
        qw.eq("username",token.getUsername());
        List<UserDO> users = Optional.ofNullable(userMapper.selectList(qw)).orElseThrow(UnknownAccountException::new);
        session.setAttribute("userSession",users.get(0));
        return new SimpleAuthenticationInfo(token.getCredentials(),users.get(0).getPassword(),getName());
    }
}
