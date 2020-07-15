package com.hhf.controller;

import com.hhf.Service.UserService;
import com.hhf.bean.dto.UserDTO;
import com.hhf.bean.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Huang.Hua.Fu
 * @date 2020/7/10 15:14
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity add(UserDTO dto){
        userService.add(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable("id") long id){
        userService.delete(id);
       return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity update(@PathVariable("id") long id,UserDTO dto){
        userService.update(id,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> get(){
        return ResponseEntity.ok(userService.get());
    }
}
