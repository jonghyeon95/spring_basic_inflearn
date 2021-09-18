package com.cos.loginspringsecurity.controller;

import com.cos.loginspringsecurity.domain.User;
import com.cos.loginspringsecurity.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public User login(@ModelAttribute User user){

        return userService.login(user);
    }

    @PostMapping("add")
    public User add(@ModelAttribute User user){
        log.info("user = {}", user);
        userService.add(user);
        return user;
    }


}
