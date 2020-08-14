package com.hhf.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Huang.Hua.Fu
 * @date 2020/8/12 14:15
 */
@RestController
@AllArgsConstructor
public class AdminController {

    @PostMapping("/admin/{id}")
    public ResponseEntity login(@PathVariable long id) {
        return ResponseEntity.ok("id为:"+id);
    }

}
