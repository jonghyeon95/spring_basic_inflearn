package com.cos.loginspringsecurity.service;

import com.cos.loginspringsecurity.domain.User;
import com.cos.loginspringsecurity.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    public User login(User user) {
        return userMapper.login(user);
    }

    public int add(User user){
        String rawPassword = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(rawPassword));
        return userMapper.add(user);
    }

}
