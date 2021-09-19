package com.cos.loginspringsecurity.mapper;

import com.cos.loginspringsecurity.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User login(User user);

    int add(User user);

    User getUserByUserId(String user_id);
}
