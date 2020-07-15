package com.hhf.controller;

import com.hhf.bean.dto.LoginDTO;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 17:27
 */
@RestController
@AllArgsConstructor
public class LoginController {

    private HttpSession sessionon;

    /**
     * 登录
     *
     * @param login 用户名、密码
     * @return token
     */
    @PostMapping("/login")
    public ResponseEntity login(LoginDTO login) {
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), login.getPassword(), false);
        SecurityUtils.getSubject().login(token);
        Object attribute = sessionon.getAttribute(SecurityUtils.getSubject().getSession().getId().toString());


        return ResponseEntity.ok(SecurityUtils.getSubject().getSession().getId());
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
    }
}
