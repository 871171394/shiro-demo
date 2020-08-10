package com.hhf.handler;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/10 14:51
 */
public class GlobalExceptionHandler {
    /**
     * 账号不存在
     * @return
     */
    @ExceptionHandler(UnknownAccountException.class)
    public ResponseEntity unknownAccountException() {
        return ResponseEntity.status(HttpStatus.FOUND).body("账号不存在");
    }

    /**
     * 认证失败
     * 账号锁
     */
    @ExceptionHandler(LockedAccountException.class)
    public ResponseEntity lockedAccountException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("账号已经被锁定不能登录");
    }

    /**
     * 登录限制
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity disabledAccountException() {
        return ResponseEntity.status(HttpStatus.FOUND).body("账号已禁用");
    }

    /**
     * 登录重试限制
     */
    @ExceptionHandler(ExcessiveAttemptsException.class)
    public ResponseEntity excessiveAttemptsException(ExcessiveAttemptsException e) {
        return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
    }

    /**
     *  用户名密码错误
     * @return
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity incorrectCredentialsException() {
        return ResponseEntity.status(HttpStatus.FOUND).body("用户名或密码错误");
    }


}
