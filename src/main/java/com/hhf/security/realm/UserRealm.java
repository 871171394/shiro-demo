package com.hhf.security.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhf.bean.table.UserDO;
import com.hhf.dao.UserMapper;
import com.hhf.manager.UserManger;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 15:59
 */
public class UserRealm extends AuthorizingRealm {
    @Resource private UserMapper userMapper;
    @Resource private UserManger manger;
    /**
     * 授权
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * 在配有缓存的情况下，只加载一次.
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权逻辑");
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo();

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
        session.setAttribute("userSession",users.isEmpty()?null:users.get(0));
        return new SimpleAuthenticationInfo(token.getCredentials(),users.get(0).getPassword(),getName());
    }
}
