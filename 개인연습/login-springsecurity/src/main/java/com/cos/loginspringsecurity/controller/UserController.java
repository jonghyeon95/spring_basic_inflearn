package com.cos.loginspringsecurity.controller;

import com.cos.loginspringsecurity.domain.User;
import com.cos.loginspringsecurity.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.expr.Instanceof;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

//    @PostMapping("login")
//    public User login(@ModelAttribute User user){
//
//        return userService.login(user);
//    }

    @PostMapping("add")
    public User add(@ModelAttribute User user){
        log.info("Controller시작");
//        log.info("user = {}", user);
        userService.add(user);
        return user;
    }

    @GetMapping("/home")
    public Object loginSuc(Authentication authentication, Principal principal){


        if(principal == null){
            log.info("principal NULL");
            return null;
        }


        log.info("principal ={}", principal);
        return principal;

//        log.info("authentication.getPrincipal ={}",authentication.getPrincipal());
//        return authentication.getPrincipal();
    }
}
